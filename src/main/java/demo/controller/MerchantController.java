package demo.controller;

import demo.model.database.DBSearch;
import demo.model.database.Merchants;
import demo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loyalty")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    // calls DB for all Merchants
    @GetMapping("/merchants")
    public List<Merchants> getMerchants() {

        // searches for database link in services
        return merchantService.findAllMerchants();
    }
    // calls DB for merchant by ID number
    @RequestMapping("/merchants/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        // searches for database link by ID
        return merchantService.findMerchantByID(id);
    }

    // creates new merchant
    @PostMapping("/merchants")
    public Merchants createUser(@RequestBody Merchants merchants) {

        return merchantService.createMerchant(merchants);
    }

    //delete existing merchant by ID
    @DeleteMapping("/merchants/{id}")
    public DBSearch deleteByID(@PathVariable("id") int id) {

        return merchantService.deleteMerchantByID(id);
    }

    // update existing merchant by ID
    @PutMapping("/merchants/{id}")
    public Merchants updateUserByID(@PathVariable("id") int id,
                                @RequestBody Merchants merchants) {

        return merchantService.updateMerchantByID(id, merchants);
    }


}

