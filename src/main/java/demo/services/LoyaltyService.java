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

        // finds all points collected
        int pointsCollected = mapper.getPointsCollected(user_id);

        // finds point ID cap (Deals Services)
        int pointsCap = dealsService.pointsCap(mapper.getDealsID(user_id));

        // compares collected points to cap
        if (pointsCollected % pointsCap == 0) {

            // if cap is reached, activate reward, reset points and send email for redemption
            int activePoint = mapper.getPointsID(user_id);
            mapper.activateReward(activePoint);
            mapper.resetPoints(activePoint);
            email.sendInstructionsEmail(user_id, activePoint);
            return "You have a reward! Please check your inbox for instructions on how to redeem!";

        // otherwise update points status:
        } else {
            if (pointsCollected == 0) {
                return "You're at 0! Start shopping @Merchant to get more points! ";
            } else if (pointsCollected < 2) {
                return "A nice start! @Merchant would love to see you again! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ".";
            } else if (pointsCap < 5) {
                return "You're getting warmer! @Merchant is waiting! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ".";
            } else if (pointsCap < 8) {
                return "You're almost there!! Wouldn't you love that reward from @Merchant?! " +
                        "Points collected : " + pointsCollected + "/" + pointsCap + ". Nice job!";
            } else {
                return "Total points collected : " + pointsCollected + "/" + pointsCap + ". Keep it up!";
            }
        }
    }

    // IN PROGRESS
    // PUT - if deal used; deactivate deal and confirm it's been redeemed
    public String checkRedeemed(int user_id) {

        // find all active rewards per user ID
        int id = mapper.getAllActiveRewards(user_id);

        // check if redeemed - PLACEHOLDER FOR TESTING
        boolean rewardUsed = true;

        // compare in hash map - - - - - - - - - - -

        // if transaction is found, deactivate reward (activate redeemed)
        if (rewardUsed = true) {
            mapper.deactivateAward(id);
            mapper.activateRedeemed(id);
            return "Reward has been collected and your points have been reset.";

        // if transaction is active and not and not yet redeemed, resend email.
        } else {
            email.sendInstructionsEmail(user_id, id);
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


