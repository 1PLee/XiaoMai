package main.service;

import main.util.ResultMessage;
import main.vo.ManagerIncomeVO;
import main.vo.PerformIncomeVO;
import main.vo.PerformVO;
import main.vo.VenueVO;

import java.util.List;
import java.util.Map;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerService {

    List<VenueVO> showVenueList(int type); //0代表返回待审批 1代表返回已审批 2代表返回请求修改的

    ResultMessage checkVenue(String venue, int action); //审批venue的申请状态 1代表通过审批并发送识别码.0代表不通过审批直接无情删掉

    List<PerformVO> getAllUnSettlePerform(); //得到所有没有结算的项目信息以及项目总收入

    PerformIncomeVO getPerformIncome(int performId, Object[] typeList); //得到某一场演出的收入信息，typeList代表订单的状态

    ResultMessage payVenueIncome(PerformVO performVO); //从未结算演出列表中选择某一场演出进行结算

    int countVIPGrade(int grade); //统计各个等级的会员人数 grade 代表等级

    Map<String, Integer> countVenueByCapacity(); //根据容纳人数区分场馆

    List<ManagerIncomeVO> getFinancialInfo(String year); //根据年份来获得该年各个月份的财务信息



}
