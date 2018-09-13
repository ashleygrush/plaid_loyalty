package demo.controller;

import demo.exceptions.DatabaseException;
import demo.mapper.UserMapper;
import demo.model.CustomResponseObject;
import demo.model.database.Loyalty;
import demo.services.LoyaltyService;
import demo.services.UserService;
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
    public CustomResponseObject <Loyalty> getAllPoints() throws Exception {
        List <Loyalty> points = service.findAllPoints();

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(points);
        obj.setMessage("success.");
        obj.setStatusCode(200);

        return obj;
    }

    // needs exception for User_ID
    @RequestMapping("/user_id={user_id}")
    public CustomResponseObject <Loyalty> findByUserID(@PathVariable("user_id") int user_id) throws Exception {

        List <Loyalty> userPoints = service.findByUserID(user_id);

        if (userPoints != null) {
            CustomResponseObject obj = new CustomResponseObject();

            obj.setData(userPoints);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;
        }
        else throw new DatabaseException("No points exist for User ID : " +user_id+".");
    }

    // needs exception for User_ID
    // Checks for active rewards
    @RequestMapping("/user_id={user_id}/rewards")
    public String getRewards(@PathVariable("user_id") int user_id) throws Exception {
        return service.comparePoints(user_id);
    }

    // IN PROGRESS!!
    @RequestMapping("/user_id={user_id}/available_rewards")
    public String checkRedeemed(@PathVariable("user_id") int user_id) {
        return service.checkRedeemed(user_id);
    }

}