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

    List<PerformVO> getOnSellPerforms(String venue); //得到场馆正在售卖的演出信息



}
