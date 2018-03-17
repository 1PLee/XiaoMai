package main.controller;

import main.service.ManagerService;
import main.util.ResultMessage;
import main.vo.ManagerIncomeVO;
import main.vo.PerformIncomeVO;
import main.vo.PerformVO;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyipeng on 2018/3/5.
 */
@Controller
@RequestMapping(value = "/Manager")
public class ManagerController {


    @Autowired
    ManagerService managerService;

    @RequestMapping(value = "/showVenueList", method = RequestMethod.GET)
    @ResponseBody
    public List<VenueVO> showVenueList(@RequestParam("venueType") int type){
        List<VenueVO> venueVOList = new ArrayList<VenueVO>();

        venueVOList = managerService.showVenueList(type);

        return  venueVOList;
    }

    @RequestMapping(value = "/checkVenueRegister", method = RequestMethod.GET)
    @ResponseBody
    public String checkVenueRegister(@RequestParam("venue") String venue, @RequestParam("action") int action){
        ResultMessage result = null;

        result = managerService.checkVenue(venue, action);

        return result.toShow();
    }

    @RequestMapping(value = "/getAllUnSettlePerform", method = RequestMethod.GET)
    @ResponseBody
    public List<PerformVO> getAllUnSettlePerform(){

        List<PerformVO> allUnSettlePerforms = new ArrayList<PerformVO>();
        allUnSettlePerforms = managerService.getAllUnSettlePerform();


        return allUnSettlePerforms;
    }

    @RequestMapping(value = "payVenueIncome", method = RequestMethod.POST)
    @ResponseBody
    public String payVenueIncome(@RequestBody PerformVO performVO) {
        ResultMessage result = null;

        result = managerService.payVenueIncome(performVO);

        return result.toShow();
    }

    @RequestMapping(value = "/countVIPGrade", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> countVIPGrade(){ //统计网站 各个等级的会员人数
        int silverNum = 0;
        int goldNum = 0;
        int diamondNum = 0;

        silverNum = managerService.countVIPGrade(1);
        goldNum = managerService.countVIPGrade(2);
        diamondNum = managerService.countVIPGrade(3);

        Map<String, Integer> countMap = new HashMap<String, Integer>();
        countMap.put("白银会员", silverNum);
        countMap.put("黄金会员", goldNum);
        countMap.put("钻石会员", diamondNum);


        return countMap;
    }

    @RequestMapping(value = "/countVenueByCapacity", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> countVenueByCapacity(){

        Map<String, Integer> resultMap = new HashMap<String, Integer>();

        resultMap = managerService.countVenueByCapacity();


        return resultMap;
    }

    @RequestMapping(value = "/getFinancialInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<ManagerIncomeVO> getFinancialInfo(@RequestParam("year") String year) { //获取网站某一年细化到各个月份的财务统计

        List<ManagerIncomeVO> financialList = new ArrayList<ManagerIncomeVO>();
        financialList = managerService.getFinancialInfo(year);

        return financialList;
    }


}
