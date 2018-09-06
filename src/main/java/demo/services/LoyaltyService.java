package demo.services;

import demo.mapper.LoyaltyMapper;
import demo.model.database.Loyalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoyaltyService extends Exception {

    @Autowired
    LoyaltyMapper mapper;

    @Autowired
    DealsService dealsService;

    @Autowired
    EmailService email;


    // GET - points (all)
    public List<Loyalty> findAllPoints() {
        return mapper.listAllPoints();
    }

    // GET - points by user ID.
    public List<Loyalty> findByUserID(int user_id) {
        return mapper.findPointsByUserID(user_id);
    }


    // NEEDS Exception Handling FOR NULL ID NUMBER!!
    // compare points for reward status (switch to active) > email user notification
    public String comparePoints(int user_id) {

        // finds all points collected by user_id
        int pointsCollected = mapper.getPointsCollected(user_id);

        // finds point ID cap (Deals Services) - deals linked by user_id
        int pointsCap = dealsService.pointsCap(mapper.getDealsIDByUser(user_id));

        // gets point ID to use below:
        int pointID = mapper.getPointsID(user_id);

        // compares collected points to cap
        if (pointsCollected % pointsCap == 0) {

            // if cap is reached, activate reward, reset points and send email for redemption
            mapper.activateReward(pointID);
            mapper.resetPoints(pointID);
            email.sendRewardEmail(pointID);
            return "You have a reward! Please check your inbox for instructions on how to redeem!";

        // otherwise update points status:
        } else {

            // using email.merchantName (converts point ID > Merchant ID > name)
            String merchantName = email.merchantName(pointID);

            if (pointsCollected == 0) {
                return "You're at 0! Start shopping " +merchantName+ " to get more points! ";
            } else if (pointsCollected < 2) {
                return "A nice start! " +merchantName+ " would love to see you again! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ".";
            } else if (pointsCap < 5) {
                return "You're getting warmer! " +merchantName+ " is waiting! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ".";
            } else if (pointsCap < 8) {
                return "You're almost there!! Wouldn't you love that reward from " +merchantName+ "?! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ". Nice job!";
            } else {
                return "Total points collected from "  +merchantName+ " : " + pointsCollected + " / " + pointsCap + ". Keep it up!";
            }
        }
    }

    // IN PROGRESS
    // PUT - if deal used; deactivate deal and confirm it's been redeemed
    public String checkRedeemed(int user_id) {

        // find all active rewards per user ID
        int pointID = mapper.getAllActiveRewards(user_id);

        // check if redeemed - PLACEHOLDER FOR TESTING
        boolean rewardUsed = true;

        // compare in hash map - - - - - - - - - - -

        // if transaction is found, deactivate reward (activate redeemed)
        if (rewardUsed = true) {
            mapper.deactivateAward(pointID);
            mapper.activateRedeemed(pointID);
            return "Reward has been collected and your points have been reset.";

        // if transaction is active and not and not yet redeemed, resend email.
        } else {
            email.sendRewardEmail(pointID);
            return "Reward is active and not yet collected. Redemption Email resent.";
        }
    }

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


