package demo.services;

/**
 * Created by ashleyalmeida
 */

import demo.exceptions.DatabaseException;
import demo.mapper.DealsMapper;
import demo.model.database.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealsService {

    @Autowired
    DealsMapper mapper;

    /**
     * Service finds and retrieves information from database on all deals by Merchant ID.
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @return list of information on all deals.
     */
    public List<Deals> findAllDealsByMerchant(int merchant_id) {
        return mapper.listAllDealsByMerchant(merchant_id);
    }


    /**
     * Service gets all deal information by merchant ID and user ID
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @param id used to return information for this Deal ID
     * @return all information about deal
     * @throws Exception
     */
    public Deals findDealByID(int merchant_id, int id) throws Exception {

        Deals deal;

        try {
           deal = mapper.findDealByID(merchant_id, id);
        } catch (Exception e) {
            throw e;
        }
        return deal;
    }


    /**
     * Service posts new deal to database
     *
     * @param data enters merchant ID, deal description, deal points cap, deal instructions into database
     * @return returns the newly submitted information back for confirmation
     * @throws Exception
     */
    public Deals createDeal(Deals data) throws Exception {

        try {
        Deals newDeal = new Deals();

        newDeal.setMerchant_id(data.getMerchant_id());
        newDeal.setDeal_description(data.getDeal_description());
        newDeal.setDeal_points_cap(data.getDeal_points_cap());
        newDeal.setDeal_instructions(data.getDeal_instructions());

        mapper.createDeal(newDeal);
        return newDeal;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Service removes deal from database by merchant ID and deal ID
     *
     * @param merchant_id used to retrieve deal information from database by merchant ID
     * @param id used to remove information for this Deal ID
     * @return boolean to confirm success of removing deal
     * @throws Exception
     */
    public Boolean deleteDealByID(int merchant_id, int id) throws Exception {
        try {
            mapper.deleteDealByID(merchant_id, id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Service updates deal in database by merchant ID and deal ID
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @param id used to update information for this Deal ID
     * @param data updates deal description, deal points cap, deal instructions in database
     * @return returns the newly submitted information back for confirmation
     * @throws Exception
     * @throws DatabaseException custom exception handling if ID can't be updated.
     */
    public Deals updateDealByID(int merchant_id, int id, Deals data) throws Exception, DatabaseException {

        Deals updateDeal = new Deals();

        updateDeal.setId(id);
        updateDeal.setMerchant_id(merchant_id);
        updateDeal.setDeal_description(data.getDeal_description());
        updateDeal.setDeal_points_cap(data.getDeal_points_cap());
        updateDeal.setDeal_instructions(data.getDeal_instructions());
        try {
            mapper.updateDealByID(updateDeal);
            if (id < 1) {
                throw new DatabaseException("Unable to update deal with ID : " + id);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return updateDeal;
    }


    /**
     * Service returns points cap for loyalty points update method (Loyalty Services)
     *
     * @param id Deal ID to get points cap
     * @return int points_cap
     */
    public int pointsCap(int id){
        return mapper.pointsCap(id);
    }

    /**
     * Service gets deal instructions for email messages method (Email Services)
     *
     * @param id Deal ID to get deal instructions
     * @return string deal_instructions to populate email body.
     */
    public String getDealInstructions(int id) {
        return mapper.getDealInstructions(id);
    }
}
