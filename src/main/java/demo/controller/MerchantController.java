package demo.controller;

import demo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.model.database.DBSearch;
import demo.model.database.Merchants;
import demo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/merchants")
    public List<Merchants> getMerchants() {
        return merchantService.findAllMerchants();
    }

    // calls DB for merchant by ID number
    @RequestMapping("/merchants/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        return merchantService.findMerchantByID(id);
    }

    // creates new merchant
    @PostMapping("/merchants")
    public Merchants createMerchant(@RequestBody Merchants merchants) {
        return merchantService.createMerchant(merchants);
    }

    //delete existing merchant by ID
    @DeleteMapping("/merchants/{id}")
    public DBSearch deleteByID(@PathVariable("id") int id) {
        return merchantService.deleteMerchantByID(id);
    }

    // update existing merchant by ID
    @PutMapping("/merchants/{id}")
    public Merchants updateMerchantByID(@PathVariable("id") int id,
                                @RequestBody Merchants merchants) {
        return merchantService.updateMerchantByID(id, merchants);
    }
}
