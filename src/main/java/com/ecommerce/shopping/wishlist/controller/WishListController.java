package com.ecommerce.shopping.wishlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WishListController {

    @GetMapping
    public String test() {
        return  "Welcome To Ecommerce Shopping app";
    }

}
