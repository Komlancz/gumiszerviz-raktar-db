package com.komlancz.gumiszerviz.service;

import com.komlancz.gumiszerviz.model.Admin;
import org.springframework.security.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService{
    boolean checkLoginEmail(String email, String password);
    boolean checkLogin(String username, String password);
    boolean registerAdmin(Admin newAdmin);
}
