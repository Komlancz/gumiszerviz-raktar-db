package com.komlancz.gumiszerviz.controller;

import com.komlancz.gumiszerviz.model.Admin;
import com.komlancz.gumiszerviz.service.LoginService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "Hello ";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Admin login(@RequestBody String loginForm){
        Admin retV = new Admin();
        boolean loggedIn = false;
        JSONObject loginDetails = new JSONObject(loginForm);
        String userName = loginDetails.getString("username");
        String password = loginDetails.getString("password");
        logger.info("Start login | username: " + userName);
        if (!userName.isEmpty() && !password.isEmpty()){
            logger.info("Login admin by username: " + userName);
            loggedIn = (isEmailLogin(userName)) ? service.checkLoginEmail(userName, password) : service.checkLogin(userName, password);
            if (loggedIn) {
                retV = new Admin();
                retV.setUsername(userName);
            }
        }
        logger.info("Logged in: " + loggedIn);
        return retV;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody String registerForm){
        JSONObject regiserDetails = new JSONObject(registerForm);
        Admin newAdmin = new Admin();
        newAdmin.setUsername(regiserDetails.getString("username"));
        newAdmin.setEmail(regiserDetails.getString("email"));
        newAdmin.setPassword(regiserDetails.getString("password"));

        return (service.registerAdmin(newAdmin)) ? "SUCCESS" : "ERROR";
    }

    private boolean isEmailLogin(String userName){
        return userName.contains("@");
    }
}
