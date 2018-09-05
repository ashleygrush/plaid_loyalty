package demo.mapper;

import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
// links to Plaid - Loyalty lookup table
public interface LoyaltyMapper {

    // list of all points
    String LIST_ALL_POINTS = "Select id, merchant_id, user_id, deal_id, redeemed, points from plaid.loyalty";

    // find user by ID
    String FIND_POINTS_BY_USER_ID = "Select * from plaid.loyalty where user_id = #{user_id}";

    // list points count
    String POINTS_COUNT = "Select points from plaid.loyalty WHERE id = #{id}";

    // Get user ID by Loyalty ID
    String GET_USER_ID_BY_LOYALTY_ID = "Select user_id from plaid.loyalty WHERE id = #{id}";

    // switch redeem to active/true
    String ACTIVATE_REWARD = "Update plaid.loyalty " +
            "SET active = #{true} " +
            "WHERE id = #{id}";

    // deactivate redeem - switch to false
    String DEACTIVATE_REWARD  = "Update plaid.loyalty " +
            "SET activate = #{false}" +
            "WHERE id = #{id}";

    // switch redeem to active/true
    String ACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{true} " +
            "WHERE id = #{id}";

    // deactivate redeem - switch to false
    String DEACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{false}" +
            "WHERE id = #{id}";

    // find email by user ID.
    String USER_EMAIL_BY_ID = "Select email from plaid.users WHERE id = #{id}";

    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // returns user by ID number
    @Select(FIND_POINTS_BY_USER_ID)
    List<Loyalty> findPointsByUserID(int id);

    @Select(POINTS_COUNT)
    int loyaltyCount(int id);

    @Select(GET_USER_ID_BY_LOYALTY_ID)
    int userIdByLoyaltyID(int id);

    // USED IN LOYALTY SERVICE FOR EMAIL PURPOSES
    @Select(USER_EMAIL_BY_ID)
    String userEmailByID(int id);

    @Update(ACTIVATE_REWARD)
    boolean activateReward(int id);

    @Update(DEACTIVATE_REWARD)
    boolean deactivateAward(int id);

    @Update(ACTIVATE_REDEEMED)
    boolean activateRedeemed(int id);

    @Update(DEACTIVATE_REDEEMED)
    boolean deactivateRedeemed(int id);




// REMOVE IF NO LONGER NEEDED

//    // NO LONGER NEEDED???
//    String GET_TRANSACTIONS_BY_ID = "Select redeemed from plaid.loyalty WHERE id = #{id}";

//    @Select(GET_TRANSACTIONS_BY_ID)
//    boolean getTransactionsByID(int id);


//    //update points in DB per transaction
//    String UPDATE_POINTS_BY_ID = "Update plaid.loyalty SET points = #{points} WHERE id = #{id}";

//    @Update(UPDATE_POINTS_BY_ID)
//    int updatePoints(Loyalty loyalty);
}
