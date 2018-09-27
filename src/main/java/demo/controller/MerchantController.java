package demo.controller;

/**
 * Created by ashleyalmeida
 */

import com.amazonaws.util.CollectionUtils;
import demo.exceptions.DatabaseException;
import demo.model.CustomResponseObject;
import demo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.model.database.Merchants;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping ("/merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    /**
     * DUNCAN - ???
     * GET - all merchants to populate hash map
     *
     * @return returns list of merchants for hash map
     */
    @RequestMapping ("/testHashMap")
    public HashMap<String, Integer> testHashMap(){
        return merchantService.merchantsList();
    }

    /**
     * GET - all merchants from database with ID number, name, email, and password.
     *
     * @return list of all merchants in database
     * @throws Exception
     */
    @GetMapping("/all")
    public CustomResponseObject <Merchants> getMerchants() throws Exception {

        List<Merchants> merchants = merchantService.findAllMerchants();

        if (!(CollectionUtils.isNullOrEmpty(merchants))) {

            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(merchants);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } else throw new DatabaseException("No merchants to show.");
    }


    /**
     * GET - retrieves merchant by unique ID
     *
     * @param id unique id assigned to merchant
     * @return all merchant information in database
     * @throws Exception
     */
    @RequestMapping("/{id}")
    public CustomResponseObject<Merchants> findByID(@PathVariable("id") int id) throws Exception {
        Merchants merchant = merchantService.findMerchantByID(id);

        if (merchant != null) {
            CustomResponseObject obj = new CustomResponseObject();

            obj.setData(merchant);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;
        }

        else throw new DatabaseException("Merchant ID doesn't exist. Please enter correct ID number or create new account.");
    }

    /**
     * POST - create new merchant in database
     *
     * @param merchants RequestBody - name, email and password
     * @return merchant creation confirmation
     * @throws Exception
     */
    @PostMapping()
    public CustomResponseObject <Merchants> createMerchant(@RequestBody Merchants merchants) throws Exception {

       merchantService.createMerchant(merchants);

       if (merchants == null) throw new DatabaseException("Does not exist.");

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(merchants);
        obj.setMessage("success.");
        obj.setStatusCode(200);

        return obj;
    }

    /**
     * DELETE - removes merchant from database by unique ID
     *
     * @param id unique ID removes merchant from database
     * @return boolean of successful removal of merchant
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public CustomResponseObject deleteByID(@PathVariable("id") int id) throws Exception {
        boolean success = merchantService.deleteMerchantByID(id);
        CustomResponseObject obj = new CustomResponseObject();

        if (success) {
            obj.setData("merchant removed");
            obj.setStatusCode(200);
            return obj;
        } else throw new DatabaseException("Unable to remove merchant. Please try again later.");

    }


    /**
     * PUT - updates merchant in database by unique ID
     *
     * @param id unique ID to update merchant in database
     * @param merchants RequestBody to fill out new information for merchant
     * @return returns information submitted for confirmation
     * @throws Exception
     */
    @PutMapping("/{id}")
    public CustomResponseObject<Merchants> updateMerchantByID(@PathVariable("id") int id,
                                                                @RequestBody Merchants merchants) throws Exception {
        merchantService.updateMerchantByID(id, merchants);

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(merchants);
        obj.setMessage("successfully updated.");
        obj.setStatusCode(200);

        return obj;
    }
}
