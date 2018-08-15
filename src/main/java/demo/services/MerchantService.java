package demo.services;

import demo.mapper.PlaidDatabase;
import demo.model.database.Merchants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    
    @Autowired
    PlaidDatabase plaidDatabase;

    // GET - all Merchants
    public List<Merchants> findAllMerchants() {
        // import merchants
        return plaidDatabase.listAllMerchants();
    }
}
