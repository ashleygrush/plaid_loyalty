//package demo.services;
//
//import demo.mapper.LoyaltyMapper;
//import demo.model.database.DBSearch;
//import demo.model.database.Loyalty;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class LoyaltyService {
//
//    @Autowired
//    LoyaltyMapper mapper;
//
//    // GET - points/Loyalty status: customers (all)
//    public List<Loyalty> findAllPoints() {
//        return mapper.listAllPoints();
//    }
//
//    // GET - points by user ID.
//    public DBSearch findPointsByID(int id) {
//
//        DBSearch searchID = new DBSearch();
//
//        searchID.setId(id);
//
//        searchID.setLoyalty(mapper.findPointsByID(id));
//
//        return searchID;
//    }
//
//    // GET - all user transactions
//
//    // PUT - if merchant ID is found - - update if needed
//    public int getRandomNumber(int min, int max){
//        Random random = new Random();
//        return random.ints(min,(max+1)).findFirst().getAsInt();
//    }
//
//    public int addPoint() {
//        if (  != null){
//            mapper.updatePoints++;
//        }
//    return mapper.updatePoints;
//    }
//
//    // PUT - if deal used; switch from active to inactive
//
//    // DELETE - if user no longer exists, delete points.
//
//
//    // for each transaction; search hash map (merchant ID)
//
//
//        // if merchant ID is found - - update if needed
//        // check customer ID Loyalty points % for new point value (how many stars)
//        // if Loyalty points reaches limit > email user notification
//}
