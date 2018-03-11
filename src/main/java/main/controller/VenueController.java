package main.controller;

import main.service.PerformService;
import main.service.VenueService;
import main.util.ResultMessage;
import main.vo.CountPerformVO;
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
 * Created by liyipeng on 2018/2/27.
 */
@Controller
@RequestMapping(value = "/Venue")
public class VenueController {

    @Autowired
    VenueService venueService;

    @Autowired
    PerformService performService;


    @RequestMapping(value = "/getVenueInfo", method = RequestMethod.GET)
    @ResponseBody
    public VenueVO getVenueInfo(@RequestParam("venue") String venue){
        VenueVO theVenue = new VenueVO();
        theVenue = venueService.getVenueInfo(venue);

        return theVenue;
    }


    @RequestMapping(value = "/registerVenue", method = RequestMethod.POST)
    @ResponseBody
    public String registerVenue(@RequestBody VenueVO venueVO) {
        ResultMessage result = null;
        System.out.println("look the name" + venueVO.getName());
        result = venueService.registerVenue(venueVO);

        return result.toShow();
    }

    @RequestMapping(value = "/loginVenue", method = RequestMethod.POST)
    @ResponseBody
    public String loginVenue(@RequestBody VenueVO venueVO){
        ResultMessage result = null;

        result = venueService.loginVenue(venueVO);

        return result.toShow();
    }


    @RequestMapping(value = "/getAllPerforms", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<CountPerformVO>> getAllPerforms(@RequestParam("venue") String venue){

        Map<String, List<CountPerformVO>> resultMap = new HashMap<String, List<CountPerformVO>>();

        List<CountPerformVO> countPerformVOS = new ArrayList<CountPerformVO>();

        countPerformVOS = performService.getAllVenuePerform(venue);

        resultMap.put("data", countPerformVOS);

        return resultMap;
    }



}
