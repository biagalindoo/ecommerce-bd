package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /**
     * Página principal do dashboard
     */
    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "dashboard/index";
    }
}