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

    /**
     * GET all loyalty points information from the database
     *
     * @return list of all loyalty points and information
     * @throws Exception
     */
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

    /**
     * GET all loyalty points information by user ID.
     *
     * @param user_id used to retrieve all loyalty point information
     *                from database
     * @return list of all loyalty points information by user ID
     * @throws Exception
     */
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


    /**
     * GET active rewards from database by user ID
     * This method compares points from the database and checks
     * the status of points_collected / points_cap.
     * It will then return messages based on points_collection
     * percentage and/or activate rewards, reset points_collected to
     * 0 and send an email with redemption instructions if the
     * points_collected has reached the points_cap.
     *
     * @param user_id used to find points ID and points_collected information.
     * @return a message with instructions for redemption or a status update
     *          per points ID.
     * @throws Exception
     */
    @RequestMapping("/{user_id}/loyalty_points/rewards")
    public CustomResponseObject<String> getRewards(@PathVariable("user_id") int user_id) throws Exception {

        ArrayList<String> messages = service.comparePoints(user_id);

        CustomResponseObject obj = new CustomResponseObject();

        obj.setData(messages);
        obj.setMessage("success.");
        obj.setStatusCode(200);

        return obj;
    }


    /**
     * IN PROGRESS!!
     *
     * GET checks for activated rewards.
     * If a reward has already been redeemed; the reward is deactivated
     * and a message is sent to the user on it's status.
     * If a reward has not yet been redeemed and is active, a message is
     * returned to user with status and redemption email with instructions
     * is re-sent to user email address.
     *
     * @param user_id used to check database for active rewards
     * @return message to user on reward status
     */
    @RequestMapping("/{user_id}/loyalty_points/available_rewards")
    public String checkRedeemed(@PathVariable("user_id") int user_id) {
        return service.checkRedeemed(user_id);
    }

}