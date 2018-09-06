package demo.services;

import demo.mapper.PlaidMapper;
import demo.model.MerchantsForHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import demo.mapper.MerchantMapper;
import demo.model.database.Merchants;

import java.util.List;

@Service
public class MerchantService {

    //creation of the merchants hashmap
    @Autowired
    PlaidMapper plaidMapper;

    @Autowired
    MerchantMapper mapper;

    //for each element in the array list, get the name and id and then put it into the hashmap
    public HashMap<String, Integer> merchantsList() {

        ArrayList<MerchantsForHashMap> list = plaidMapper.selectAllMerchants();

        HashMap<String, Integer> codes = new HashMap<>();

        for (MerchantsForHashMap merchant: list) {
            String a = merchant.getName();
            int b = merchant.getId();
            codes.put(a, b);
        }
        return codes;
    }


    // GET - all Merchants
    public List<Merchants> findAllMerchants() {
        return mapper.listAllMerchants();
    }

    // GET - find merchant by ID
    public List<Merchants> findMerchantByID(int id) {
        return mapper.findMerchantByID(id);
    }


    // POST - create new merchant
    public Merchants createMerchant(Merchants data) {

        Merchants newMerch = new Merchants();

        newMerch.setName(data.getName());
        newMerch.setPassword(data.getPassword());
        newMerch.setEmail(data.getEmail());

        try {
            mapper.createMerchant(newMerch);
        } catch (Exception e) {
            System.out.println("Merchant already exists. Please log in : " + data.getEmail());
        }

        return newMerch;
    }

    // DELETE - delete existing merchant by ID
    public String deleteMerchantByID(int id) {
        mapper.deleteMerchantByID(id);

        return "Merchant successfully removed with ID : " + id + ".";
    }

    // PUT - update existing merchant by ID
    public Merchants updateMerchantByID(int id, Merchants data) {

        Merchants updateMerch = new Merchants();

        updateMerch.setName(data.getName());
        updateMerch.setPassword(data.getPassword());
        updateMerch.setEmail(data.getEmail());
        updateMerch.setId(id);

        mapper.updateMerchantByID(updateMerch);

        return updateMerch;
    }

    // gets merchant name by ID
    public String merchantNameById(int id) {
        return mapper.getMerchantNameById(id);
    }

}
