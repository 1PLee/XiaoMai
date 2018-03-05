package main.controller;

import main.service.VenueService;
import main.util.ResultMessage;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liyipeng on 2018/2/27.
 */
@Controller
@RequestMapping(value = "/Venue")
public class VenueController {

    @Autowired
    VenueService venueService;


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

        result = venueService.registerVenue(venueVO);

        return result.toShow();
    }





}
