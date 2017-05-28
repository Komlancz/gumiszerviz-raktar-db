package com.komlancz.gumiszerviz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(){
        System.out.println("Hello");
    }
}
