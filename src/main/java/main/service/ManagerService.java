package main.service;

import main.util.ResultMessage;
import main.vo.VenueVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerService {

    List<VenueVO> showVenueList(int type); //0代表返回待审批 1代表返回已审批 2代表返回请求修改的

    ResultMessage checkVenue(String venue, int action); //审批venue的申请状态 1代表通过审批并发送识别码.0代表不通过审批直接无情删掉
}
