package demo.controller;

import com.amazonaws.util.CollectionUtils;
import demo.exceptions.DatabaseException;
import demo.mapper.UserMapper;
import demo.model.CustomResponseObject;
import demo.model.database.Loyalty;
import demo.services.LoyaltyService;
import demo.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class LoyaltyController {

    @Autowired
    LoyaltyService service;

    // calls DB for all loyalty points
    // look into why it's not refreshing data? activating in DB< but not refreshing to main page
    @GetMapping("/loyalty_points/all")
    public CustomResponseObject<Loyalty> getAllPoints() throws Exception {

        List<Loyalty> points = service.findAllPoints();

        if (!(CollectionUtils.isNullOrEmpty(points))) {

            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(points);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } else throw new DatabaseException("No loyalty_points to show.");

    }

    // needs exception for User_ID
    @RequestMapping("/{user_id}/loyalty_points")
    public CustomResponseObject<Loyalty> findByUserID(@PathVariable("user_id") int user_id) throws Exception {

        List<Loyalty> userPoints = service.findByUserID(user_id);

        if (!(CollectionUtils.isNullOrEmpty(userPoints))) {
            CustomResponseObject obj = new CustomResponseObject();

            obj.setData(userPoints);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } else throw new DatabaseException("No points exist for User ID: " + user_id + ".");
    }


    // needs exception for User_ID
    // Checks for active rewards
    @RequestMapping("/{user_id}/loyalty_points/rewards")
    public CustomResponseObject<String> getRewards(@PathVariable("user_id") int user_id) throws Exception {

        ArrayList<String> messages = service.comparePoints(user_id);

        CustomResponseObject obj = new CustomResponseObject();

        obj.setData(messages);
        obj.setMessage("success.");
        obj.setStatusCode(200);

        return obj;
    }


    // IN PROGRESS!!
    @RequestMapping("/{user_id}/loyalty_points/available_rewards")
    public String checkRedeemed(@PathVariable("user_id") int user_id) {
        return service.checkRedeemed(user_id);
    }

}