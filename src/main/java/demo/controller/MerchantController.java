package demo.controller;

import demo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping ("/merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @RequestMapping ("/testHashMap")
    public HashMap<String, Integer> testHashMap(){
        return merchantService.merchantsList();

    }
}
