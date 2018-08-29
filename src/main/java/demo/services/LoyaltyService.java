package demo.services;

import demo.mapper.LoyaltyMapper;
import demo.model.database.DBSearch;
import demo.model.database.Loyalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class LoyaltyService {

    @Autowired
    LoyaltyMapper mapper;

    int counter;

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

    // GET - all user transactions
    // for each transaction; search hash map (merchant ID)
    // PUT - if merchant ID is found - update if needed
    public String seachHashMapForMerchantID(int merchant_id, int user_id) {

        // import hash map
        HashMap<Integer, Integer> map = new HashMap();
        counter = 0;

        // search for key in hash map
        if (map.containsKey(merchant_id)) {

            // if merchant is found, search for new transactions
            if (map.get(2) != null) {
                // counter tracks number of transactions hit > added to update points
                counter++;
                System.out.println("Number of transactions hit : " + counter);
                updatePoints(mapper.findAllDealsByMerchantID(merchant_id));
                return "Found participating merchant; updating points.";
            }
            // if no new transactions...
            else {
                // counter to check for valid transactions
                System.out.println(counter);
                return "No new transactions found.";
            }
        }
        // if no merchants found...
        return "No participating merchants found.";
    }



    // check customer ID Loyalty points % for new point value (how many stars)
    public String updatePoints(int id){

        //create number generator (1 - 10)
        Random random = new Random();

        // if new transactions exist; generate new number
        if (counter >= 0){
            int updatePoints = random.nextInt((10) + counter);
            mapper.updatePoints(id, updatePoints);

            // check if points have hit cap...
            checkRedeemed(id);
            return "points successfully updated.";
        }

        // otherwise, return ID.
        return "No new transactions found.";
    }


    // PUT - if 10 is hit, redeem points (switch to active) > email user notification
    public String checkRedeemed(int id) {

        // if points are maxed out, activate and send email.
        if (mapper.loyaltyCount(id) >= 10) {
            mapper.activateRedeemed(id);
            LoyaltyService.sendInstructionsEmail();
            return "Please check your inbox for instructions on how to redeem your reward!";
        }

        // otherwise, send estimated points email. "You're 1 point away from a free drink!"
        else {
            LoyaltyService.sendPointsCountEmail();
        }
        return "No rewards available for redemption; please check your inbox for an update!" ;
    }

    // PUT - if deal used; switch from active to inactive


    // sends email that reward is active and instructions to redeem
    private static void sendInstructionsEmail() {
    }

    // sends email with current points count
    private static void sendPointsCountEmail() {
    }

    // DELETE - if user no longer exists, delete points.

}



