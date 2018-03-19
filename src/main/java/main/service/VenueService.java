package main.service;

import main.util.ResultMessage;
import main.vo.OrderVO;
import main.vo.PerformVO;
import main.vo.VenueVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/27.
 */
public interface VenueService {
    VenueVO getVenueInfo(String venue);

    ResultMessage registerVenue(VenueVO venueVO); //场馆提交注册申请

    ResultMessage loginVenue(VenueVO venueVO); //根据场馆名字和分配的识别码进行登录

    ResultMessage changeVenueInfo(VenueVO venueVO); //修改场馆信息

    List<PerformVO> getOnSellPerforms(String venue); //得到场馆正在售卖的演出信息

    List<PerformVO> getVenueAllPerformCount(String venue);// 得到某个场馆的全部演出统计

    List<PerformVO> getVenueBookPerformCount(String venue); //得到某个场馆正在卖票中的演出信息统计

    List<PerformVO> getVenueEndPerformCount(String venue); //得到某个场馆演出已经结束的演出信息统计

}
