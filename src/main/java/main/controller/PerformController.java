package main.controller;

import main.service.PerformService;
import main.vo.PerformVO;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
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

    @RequestMapping(value = "/getAllPerforms",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,List<PerformVO>> getAllTickets(){ //get all performs to show on the home page
        List<PerformVO> performList = new ArrayList<PerformVO>();

        performList = performService.getAllPerforms();

        Map<String,List<PerformVO>> resultMap = new HashMap<String, List<PerformVO>>();
        resultMap.put("data",performList);
        return resultMap;
    }

    @RequestMapping(value = "/getPerformInfo", method = RequestMethod.GET)
    @ResponseBody
    public PerformVO getPerformInfo(@RequestParam("performId") int performId){
        PerformVO thePerform = new PerformVO();
        thePerform = performService.getPerformInfo(performId);

        return thePerform;
    }

    @RequestMapping(value = "/getPrice", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPrice(@RequestParam("performID") int performID){
        List<Integer> performPrice = new ArrayList<Integer>();
        performPrice = performService.getPrice(performID);

        JSONObject result = new JSONObject();
        result.put("allPrice", performPrice);

        return result;
    }

}
