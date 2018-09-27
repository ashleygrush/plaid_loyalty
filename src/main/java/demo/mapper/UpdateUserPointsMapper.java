package demo.mapper;

/**
 * Created by ashleyalmeida
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UpdateUserPointsMapper {

    String UPDATE_USER_POINTS_BY_MERCHANT = "UPDATE plaid.loyalty SET points_collected = points_collected + 1 " +
            "WHERE merchant_id = #{merchant_id} AND user_id = #{user_id}";

    @Update(UPDATE_USER_POINTS_BY_MERCHANT)
    int UpdateUserPointsByMerchant(@Param("merchant_id") int merchant_id,
                                      @Param("user_id") int user_id);


}
