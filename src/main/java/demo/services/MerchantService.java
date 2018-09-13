package demo.services;

import demo.exceptions.DatabaseException;
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

    //creation of the merchants hash map
    @Autowired
    PlaidMapper plaidMapper;

    @Autowired
    MerchantMapper mapper;

    //for each element in the array list, get the name and id and then put it into the hash map
    public HashMap<String, Integer> merchantsList() {

        ArrayList<MerchantsForHashMap> list = plaidMapper.selectAllMerchants();

        HashMap<String, Integer> values = new HashMap<>();

        for (MerchantsForHashMap merchant : list) {
            String name = merchant.getName();
            int id = merchant.getId();
            values.put(name, id);
        }
        return values;
    }


    // GET - all Merchants
    public List<Merchants> findAllMerchants() {
        return mapper.listAllMerchants();
    }

    // GET - find merchant by ID
    public Merchants findMerchantByID(int id) throws Exception {

        Merchants merchants;

        try {
            merchants = mapper.findMerchantByID(id);
        } catch (Exception e) {
            throw e;
        }
        return merchants;
    }


    // POST - create new merchant
    public Merchants createMerchant(Merchants data) throws Exception {

        try {
            Merchants newMerch = new Merchants();

            newMerch.setName(data.getName());
            newMerch.setPassword(data.getPassword());
            newMerch.setEmail(data.getEmail());

            mapper.createMerchant(newMerch);
            return newMerch;

        } catch (Exception e) {
            throw e;
        }
    }

    // DELETE - delete existing merchant by ID
    public boolean deleteMerchantByID(int id) throws Exception {
        try {
            mapper.deleteMerchantByID(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // PUT - update existing merchant by ID
    public Merchants updateMerchantByID(int id, Merchants data) throws Exception, DatabaseException {

        Merchants updateMerch = new Merchants();

        updateMerch.setName(data.getName());
        updateMerch.setPassword(data.getPassword());
        updateMerch.setEmail(data.getEmail());
        updateMerch.setId(id);
        try {
            mapper.updateMerchantByID(updateMerch);

            if (id < 1) {
                throw new DatabaseException("Unable to update merchant with ID : " + id);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return updateMerch;
    }

    // gets merchant name by ID
    public String merchantNameById(int id) {
        return mapper.getMerchantNameById(id);
    }

}
