package main.service.Impl;

import main.dao.PerformDAO;
import main.service.PerformService;
import main.vo.PerformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
@Transactional
@Service
public class PerformServiceBean implements PerformService {
    @Autowired
    PerformDAO performDAO;
    public List<PerformVO> getAllPerforms() {
        return null;
    }
}
