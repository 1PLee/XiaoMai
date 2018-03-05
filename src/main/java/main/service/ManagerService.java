package main.service;

import main.vo.VenueVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerService {

    List<VenueVO> showVenueList(int type); //0代表返回待审批 1代表返回已审批 2代表返回请求修改的
}
