package main.service.Impl;

import com.sun.corba.se.spi.ior.ObjectKey;
import main.dao.BaseDAO;
import main.dao.ManagerDAO;
import main.dao.PerformDAO;
import main.dao.VenueDAO;
import main.entity.PerformEntity;
import main.entity.TicketOrderEntity;
import main.entity.VenueEntity;
import main.service.ManagerService;
import main.util.DateUtil;
import main.util.ResultMessage;
import main.util.SendMailCode;
import main.vo.ManagerIncomeVO;
import main.vo.PerformIncomeVO;
import main.vo.PerformVO;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by liyipeng on 2018/3/5.
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDAO managerDAO;

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    VenueDAO venueDAO;

    @Autowired
    PerformDAO performDAO;

    @Transactional
    public List<VenueVO> showVenueList(int type) {
        List<VenueEntity> venueEntityList = new ArrayList<VenueEntity>();
        List<VenueVO> venueVOList = new ArrayList<VenueVO>();

        venueEntityList = managerDAO.showVenueList(type);
        Iterator<VenueEntity> iterator = venueEntityList.iterator();

        VenueEntity oneVenueEntity = null;
        VenueVO oneVenueVO = null;

        while (iterator.hasNext()){
            oneVenueEntity = iterator.next();
            oneVenueVO = new VenueVO();

            oneVenueVO.setVenueID(oneVenueEntity.getVenueId());
            oneVenueVO.setName(oneVenueEntity.getVenue());
            oneVenueVO.setLocation(oneVenueEntity.getLocation());
            oneVenueVO.setDescription(oneVenueEntity.getDescription());
            oneVenueVO.setCapacity(oneVenueEntity.getCapacity());
            oneVenueVO.setType(oneVenueEntity.getType());
            oneVenueVO.setMail(oneVenueEntity.getMail());
            oneVenueVO.setCode(oneVenueEntity.getCode());

            venueVOList.add(oneVenueVO);
        }

        return venueVOList;
    }

    @Transactional
    public ResultMessage checkVenue(String venue, int action) {

        ResultMessage result = null;
        int code = 0; //识别码
        code = new Random().nextInt(9999999);
        if(code < 1000000){
            code += 1000000;
        }

        if(action == 1){ //通过审核
            VenueEntity theVenue = new VenueEntity();
            theVenue = venueDAO.getVenueInfo(venue);
            String mail = theVenue.getMail();
            SendMailCode.sendMailCode(mail, code);

        }else { //未通过审核
            code = 0;
        }

        result = managerDAO.checkVenue(venue, action, code);

        return result;
    }

    @Transactional
    public List<PerformVO> getAllUnSettlePerform() {
        List<PerformVO> unSettlePerforms = new ArrayList<PerformVO>();
        List<PerformEntity> unSettleEntities = new ArrayList<PerformEntity>();

        unSettleEntities = performDAO.getAllUnSettlePerform();
        PerformVO unSettlePerformVO = null;
        PerformIncomeVO performIncomeVO = null;

        Object[] orderTypeList = {1, 2, 4};

        for (PerformEntity onePerform : unSettleEntities) {
            unSettlePerformVO = new PerformVO();
            unSettlePerformVO.setPerformID(onePerform.getId());
            unSettlePerformVO.setName(onePerform.getName());
            unSettlePerformVO.setTime(onePerform.getTime());
            unSettlePerformVO.setVenue(onePerform.getAddress());

            performIncomeVO = getPerformIncome(onePerform.getId(), orderTypeList);
            unSettlePerformVO.setPerformIncomeVO(performIncomeVO);

            unSettlePerforms.add(unSettlePerformVO);
        }


        return unSettlePerforms;
    }

    @Transactional
    public PerformIncomeVO getPerformIncome(int performId, Object[] typeList) {
        PerformIncomeVO performIncomeVO = new PerformIncomeVO();
        double totalIncome = 0.0;
        int ticketsNum = 0; //卖出的总票数
        int backTickets = 0; //退订的票数总和

        List<Integer> seatDetails = new ArrayList<Integer>(); //各个位置上卖出的票数

        List<TicketOrderEntity> allOrders = new ArrayList<TicketOrderEntity>();

        allOrders = performDAO.getPerformIncomeDetail(performId, typeList); //typeList中存放希望得到的订单状态


        List<Object[]> priceList = new ArrayList<Object[]>();
        priceList = performDAO.getPrice(performId);


        Object[] price = priceList.get(0);

        for(int i =0;i<price.length;i++) {
            if (price[i] == null) {
                break;
            }

            seatDetails.add(0);
        }

        for (TicketOrderEntity oneOrder : allOrders) {

            if (oneOrder.getOrderType() == 4) { //退订
                backTickets = backTickets + oneOrder.getTicketNum();
                totalIncome = totalIncome + (oneOrder.getOrderMoney() - oneOrder.getBackMoney());

            }else {

                ticketsNum = ticketsNum + oneOrder.getTicketNum();

                for(int j =0;j<price.length;j++) { //增加某个位置的票数

                    if(price[j] == null){
                        break;
                    }

                    if ( ( (Integer)price[j] ).equals(oneOrder.getTicketMoney())) {

                        seatDetails.set(j, (seatDetails.get(j) + oneOrder.getTicketNum()));
                    }
                }

                totalIncome = totalIncome + oneOrder.getOrderMoney();

            }

        }

        performIncomeVO.setPerformId(performId);
        performIncomeVO.setTotalIncome(totalIncome);
        performIncomeVO.setBackTicketNum(backTickets);
        performIncomeVO.setTotalTicketNum(ticketsNum);
        performIncomeVO.setSeatCountList(seatDetails);


        return performIncomeVO;
    }


    @Transactional
    public ResultMessage payVenueIncome(PerformVO performVO) {

        PerformIncomeVO performIncomeVO = performVO.getPerformIncomeVO();
        System.out.println("look income:" + performIncomeVO.getTotalIncome());
        double totalIncome = performIncomeVO.getTotalIncome();
        String performTime = performVO.getTime();
        int performId = performVO.getPerformID();
        PerformEntity thePerform = new PerformEntity();
        thePerform = baseDAO.getEntity(PerformEntity.class, performId);

        String venue = thePerform.getAddress();
        String[] venueSplit = performTime.split("\\.");
        System.out.println("year: " + venueSplit[0]);
        int year = Integer.parseInt(venueSplit[0]);

        double venueIncome = totalIncome * (0.5);

        VenueEntity venueEntity = venueDAO.getVenueInfo(venue);

        /*结算场馆收入*/
        System.out.println("look the venueID:" + venueEntity.getVenueId());
        ResultMessage result = managerDAO.payVenueIncome(venueEntity.getVenueId(), venueIncome, year);

        if(result == ResultMessage.SUCCESS){
            /*改变演出状态*/
            thePerform.setState(3);

            result = baseDAO.saveOrUpdate(thePerform);
        }


        return result;

    }

    @Transactional
    public int countVIPGrade(int grade) {
        int countVip = 0;
        countVip = managerDAO.countVIPGrade(grade);

        return countVip;
    }

    @Transactional
    public Map<String, Integer> countVenueByCapacity() {

        Map<String, Integer> countMap = new HashMap<String, Integer>();

        int underThousand = 0;// 0-1000
        int underTwoThousand = 0; // 1000-2000
        int underFiveThousand = 0; //2000-5000
        int upFiveThousand = 0; //5000以上

        underThousand = managerDAO.countVenueByCapacity(0,1001);
        underTwoThousand = managerDAO.countVenueByCapacity(1000, 2001);
        underFiveThousand = managerDAO.countVenueByCapacity(2000, 5001);
        upFiveThousand = managerDAO.countVenueByCapacity(5000, 10001);

        countMap.put("1000以内", underThousand);
        countMap.put("2000以内", underTwoThousand);
        countMap.put("5000以内", underFiveThousand);
        countMap.put("10000以内", upFiveThousand);


        return countMap;
    }


    @Transactional
    public List<ManagerIncomeVO> getFinancialInfo(String year) {
        List<TicketOrderEntity> orderEntities = new ArrayList<TicketOrderEntity>();
        List<PerformEntity> oneYearPerforms = new ArrayList<PerformEntity>(); // 某一年的全部演出

        ManagerIncomeVO oneMonthIncomeVO = null;
        List<ManagerIncomeVO> yearIncomeList = new ArrayList<ManagerIncomeVO>(); //存放十二个月的统计
        orderEntities = managerDAO.getYearOrders(year); //得到某一年的全部订单
        int oneYear = 12;
        int theMonth = 0;
        int orderType = 0;
        String orderTime = null;
        String performTime = null;

        double totalNum = 0.0;
        double backMoney = 0.0;
        double totalProfit = 0.0;
        int performs = 0;

        for(int i=0;i<oneYear;i++){ //初始化List
            oneMonthIncomeVO = new ManagerIncomeVO();
            yearIncomeList.add(oneMonthIncomeVO);
        }

        oneYearPerforms = performDAO.getPerformsByYear(year);


        for (PerformEntity onePerform : oneYearPerforms) {
            performTime = onePerform.getTime();
            theMonth = Integer.parseInt(performTime.split("\\.")[1]);
            System.out.println("look the Month:" + theMonth);
            oneMonthIncomeVO = yearIncomeList.get(theMonth - 1);

            performs = oneMonthIncomeVO.getPerforms();
            performs++;
            oneMonthIncomeVO.setPerforms(performs);
        }



        for (TicketOrderEntity oneOrder : orderEntities) {
            orderTime = DateUtil.timestamp2String(oneOrder.getOrderTime());
            theMonth = Integer.parseInt(orderTime.split("-")[1]);

            oneMonthIncomeVO = yearIncomeList.get(theMonth - 1);

            totalNum = oneMonthIncomeVO.getTotalIncome();
            backMoney = oneMonthIncomeVO.getBackMoney();
            totalProfit = oneMonthIncomeVO.getTotalProfit();

            orderType = oneOrder.getOrderType();
            if(orderType == 1 || orderType == 2){ //已支付或已检票订单
                totalNum += oneOrder.getOrderMoney();
                totalProfit += oneOrder.getOrderMoney();
            }

            if(orderType == 4){ //退款订单
                backMoney += oneOrder.getBackMoney();
                totalProfit -= oneOrder.getBackMoney();
            }

            oneMonthIncomeVO.setTotalIncome(totalNum);
            oneMonthIncomeVO.setTotalProfit(totalProfit);
            oneMonthIncomeVO.setBackMoney(backMoney);

        }


        return yearIncomeList;
    }


}
