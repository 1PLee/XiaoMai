package main.service;

import main.util.ResultMessage;
import main.vo.PerformIncomeVO;
import main.vo.PerformVO;
import main.vo.VenueVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerService {

    List<VenueVO> showVenueList(int type); //0代表返回待审批 1代表返回已审批 2代表返回请求修改的

    ResultMessage checkVenue(String venue, int action); //审批venue的申请状态 1代表通过审批并发送识别码.0代表不通过审批直接无情删掉

    List<PerformVO> getAllUnSettlePerform(); //得到所有没有结算的项目信息以及项目总收入

    PerformIncomeVO getPerformIncome(int performId); //得到某一场演出的收入信息

    ResultMessage payVenueIncome(PerformVO performVO); //从未结算演出列表中选择某一场演出进行结算
}
