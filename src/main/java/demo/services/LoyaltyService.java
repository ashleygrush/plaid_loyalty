package demo.services;


import demo.mapper.LoyaltyMapper;
import demo.model.database.Loyalty;
import demo.model.PointsHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Loyalty> findByUserID(int user_id) throws Exception {

        List<Loyalty> userPoints;

        try {
            userPoints = mapper.findPointsByUserID(user_id);
        } catch (Exception e) {
            throw e;
        }
        return userPoints;
    }


    // needs exception for null User_ID
    // compare points for reward status (switch to active) > email user notification
    public ArrayList<String> comparePoints(int user_id) throws Exception {

        // returns responses (multiple points)
        ArrayList<String> messages = new ArrayList<>();

        // import hash map for points ID/collection
        HashMap<Integer, Integer> pointsMap = pointsHashMap(user_id);

        // gets list of point ID's (keys) to use below.
        List<Integer> pointIDList = mapper.getPointsID(user_id);

        for (int pointID : pointIDList) {
            // find value (points_collected) by point ID
            if (pointsMap.containsKey(pointID) == true) {

                // get points collected
                int pointsCollected = pointsMap.get(pointID);

                // finds point ID cap (Deals Services)
                int pointsCap = dealsService.pointsCap(mapper.getDealsID(pointID));

                // compares collected points to cap
                if (pointsCollected % pointsCap == 0) {

                    // if cap is reached, activate reward, reset points and send email for redemption
                    mapper.activateReward(pointID);
                    mapper.resetPoints(pointID);
                    email.sendRewardEmail(pointID);

                    // using email.merchantName (converts point ID > Merchant ID > name)
                    String merchantName = email.merchantName(pointID);

                    // instructions on how to redeem
                    messages.add("You have a reward from " + merchantName + "! Please check your inbox for instructions on how to redeem!");

                    // otherwise update points status:
                } else {
                    // using email.merchantName (converts point ID > Merchant ID > name)
                    String merchantName = email.merchantName(pointID);

                    if (pointsCollected == 0) {
                        messages.add("You're at 0! Start shopping " + merchantName + " to get more points! ");
                    } else if (pointsCollected < 2) {
                        messages.add("A nice start! " + merchantName + " would love to see you again! " +
                                "Points collected : " + pointsCollected + "/" + pointsCap + ".");
                    } else if (pointsCap < 5) {
                        messages.add("You're getting warmer! " + merchantName + " is waiting! " +
                                "Points collected : " + pointsCollected + "/" + pointsCap + ".");
                    } else if (pointsCap < 8) {
                        messages.add("You're almost there!! Wouldn't you love that reward from " + merchantName + "?! " +
                                "Points collected : " + pointsCollected + "/" + pointsCap + ". Nice job!");
                    } else {
                        messages.add("Total points collected from " + merchantName + " : " + pointsCollected + " / " + pointsCap + ". Keep it up!");
                    }
                }
            }

        }

        return messages;
    }

    //for each element in the array list, get the name and id and then put it into the hash map
    public HashMap<Integer, Integer> pointsHashMap(int user_id) {

        ArrayList<PointsHashMap> hashMap = mapper.getPointsCollected(user_id);

        HashMap<Integer, Integer> values = new HashMap<>();

        for (PointsHashMap points : hashMap) {
            int id = points.getId();
            int points_collected = points.getPoints_collected();
            values.put(id, points_collected);
        }
        return values;
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



