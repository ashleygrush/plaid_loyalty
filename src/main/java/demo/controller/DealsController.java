package demo.controller;


import demo.model.database.Deals;
import demo.services.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
public class DealsController {

    @Autowired
    DealsService service;

    // calls DB for all deals
    @GetMapping("/all")
    public List<Deals> getAllDeals(){
        return service.findAllDeals();
    }

    // calls DB for deals by ID number
    @RequestMapping("/id={id}")
    public List<Deals> findByID(@PathVariable("id") int id) {
        return service.findDealByID(id);
    }

    // creates new deal
    @PostMapping
    public Deals createDeal(@RequestBody Deals deals) {
        return service.createDeal(deals);
    }

    //delete deal by ID
    @DeleteMapping("/id={id}")
    public String deleteByID(@PathVariable("id") int id) {
        return service.deleteDealByID(id);
    }

    // update existing deal by ID
    @PutMapping("/merchant_id={merchant_id}/id={id}")
    public Deals updateDealByID(@PathVariable("merchant_id") int merchant_id,
                                @PathVariable("id") int id,
                                @RequestBody Deals deals) {
        return service.updateDealByID(merchant_id, id, deals);
    }

    // use merchant ID to find deals
    @GetMapping("/merchant_id={merchant_id}")
    public List<Deals> getAllDealsByMerchant(@PathVariable("merchant_id") int merchant_id) {
        return service.findAllDealsByMerchant(merchant_id);
    }

}
