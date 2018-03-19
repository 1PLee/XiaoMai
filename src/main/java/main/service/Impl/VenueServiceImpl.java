package main.service.Impl;

import com.sun.corba.se.spi.ior.ObjectKey;
import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.dao.VenueDAO;
import main.entity.PerformEntity;
import main.entity.VenueEntity;
import main.service.ManagerService;
import main.service.UserOrderService;
import main.service.VenueService;
import main.util.DateUtil;
import main.util.ResultMessage;
import main.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/27.
 */
@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    VenueDAO venueDAO;

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    PerformDAO performDAO;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    ManagerService managerService;


    @Transactional
    public VenueVO getVenueInfo(String venue) {
        VenueEntity venueEntity = new VenueEntity();
        venueEntity = venueDAO.getVenueInfo(venue);

        VenueVO theVenue = new VenueVO();
        theVenue.setDescription(venueEntity.getDescription());
        theVenue.setLocation(venueEntity.getLocation());
        theVenue.setCapacity(venueEntity.getCapacity());
        theVenue.setVenueID(venueEntity.getVenueId());
        theVenue.setMail(venueEntity.getMail());

        return theVenue;
    }


    @Transactional
    public ResultMessage registerVenue(VenueVO venueVO) {
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setVenue(venueVO.getName());
        venueEntity.setCapacity(venueVO.getCapacity());
        venueEntity.setLocation(venueVO.getLocation());
        venueEntity.setDescription(venueVO.getDescription());
        venueEntity.setMail(venueVO.getMail());
        venueEntity.setType(0);


        ResultMessage result = null;
        result = venueDAO.registerVenue(venueEntity);

        return result;
    }




    @Transactional
    public ResultMessage loginVenue(VenueVO venueVO) {
        VenueEntity theVenue = new VenueEntity();
        String venueName = venueVO.getName();
        int venueCode = venueVO.getCode();

        theVenue = venueDAO.getVenueInfo(venueName);

        if(venueCode != theVenue.getCode()){
            return ResultMessage.WRONG_PASSWORD;
        }

        return ResultMessage.SUCCESS;
    }

    @Transactional
    public ResultMessage changeVenueInfo(VenueVO venueVO) {
        String venueName = venueVO.getName();
        VenueEntity theVenue = new VenueEntity();
        theVenue = venueDAO.getVenueInfo(venueName);
        theVenue.setVenue(venueVO.getName());
        theVenue.setMail(venueVO.getMail());
        theVenue.setLocation(venueVO.getLocation());
        theVenue.setCapacity(venueVO.getCapacity());
        theVenue.setDescription(venueVO.getDescription());
        theVenue.setType(0);

        ResultMessage result = null;

        result = baseDAO.saveOrUpdate(theVenue);

        return result;
    }

    @Transactional
    public List<PerformVO> getOnSellPerforms(String venue) {

        List<PerformEntity> allPerforms = performDAO.getAllPerformByVenue(venue);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp performTime = null;

        List<PerformVO> onSellPerforms = new ArrayList<PerformVO>();
        Object[] performPrice = null;
        Object[] performSeat = null;
        List<Object[] > seatList = null;
        List<Integer> priceList = null;

        PerformVO onePerform = null;

        for (PerformEntity p : allPerforms) {
            performTime = DateUtil.String2Timestamp(p.getTime());

            if(!(performTime.before(now))){ //演出还没有结束

                onePerform = new PerformVO();
                performPrice = performDAO.getPrice(p.getId()).get(0);
                System.out.println("look performId:" + p.getId());
                seatList = performDAO.getSeat(p.getId());

                if(seatList.size() != 0){

                    performSeat = seatList.get(0);
                    priceList = new ArrayList<Integer>();

                    for(int i =0;i<performSeat.length;i++){

                        if((Integer)performSeat[i] != 0){ //还有座位

                            priceList.add((Integer) performPrice[i]);

                        }

                    }

                    onePerform.setPrice(priceList);
                    onePerform.setName(p.getName());
                    onePerform.setPerformID(p.getId());
                    onSellPerforms.add(onePerform);

                }


            }


        }


        return onSellPerforms;
    }

    @Transactional
    public List<PerformVO> getVenueAllPerformCount(String venue) {
        List<PerformVO> performCountVOList = new ArrayList<PerformVO>();
        List<PerformVO> bookPerformCountVOList = new ArrayList<PerformVO>(); //处于售卖中的演出
        List<PerformVO> endPerformCountVOList = new ArrayList<PerformVO>(); // 已经结束售卖的演出

        bookPerformCountVOList = getVenueBookPerformCount(venue);
        endPerformCountVOList = getVenueEndPerformCount(venue);

        performCountVOList.addAll(bookPerformCountVOList);
        performCountVOList.addAll(endPerformCountVOList);


        return performCountVOList;
    }

    @Transactional
    public List<PerformVO> getVenueBookPerformCount(String venue) {
        List<PerformEntity> allPerforms = new ArrayList<PerformEntity>(); //场馆承办的全部演出
        List<PerformVO> bookPerformCountVO = new ArrayList<PerformVO>(); //场馆举办的处于预定状态的演出的统计情况

        PerformVO bookPerformVO = null;
        PerformIncomeVO performIncomeVO = null;

        Object[] typeList = {1, 4}; //售卖中的演出订单 状态分为已支付和退款

        allPerforms = performDAO.getAllPerformByVenue(venue);

        for (PerformEntity onePerform : allPerforms) {
            if(onePerform.getState() == 1){
                bookPerformVO = new PerformVO();
                performIncomeVO = new PerformIncomeVO();

                bookPerformVO.setPerformID(onePerform.getId());
                bookPerformVO.setName(onePerform.getName());
                bookPerformVO.setTime(onePerform.getTime());
                bookPerformVO.setState(onePerform.getState());

                performIncomeVO = managerService.getPerformIncome(onePerform.getId(), typeList);

                bookPerformVO.setPerformIncomeVO(performIncomeVO);

                bookPerformCountVO.add(bookPerformVO);

            }

        }


        return bookPerformCountVO;
    }

    @Transactional
    public List<PerformVO> getVenueEndPerformCount(String venue) {

        List<PerformEntity> allPerforms = new ArrayList<PerformEntity>(); //场馆承办的全部演出
        List<PerformVO> endPerformCountVO = new ArrayList<PerformVO>(); //场馆举办的处于已经结束状态的演出的统计情况

        PerformVO endPerformVO = null;
        PerformIncomeVO performIncomeVO = null;

        Object[] typeList = {1, 2, 4}; //已结束的演出订单 状态分为已支付，已检票和退款

        allPerforms = performDAO.getAllPerformByVenue(venue);

        for (PerformEntity onePerform : allPerforms) {
            if(onePerform.getState() == 2){

                endPerformVO = new PerformVO();
                performIncomeVO = new PerformIncomeVO();

                endPerformVO.setPerformID(onePerform.getId());
                endPerformVO.setName(onePerform.getName());
                endPerformVO.setTime(onePerform.getTime());
                endPerformVO.setState(onePerform.getState());

                performIncomeVO = managerService.getPerformIncome(onePerform.getId(), typeList);

                endPerformVO.setPerformIncomeVO(performIncomeVO);

                endPerformCountVO.add(endPerformVO);

            }

        }

        return endPerformCountVO;
    }


}
