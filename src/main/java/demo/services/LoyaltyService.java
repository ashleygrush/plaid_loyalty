package demo.services;

import demo.mapper.LoyaltyMapper;
import demo.model.database.Loyalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoyaltyService {

    @Autowired
    LoyaltyMapper mapper;

    // GET - points (all)
    public List<Loyalty> findAllPoints() {
        return mapper.listAllPoints();
    }

    // GET - points by user ID.
    public List<Loyalty> findByUserID(int user_id) {
        return mapper.findPointsByUserID(user_id);
    }


    // NEEDS Exception Handling FOR NULL ID NUMBER!!
    // PUT - if 10 is hit, redeem points (switch to active) > email user notification
    public String checkActive(int id) {

        // if points are maxed out, activate and send email.
        if (mapper.loyaltyCount(id) >= 10) {
            mapper.activateReward(id);
            sendInstructionsEmail(id);
            return "Please check your inbox for instructions on how to redeem your reward!";
        }

        // otherwise, send estimated points email. "You're 1 point away from a free drink!"
        else {
            sendPointsCountEmail(id);
            return "Please check your inbox for an update on your points!";
        }
    }

    // IN PROGRESS
    // PUT - if deal used; deactivate deal and confirm it's been redeemed
    public String checkRedeemed(int id) {

        // if transaction is found, deactivate reward (activate redeemed)
        if (true == true) {
            mapper.deactivateAward(id);
            mapper.activateRedeemed(id);
            sendPointsCountEmail(id);
            return "Reward has been collected and your points have been reset.";
        } else {
            sendInstructionsEmail(id);
            return "Reward is active and not yet collected. Redemption Email resent.";
        }
    }


    // sends email that reward is active and instructions to redeem
    private void sendInstructionsEmail(int id){
        EmailService.sendMail(
                userEmailAddress(id),
                "grush.ashley@gmail.com",
                "Redeem your Loyalty Reward!",
                "Take this to your QR code to your local coffee shop for a free drink!");
    }

    // sends email with current points count
    private void sendPointsCountEmail(int id) {
        EmailService.sendMail(
                userEmailAddress(id),
                "grush.ashley@gmail.com",
                "Here's your current reward count!",
                "You're almost there! Just a few more points to go!");
    }

    // Finds email by User ID > Loyalty ID
    private String userEmailAddress(int id){
        int user_id = mapper.userIdByLoyaltyID(id);
        return mapper.userEmailByID(user_id);
    }


// MERGE WITH HASH MAP IF NEEDED - THEN DELETE
//    // check customer ID Loyalty points % for new point value (how many stars)
//    public String updatePoints(int id){
//
//        //create number generator (1 - 10)
//        Random random = new Random();
//
//        // import loyalty class
//        Loyalty updatePoints = new Loyalty();
//
////      once hash map is connected - set transactions counter
////      > if new transactions exist; generate new number
////        if (counter <= 0){
//
//            if (counter > 0) {
//            int points = random.nextInt((10) + 1); // add counter here!
//
//            updatePoints.setPoints(points);
//            updatePoints.setId(id);
//
//            mapper.updatePoints(updatePoints);
//
//            // check if points have hit cap...
//            checkRedeemed(id);
//            return "points successfully updated.";
//        }
//        // otherwise, return ID.
//        return "No new transactions found.";
//    }

}



