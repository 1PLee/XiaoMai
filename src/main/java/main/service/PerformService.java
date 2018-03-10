package main.service;

import main.util.ResultMessage;
import main.vo.PerformVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
public interface PerformService {

     List<PerformVO> getAllPerforms();

     List<Integer> getPrice(int performID);

     PerformVO getPerformInfo(int performID);

     PerformVO getDescription(int performID);

     ResultMessage newPerform(PerformVO performVO); //发布新的演出计划
}
