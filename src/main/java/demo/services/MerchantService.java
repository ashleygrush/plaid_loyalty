package demo.services;

import demo.mapper.MerchantMapper;
import demo.model.database.Merchants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    
    @Autowired
    MerchantMapper mapper;

    // GET - all Merchants
    public List<Merchants> findAllMerchants() {
        // import merchants
        return mapper.listAllMerchants();
    }
}
