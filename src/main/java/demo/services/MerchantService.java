package demo.services;


import demo.mapper.PlaidMapper;
import demo.model.MerchantsForHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MerchantService {


    //creation of the merchants hashmap
    @Autowired
    PlaidMapper plaidMapper;

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
    }}
