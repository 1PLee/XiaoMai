package main.service;

import main.vo.PerformVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
public interface PerformService {

     List<PerformVO> getAllPerforms();

     List<Integer> getPrice(int performID);

     PerformVO getPerformInfo(int performID);
}
