package demo.controller;

import demo.services.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @Autowired
    AccessToken accessToken;

    /**
     * Home page.
     */
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("PLAID_PUBLIC_KEY", accessToken.getEnv().getProperty("PLAID_PUBLIC_KEY"));
        model.addAttribute("PLAID_ENV", accessToken.getEnv().getProperty("PLAID_ENV"));
        return "index";
    }
}
