package demo.services;

import demo.mapper.LoyaltyMapper;
import demo.model.database.DBSearch;
import demo.model.database.Loyalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class LoyaltyService {

    @Autowired
    LoyaltyMapper mapper;

    int counter = 1;

    // GET - points/Loyalty status: customers (all)
    public List<Loyalty> findAllPoints() {
        return mapper.listAllPoints();
    }

    // GET - points by user ID.
    public DBSearch findByUserID(int user_id) {

        DBSearch searchID = new DBSearch();

        searchID.setId(user_id);

        searchID.setLoyalty(mapper.findPointsByUserID(user_id));

        return searchID;
    }


    // check customer ID Loyalty points % for new point value (how many stars)
    public String updatePoints(int id){

        //create number generator (1 - 10)
        Random random = new Random();

        // import loyalty class
        Loyalty updatePoints = new Loyalty();

//      once hash map is connected - set transactions counter
//      > if new transactions exist; generate new number
//        if (counter <= 0){

            if (counter > 0) {
            int points = random.nextInt((10) + 1); // add counter here!

            updatePoints.setPoints(points);
            updatePoints.setId(id);

            mapper.updatePoints(updatePoints);

            // check if points have hit cap...
            checkRedeemed(id);
            return "points successfully updated.";
        }
        // otherwise, return ID.
        return "No new transactions found.";
    }


    // NEEDS Exception Handling FOR NULL ID NUMBER!!
    // PUT - if 10 is hit, redeem points (switch to active) > email user notification
    public String checkActive(int id) {

        // if points are maxed out, activate and send email.
        if (mapper.loyaltyCount(id) >= 10) {
            mapper.activateReward(id);
            LoyaltyService.sendInstructionsEmail();
            return "Please check your inbox for instructions on how to redeem your reward!";
        }

        // otherwise, send estimated points email. "You're 1 point away from a free drink!"
        else {
            LoyaltyService.sendPointsCountEmail();
            return "Please check your inbox for an update on your points!";
        }
    }

    // IN PROGRESS
    // PUT - if deal used; switch from active to inactive
    public String checkRedeemed(int id) {

        // if transaction is found, deactivate reward (activate redeemed)
        if (true == true) {
            mapper.deactivateAward(id);
            mapper.activateRedeemed(id);
            LoyaltyService.sendPointsCountEmail();
            return "Reward has been collected and your points have been reset.";
        } else {
            LoyaltyService.sendInstructionsEmail();
            return "Reward is active and not yet collected.";
        }
    }


    // sends email that reward is active and instructions to redeem
    private static void sendInstructionsEmail() {
    }

    // sends email with current points count
    private static void sendPointsCountEmail() {
    }


    // DELETE - if user no longer exists, delete points.

}



