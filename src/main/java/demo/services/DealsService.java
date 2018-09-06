package demo.services;

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
    public List<Deals> findAllDeals() {
        return mapper.listAllDeals();
    }


    // GET - find deal by ID
    public List<Deals> findDealByID(int id) {
        return mapper.findDealByID(id);
    }


    // POST - create new deal
    public Deals createDeal(Deals data) {

        Deals newDeal = new Deals();

        newDeal.setMerchant_id(data.getMerchant_id());
        newDeal.setDeal_description(data.getDeal_description());
        newDeal.setDeal_points_cap(data.getDeal_points_cap());
        newDeal.setDeal_instructions(data.getDeal_instructions());

        try {
            mapper.createDeal(newDeal);
        } catch (Exception e) {
            System.out.println("Deal already exists." + data.getDeal_description());
        }
        return newDeal;
    }

    // DELETE - delete existing deal by ID
    public String deleteDealByID(int id) {
        mapper.deleteDealByID(id);
        return "Deal successfully removed with ID : " + id + ".";
    }

    // PUT - update existing deal by ID
    public Deals updateDealByID(int merchant_id, int id, Deals data) {

        Deals updateDeal = new Deals();

        updateDeal.setId(id);
        updateDeal.setMerchant_id(merchant_id);
        updateDeal.setDeal_description(data.getDeal_description());
        updateDeal.setDeal_points_cap(data.getDeal_points_cap());
        updateDeal.setDeal_instructions(data.getDeal_instructions());

        mapper.updateDealByID(updateDeal);

        return updateDeal;
    }

    // GET - all Deals
    public List<Deals> findAllDealsByMerchant(int merchant_id) {
        return mapper.listAllDealsByMerchant(merchant_id);
    }

    // RETURNS points cap
    public int pointsCap(int id){
        return mapper.pointsCap(id);
    }

    public String getDealInstructions(int id) {
        return mapper.getDealInstructions(id);
    }
}
