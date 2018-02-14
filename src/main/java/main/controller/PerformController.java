package main.controller;

import main.service.PerformService;
import main.vo.PerformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liyipeng on 2018/2/12.
 */
@Controller
@RequestMapping(value = "/Perform")

public class PerformController {

    @Autowired
    PerformService performService;

    @RequestMapping(value = "/getAllPerforms")
    @ResponseBody
    public List<PerformVO> getAllTickets(){ //get all performs to show on the home page
        List<PerformVO> performList = new ArrayList<PerformVO>();

        performList = performService.getAllPerforms();

        return performList;
    }

}