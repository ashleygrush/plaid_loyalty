package demo.controller;


import demo.model.database.DBSearch;
import demo.model.database.Deals;
import demo.services.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants/")
public class DealsController {

    @Autowired
    DealsService service;

    // calls DB for all deals
    @GetMapping("/deals/all")
    public List<Deals> getAllDeals() {
        return service.findAllDeals();
    }

    // calls DB for deals by ID number
    @RequestMapping("/deals/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        return service.findDealByID(id);
    }

    // creates new deal
    @PostMapping("/deals")
    public Deals createDeal(@RequestBody Deals deals) {
        return service.createDeal(deals);
    }

    //delete deal by ID
    @DeleteMapping("/deals/{id}")
    public DBSearch deleteByID(@PathVariable("id") int id) {
        return service.deleteDealByID(id);
    }

    // update existing deal by ID
    @PutMapping("/deals/{id}")
    public Deals updateDealByID(@PathVariable("id") int id,
                                @RequestBody Deals deals) {
        return service.updateDealByID(id, deals);
    }

}
