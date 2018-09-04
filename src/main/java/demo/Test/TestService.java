package demo.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestService {

    @Autowired
    TestMapper mapper;

    int counter = 0;

//    // GET - all user transactions
//    // for each transaction; search hash map (merchant ID)
//    // PUT - if merchant ID is found - update if needed
//    public String searchHashMapForMerchantID(int merchant_id, int user_id) {
//
////        // import hash map
////        HashMap<Integer, Integer> map = new HashMap();
////        counter = 0;
//
////        // search for key in hash map
////        if (map.containsKey(merchant_id)) {
////
////            // if merchant is found, search for new transactions
////            if (map.get(2) != null) {
//        if (merchant_id != 0) {
//            // counter tracks number of transactions hit > added to update points
//            counter++;
//            System.out.println("Number of transactions hit : " + counter);
//            updatePoints(mapper.findAllDealsByMerchantID(merchant_id));
//            return "Found participating merchant; updating points.";
//        }
//        // if no new transactions...
//        else {
//            // counter to check for valid transactions
//            System.out.println("Transactions found : " + counter);
//            return "No new transactions found.";
//        }
//    }
////        // if no merchants found...
////        return "No participating merchants found.";
////    }

// TESTING WITH MODULUS VS <=
//    // PUT - if 10 is hit, redeem points (switch to active) > email user notification
//    public String checkRedeemed(int id) {
//
//        // if points are maxed out, activate, reset points and send email.
//        if (mapper.pointsCountByID(id) % 10 == 0) {
//            mapper.activateRedeemed(id);
//            mapper.resetPointsIfActive(id);
//            LoyaltyService.sendInstructionsEmail();
//            return "Please check your inbox for instructions on how to redeem your reward!";
//        }
//
//        // otherwise, send estimated points email. "You're 1 point away from a free drink!"
//        else {
//            LoyaltyService.sendPointsCountEmail();
//            return "Points collected so far : " + mapper.pointsCountByID(id) +
//                    "Please check your inbox for an update on your balance!";
//        }
//    }


    // check customer ID Loyalty points % for new point value (how many stars)
    public String updatePoints(int id){

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
//        if (counter > 0) {
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

        // otherwise, return ID.
        return "No new transactions found.";
    }
}
