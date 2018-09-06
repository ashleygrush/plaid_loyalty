package demo.controller;

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
    // look into why it's not refreshing data? activating in DB< but not refreshing to main page
    @GetMapping("/all")
    public List<Loyalty> getAllPoints() {
        return service.findAllPoints();
    }

    // calls DB for Users by ID number
    @RequestMapping("/user_id={user_id}")
    public List<Loyalty> findByUserID(@PathVariable("user_id") int user_id) {
        return service.findByUserID(user_id);
    }

    // needs exception for null ID number - Checks for active rewards
    @RequestMapping("/user_id={user_id}/rewards")
    public String getRewards(@PathVariable("user_id") int user_id) {
        return service.comparePoints(user_id);
    }

    //
    @RequestMapping("/user_id={user_id}/available_rewards")
    public String checkRedeemed(@PathVariable("user_id") int user_id) {
        return service.checkRedeemed(user_id);
    }




// SEE TRANSACTIONS UPDATE IN USER CONTROLLER/SERVICE/MAPPER - REMOVE THIS IF NO LONGER NEEDED
//    @RequestMapping("/update_points/id={id}")
//    public String UpdatePoints(@PathVariable("id") int id) {
//        return service.updatePoints(id);
//    }

}