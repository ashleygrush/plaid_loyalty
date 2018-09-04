package demo.controller;

import demo.model.database.DBSearch;
import demo.model.database.Loyalty;
import demo.services.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loyalty_points")
public class LoyaltyController {

    @Autowired
    LoyaltyService service;

    // calls DB for all loyalty points
    @GetMapping("/all")
    public List<Loyalty> getAllPoints() {
        return service.findAllPoints();
    }

    // calls DB for Users by ID number
    // update to LIST for more than one reward system
    @RequestMapping("/{user_id}")
    public DBSearch findByUserID(@PathVariable("user_id") int user_id) {
        return service.findByUserID(user_id);
    }

    // needs exception for null ID number
    @RequestMapping("/redemption_activity/id={id}")
    public String RedemptionActivity(@PathVariable("id") int id) {
        return service.checkRedeemed(id);
    }


    @RequestMapping("/update_points/id={id}")
    public String UpdatePoints(@PathVariable("id") int id) {
        return service.updatePoints(id);
    }

    // NOT WORKING - IN PROGRESS
    @RequestMapping("/check_active/id={id}")
    public String checkActive(@PathVariable("id") int id) {
        return service.checkActive(id);
    }
}