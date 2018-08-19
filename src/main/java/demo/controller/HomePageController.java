package demo.controller;

import demo.services.PlaidAPIServiceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @Autowired
    PlaidAPIServiceInternal plaidApiService;

    /**
     * Home page.
     */
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("PLAID_PUBLIC_KEY", plaidApiService.getEnv().getProperty("PLAID_PUBLIC_KEY"));
        model.addAttribute("PLAID_ENV", plaidApiService.getEnv().getProperty("PLAID_ENV"));
        return "index";
    }
}
