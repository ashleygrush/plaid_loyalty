package demo.services;

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

    // GET - all Deals
    public List<Deals> findAllDealsByMerchant(int merchant_id) {
        return mapper.listAllDealsByMerchant(merchant_id);
    }


    // GET - find deal by ID
    public Deals findDealByID(int merchant_id, int id) throws Exception {

        Deals deal;

        try {
           deal = mapper.findDealByID(merchant_id, id);
        } catch (Exception e) {
            throw e;
        }
        return deal;
    }


    // POST - create new deal
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

    // DELETE - delete existing deal by ID
    public Boolean deleteDealByID(int merchant_id, int id) throws Exception {
        try {
            mapper.deleteDealByID(merchant_id, id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // PUT - update existing deal by ID
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


    // RETURNS points cap - for loyalty
    public int pointsCap(int id){
        return mapper.pointsCap(id);
    }

    // get deal instructions > for email messages
    public String getDealInstructions(int id) {
        return mapper.getDealInstructions(id);
    }
}
