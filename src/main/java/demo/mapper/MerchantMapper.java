package demo.mapper;

import demo.model.database.Merchants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
// maps to Plaid Database - Merchant table
public interface MerchantMapper {

    // list of all Merchants
    String LIST_ALL_MERCHANTS = "Select id, name, email from plaid.merchants";


    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_MERCHANTS)
    List<Merchants> listAllMerchants();
}
