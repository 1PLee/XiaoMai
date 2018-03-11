package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.entity.DescriptionEntity;
import main.entity.PerformEntity;
import main.entity.PriceEntity;
import main.entity.SeatEntity;
import main.service.PerformService;
import main.util.DateUtil;
import main.util.ResultMessage;
import main.vo.CountPerformVO;
import main.vo.PerformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */

@Service
public class PerformServiceImpl implements PerformService {
    //private static final Logger log = LoggerFactory.getLogger(PerformServiceBean.class);
    @Autowired
    PerformDAO performDAO;

    @Autowired
    BaseDAO baseDAO;

    @Transactional
    public List<PerformVO> getAllPerforms() {

        List<PerformEntity> performList = performDAO.getAllPerform();


        List<PerformVO> performVOList = new ArrayList<PerformVO>();

        Iterator<PerformEntity> iterator = performList.iterator();

        PerformEntity onePerform = null;
        PerformVO onePerformVO = null;

        while (iterator.hasNext()){
            onePerform = iterator.next();
            onePerformVO = new PerformVO();
            onePerformVO.setName(onePerform.getName());
            onePerformVO.setPerformID(onePerform.getId());
            onePerformVO.setTime(onePerform.getTime());
            onePerformVO.setPriceMin(onePerform.getPriceMin());
            onePerformVO.setVenue(onePerform.getAddress());

            switch (onePerform.getType()) {
                case 1:
                    onePerformVO.setType("演唱会");
                    break;
                case 2:
                    onePerformVO.setType("音乐会");
                    break;
                case 3:
                    onePerformVO.setType("话剧");
                    break;
                case 4:
                    onePerformVO.setType("舞蹈");
                    break;
                default:
                    onePerformVO.setType("体育比赛");
            }

            performVOList.add(onePerformVO);
        }


        return performVOList;
    }


    /*根据performID 得到演出的全部价格*/
    public List<Integer> getPrice(int performID) {
        List<Object[]> performPriceList = new ArrayList<Object[]>();
        performPriceList = performDAO.getPrice(performID);

        Object[] performPrice = performPriceList.get(0);

        List<Integer> priceList = new ArrayList<Integer>(); //这个perform的全部票价

        for (int i = 0;i<performPrice.length;i++){
                priceList.add((Integer) performPrice[i]);
        }

        return priceList;
    }

    /*根据performID 得到演出的全部座位情况*/
    public List<Integer> getSeat(int performID) {
        List<Object[]> performSeatList = new ArrayList<Object[]>();
        performSeatList = performDAO.getSeat(performID);

        Object[] performSeat = performSeatList.get(0);

        List<Integer> seatList = new ArrayList<Integer>();

        for(int i = 0;i < performSeat.length;i++){
            seatList.add((Integer)performSeat[i]);
        }

        return seatList;
    }

    @Transactional
    public PerformVO getPerformInfo(int performID) {
        PerformVO thePerform = new PerformVO();
        List<Integer> priceList = new ArrayList<Integer>();
        List<Integer> seatList = new ArrayList<Integer>();

        priceList = getPrice(performID);
        seatList = getSeat(performID);

        thePerform.setPrice(priceList);
        thePerform.setSeat(seatList);

        return thePerform;
    }

    @Transactional
    public PerformVO getDescription(int performID) {
        PerformVO thePerform = new PerformVO();
        DescriptionEntity theDes = new DescriptionEntity();

        theDes = performDAO.getDescription(performID);

        thePerform.setDescription(theDes.getDescription());

        return thePerform;
    }

    @Transactional
    public ResultMessage newPerform(PerformVO performVO) {

        PerformEntity newPerform = new PerformEntity();
        String performType = performVO.getType();
        List<Integer> price = performVO.getPrice();
        List<Integer> seat = performVO.getSeat();

        PriceEntity newPerformPrice = new PriceEntity();
        SeatEntity newPerformSeat = new SeatEntity();

        newPerform.setAddress(performVO.getVenue());
        newPerform.setName(performVO.getName());

        String performTime = DateUtil.dateStrTrans(performVO.getTime());

        newPerform.setTime(performTime);

        if (performType.equals("演唱会")) {
            newPerform.setType(1);
        } else if (performType.equals("音乐会")) {
            newPerform.setType(2);
        }else if(performType.equals("舞蹈")){
            newPerform.setType(3);
        }else if(performType.equals("话剧")){
            newPerform.setType(4);
        }else {
            newPerform.setType(5); //体育比赛
        }

        newPerform.setPriceMin(String.valueOf(price.get(0)) + "起");

        /*插入新的演出得到performID*/
        try {
            int newPerformId = baseDAO.save(newPerform);

            /*插入价格/座位信息*/
            newPerformPrice.setPerformId(newPerformId);
            newPerformSeat.setPerformId(newPerformId);

            newPerformPrice.setPriceOne(price.get(0));
            newPerformSeat.setSeatOne(seat.get(0));

            newPerformPrice.setPriceTwo(price.get(1));
            newPerformSeat.setSeatTwo(seat.get(1));

            if (price.get(2) != null) {
                newPerformPrice.setPriceThree(price.get(2));
                newPerformSeat.setSeatThree(seat.get(2));
            }

            if(price.get(3) != null){
                newPerformPrice.setPriceFour(price.get(3));
                newPerformSeat.setSeatFour(seat.get(3));
            }

            if(price.get(4) != null){
                newPerformPrice.setPriceFive(price.get(4));
                newPerformSeat.setSeatFive(seat.get(4));
            }

            if(price.get(5) != null){
                newPerformPrice.setPriceSix(price.get(5));
                newPerformSeat.setSeatSix(seat.get(5));
            }

            baseDAO.save(newPerformPrice);
            baseDAO.save(newPerformSeat);

            if(newPerformId != 0){
                return ResultMessage.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        return ResultMessage.FAILURE;
    }

    @Transactional
    public List<CountPerformVO> getAllVenuePerform(String venue) {

        List<CountPerformVO> venuePerforms = new ArrayList<CountPerformVO>();
        List<PerformEntity> performEntities = new ArrayList<PerformEntity>();

        performEntities = performDAO.getAllPerformByVenue(venue);


        CountPerformVO onePerformVO = null;
        Object[] numAndIncome = null; //每场演出卖出的总票数和总收入
        long ticketNums = 0;

        for (PerformEntity p:performEntities){
            onePerformVO = new CountPerformVO();
            onePerformVO.setPerformId(p.getId());
            onePerformVO.setPerformName(p.getName());
            onePerformVO.setPerformTime(p.getTime());
            onePerformVO.setPerformType(p.getType());

            numAndIncome = performDAO.getPerformIncome(p.getId());


            if((Long) numAndIncome[0] == null){ //没有购买记录
                onePerformVO.setSellTickets(0);
                onePerformVO.setTotalIncome(0.0);
            }else {
                ticketNums = (Long) numAndIncome[0];


                onePerformVO.setSellTickets((int) ticketNums);
                onePerformVO.setTotalIncome((Double) numAndIncome[1]);
            }

            venuePerforms.add(onePerformVO);
        }


        return venuePerforms;
    }

}

