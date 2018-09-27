package demo.services;

/**
 * Created by ashleyalmeida
 */

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

    /**
     * Service used to populate hash map with Merchant name and ID
     * for each element in the array list, get the name and id and then put it into the hash map
     *
     * @return hash map values: merchant name (key), merchant ID (value)
     */
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


    /**
     * Service finds and retrieves information from database on all merchants.
     *
     * @return list of information on all merchants.
     */
    public List<Merchants> findAllMerchants() {
        return mapper.listAllMerchants();
    }

    /**
     * Service finds and retrieves all merchant information from database by unique ID
     *
     * @param id unique ID assigned to merchant
     * @return all information about merchant
     * @throws Exception
     */
    public Merchants findMerchantByID(int id) throws Exception {

        Merchants merchants;

        try {
            merchants = mapper.findMerchantByID(id);
        } catch (Exception e) {
            throw e;
        }
        return merchants;
    }


    /**
     * Service adds new merchant information into database - name, email, password.
     *
     * @param data enters name, email, password into database
     * @return returns the newly submitted information back for confirmation
     * @throws Exception
     */
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

    /**
     * Service removes merchant from the database by unique ID
     *
     * @param id unique ID to remove merchant
     * @return boolean to confirm success of removing merchant
     * @throws Exception
     */
    public boolean deleteMerchantByID(int id) throws Exception {
        try {
            mapper.deleteMerchantByID(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Service updates merchant information in database by unique ID
     *
     * @param id unique ID to update merchant parameters
     * @param data new information to update in database
     * @return returns updated information to the user for confirmation
     * @throws Exception
     * @throws DatabaseException custom exception handling if ID doesn't exist.
     */
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

    /**
     * Service gets merchant name by ID; used when returning a message to user regarding loyalty points (Loyalty Service)
     *
     * @param id unique ID to find merchant name
     * @return String merchant name
     */
    public String merchantNameById(int id) {
        return mapper.getMerchantNameById(id);
    }

}
