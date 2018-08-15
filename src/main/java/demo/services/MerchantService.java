package demo.services;

import demo.mapper.MerchantMapper;
import demo.model.database.DBSearch;
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

    // GET - find merchant by ID
    public DBSearch findMerchantByID(int id) {

        DBSearch searchID = new DBSearch();

        searchID.setId(id);

        searchID.setUsers(mapper.findMerchantByID(id));

        return searchID;
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
    public DBSearch deleteMerchantByID(int id) {

        DBSearch removeID = new DBSearch();

        // compare DB ID (removeID) to searched "id" (id)
        if (removeID.getId() == id) ;
        {
            // remove ID from DB
            removeID.setId(mapper.deleteMerchantByID(id));
        }
        // if no results; return DB ID (remove ID)
        return removeID;
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

}
