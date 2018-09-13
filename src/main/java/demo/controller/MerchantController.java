package demo.controller;

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

    @RequestMapping ("/testHashMap")
    public HashMap<String, Integer> testHashMap(){
        return merchantService.merchantsList();
    }

    // calls DB for all Merchants
    @GetMapping("/all")
    public CustomResponseObject <Merchants> getMerchants() {

        List <Merchants> merchants = merchantService.findAllMerchants();

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(merchants);
        obj.setError("success.");
        obj.setStatusCode(200);

        return obj;
    }

    // calls DB for merchant by ID number
    @RequestMapping("/id={id}")
    public CustomResponseObject<Merchants> findByID(@PathVariable("id") int id) throws Exception {
        Merchants merchant = merchantService.findMerchantByID(id);

        if (merchant != null) {
            CustomResponseObject obj = new CustomResponseObject();

            obj.setData(merchant);
            obj.setError("success.");
            obj.setStatusCode(200);

            return obj;
        }

        else throw new DatabaseException("Merchant ID doesn't exist. Please enter correct ID number or create new account.");
    }

    // creates new merchant
    @PostMapping()
    public CustomResponseObject <Merchants> createMerchant(@RequestBody Merchants merchants) throws Exception {

       merchantService.createMerchant(merchants);

       if (merchants == null) throw new DatabaseException("Does not exist.");

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(merchants);
        obj.setError("success.");
        obj.setStatusCode(200);

        return obj;
    }

    //delete existing merchant by ID
    @DeleteMapping("/id={id}")
    public CustomResponseObject deleteByID(@PathVariable("id") int id) throws Exception {
        boolean success = merchantService.deleteMerchantByID(id);
        CustomResponseObject obj = new CustomResponseObject();

        if (success) {
            obj.setData("merchant removed");
            obj.setStatusCode(200);
            return obj;
        } else throw new DatabaseException("Unable to remove merchant. Please try again later.");

    }


    // update existing merchant by ID
    @PutMapping("/id={id}")
    public CustomResponseObject<Merchants> updateMerchantByID(@PathVariable("id") int id,
                                                                @RequestBody Merchants merchants) throws Exception {
        merchantService.updateMerchantByID(id, merchants);

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(merchants);
        obj.setError("successfully updated.");
        obj.setStatusCode(200);

        return obj;
    }
}
