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
    public List<Merchants> getMerchants() {
        return merchantService.findAllMerchants();
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

        else throw new DatabaseException("ID does not exist.");
    }

    // creates new merchant
    @PostMapping()
    public Merchants createMerchant(@RequestBody Merchants merchants) {
        return merchantService.createMerchant(merchants);
    }

    //delete existing merchant by ID
    @DeleteMapping("/id={id}")
    public String deleteByID(@PathVariable("id") int id) {
        return merchantService.deleteMerchantByID(id);
    }

    // update existing merchant by ID
    @PutMapping("/id={id}")
    public Merchants updateMerchantByID(@PathVariable("id") int id,
                                @RequestBody Merchants merchants) {
        return merchantService.updateMerchantByID(id, merchants);
    }
}
