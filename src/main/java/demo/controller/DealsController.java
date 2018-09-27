package demo.controller;

/**
 * Created by ashleyalmeida
 */

import com.amazonaws.util.CollectionUtils;
import demo.exceptions.DatabaseException;
import demo.model.CustomResponseObject;
import demo.model.database.Deals;
import demo.services.DealsService;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants")
public class DealsController {

    @Autowired
    DealsService service;


    /**
     * GET all deal information by merchant ID.
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @return list of all deals by merchant ID
     * @throws Exception
     */
    @GetMapping("/{merchant_id}/deals")
    public CustomResponseObject<Deals> getAllDealsByMerchant(@PathVariable("merchant_id") int merchant_id) throws Exception {

        List<Deals> deals = service.findAllDealsByMerchant(merchant_id);

        if (!(CollectionUtils.isNullOrEmpty(deals))) {

            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(deals);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } else throw new DatabaseException("No deals to show.");
    }

    /**
     * GET deal information my merchant ID and by deal ID
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @param id used to return information for this Deal ID
     * @return list of all deal information by merchant ID and deal ID
     * @throws Exception
     */
    @RequestMapping("/{merchant_id}/deals/{id}")
    public CustomResponseObject <Deals> findByID(@PathVariable("merchant_id") int merchant_id,
                                                 @PathVariable("id") int id) throws Exception {

        Deals deal = service.findDealByID(merchant_id, id);

        if (deal != null) {
            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(deal);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } throw new DatabaseException("Deal ID doesn't exist. Please enter correct ID number.");
    }

    /**
     * POST - creates new deal in database
     *
     * @param deals RequestBody - merchant ID, deal description, deal points cap, deal instructions
     * @return deal creation confirmation
     * @throws Exception
     */
    @PostMapping("/deals")
    public CustomResponseObject <Deals> createDeal(@RequestBody Deals deals) throws Exception {

        Deals deal = service.createDeal(deals);

        if (deal == null) throw new DatabaseException("Does not exist.");

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(deal);
        obj.setMessage("success. user created");
        obj.setStatusCode(200);

        return obj;
    }

    /**
     * DELETE deal from database by Merchant ID and Deal ID
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @param id used to return information for this Deal ID
     * @return boolean of successful removal of deal
     * @throws Exception
     */
    @DeleteMapping("/{merchant_id}/deals/id={id}")
    public CustomResponseObject deleteByID(@PathVariable("merchant_id") int merchant_id,
            @PathVariable("id") int id) throws Exception {

        boolean success = service.deleteDealByID(merchant_id, id);

        CustomResponseObject obj = new CustomResponseObject();

        if (success) {
            obj.setData("deal removed");
            obj.setStatusCode(200);
            return obj;
        } else throw new DatabaseException("Unable to remove deal. Please try again later.");
    }

    /**
     * PUT - update deal information my merchant ID and by deal ID
     *
     * @param merchant_id used to retrieve all deal information from database by merchant ID
     * @param id used to return information for this Deal ID
     * @param deals RequestBody - deal description, deal points cap, deal instructions
     * @return returns information submitted for confirmation
     * @throws Exception
     */
    @PutMapping("/{merchant_id}/deals/{id}")
    public CustomResponseObject <Deals> updateDealByID(@PathVariable("merchant_id") int merchant_id,
                                @PathVariable("id") int id,
                                @RequestBody Deals deals) throws Exception {

        service.updateDealByID(merchant_id, id, deals);

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(deals);
        obj.setMessage("successfully updated.");
        obj.setStatusCode(200);

        return obj;
    }


}
