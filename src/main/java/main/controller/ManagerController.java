package main.controller;

import main.service.ManagerService;
import main.util.ResultMessage;
import main.vo.PerformIncomeVO;
import main.vo.PerformVO;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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



}
