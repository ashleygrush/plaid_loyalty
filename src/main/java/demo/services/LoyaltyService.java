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


    /**
     * Service that lists all points in database
     *
     * @return List all points in the database
     */
    public List<Loyalty> findAllPoints() {
        return mapper.listAllPoints();
    }


    /**
     * Service finds all loyalty points by User ID
     *
     * @param user_id used to find all loyalty points
     * @return list of loyalty points and information
     * @throws Exception
     */
    public List<Loyalty> findByUserID(int user_id) throws Exception {

        List<Loyalty> userPoints;

        try {
            userPoints = mapper.findPointsByUserID(user_id);
        } catch (Exception e) {
            throw e;
        }
        return userPoints;
    }


    /**
     * Service that compares points for reward status information.
     *
     * Service retrieves loyalty points ID and points_collected from the database
     * and adds them into a hash map.  The hash map is then compared against a
     * list of points ID's (keys) to get the results of all points_collected for that
     * user.  (This is needed if a user has more that one loyalty program).
     * Once the key is found, it is then used to retrieve the points_cap from the
     * database and compare points_collected / points_cap to see the result.
     *
     * If cap is reached: the loyalty point ID is then switched to active, the
     * collected_points are reset to 0, and an email is sent to the user with
     * instructions on how to redeem (QR code for redemption).
     * A short message is then returned to check user's inbox.
     *
     * If cap is NOT reached, the points_collected then goes through the if/else
     * ladder for a message relating to points percentage of completion. This message is
     * then also returned to the user for a status update.
     *
     * @param user_id used to populate a hash map of collected_points and points ID
     *                with User ID. Then used to put all points ID into a list.
     * @return message to user with information on points_collected status or redemption
     *                instructions.
     * @throws Exception
     *
     *  needs exception for null User_ID
     */
    public ArrayList<String> comparePoints(int user_id) throws Exception {

        // returns responses (multiple points)
        ArrayList<String> messages = new ArrayList<>();

        // import hash map for points ID/collection
        HashMap<Integer, Integer> pointsMap = pointsHashMap(user_id);

        // gets list of point ID's (keys) to use below.
        List<Integer> pointIDList = mapper.getPointsID(user_id);

        for (int pointID : pointIDList) {
            // find value (points_collected) by point ID
            if (pointsMap.containsKey(pointID)) {

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
                    } else if (pointsCollected < 5) {
                        messages.add("You're getting warmer! " + merchantName + " is waiting! " +
                                "Points collected : " + pointsCollected + "/" + pointsCap + ".");
                    } else if (pointsCollected < 8) {
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


    /**
     * HashMap for points - Key: points ID / value: points_collected.
     *
     * @param user_id used to pull points ID and points_collected per user ID
     * @return hash map with points ID / points_collected
     */
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


    /**
     * IN PROGRESS!!
     *
     * Service checks for active rewards; if redeemed the reward is deactivated
     * and returns a status message. If reward is active and not redeemed; a status
     * message is returned and redemption email with instructions is re-sent to user
     * email address.
     *
     * @param user_id needed to find all rewards then have been activated
     * @return message to user if reward is active or not.
     */
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



