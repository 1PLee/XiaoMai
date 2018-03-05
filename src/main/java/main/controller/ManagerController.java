package main.controller;

import main.service.ManagerService;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
