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

    String GET_TRANSACTIONS_BY_ID = "Select redeemed from plaid.loyalty WHERE id = #{id}";



    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // returns user by ID number
    @Select(FIND_POINTS_BY_USER_ID)
    List<Loyalty> findPointsByUserID(int user_id);

    @Select(POINTS_COUNT)
    int loyaltyCount(int id);

    @Update(ACTIVATE_REWARD)
    boolean activateReward(int id);

    @Update(DEACTIVATE_REWARD)
    boolean deactivateAward(int id);

    @Update(ACTIVATE_REDEEMED)
    boolean activateRedeemed(int id);

    @Update(DEACTIVATE_REDEEMED)
    boolean deactivateRedeemed(int id);

    @Select(GET_TRANSACTIONS_BY_ID)
    boolean getTransactionsByID(int id);


// REMOVE IF NO LONGER NEEDED

//    //update points in DB per transaction
//    String UPDATE_POINTS_BY_ID = "Update plaid.loyalty SET points = #{points} WHERE id = #{id}";

//    @Update(UPDATE_POINTS_BY_ID)
//    int updatePoints(Loyalty loyalty);
}
