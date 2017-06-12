package com.komlancz.gumiszerviz.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void index(@RequestBody String valami){
        JSONObject jsonObject = new JSONObject(valami);
        System.out.println("Hello " + jsonObject.get("name"));
    }
}
