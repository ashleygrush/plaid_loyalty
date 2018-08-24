package demo.mapper;

import demo.model.MerchantsForHashMap;
import demo.model.database.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;
import java.util.List;

@Mapper

public interface PlaidMapper {

    String SELECT_ALL_MERCHANTS = "SELECT id, name FROM `plaid`.merchants";

    String LIST_ALL_USERS = "Select id, name, email from plaid.users";

    @Select(SELECT_ALL_MERCHANTS)
    public ArrayList<MerchantsForHashMap> selectAllMerchants();

    @Select(LIST_ALL_USERS)
    List<Users> listAllUsers();

}
