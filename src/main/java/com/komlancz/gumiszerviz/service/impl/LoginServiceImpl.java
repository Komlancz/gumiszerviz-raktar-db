package com.komlancz.gumiszerviz.service.impl;

import com.komlancz.gumiszerviz.model.Admin;
import com.komlancz.gumiszerviz.repository.AdminRepository;
import com.komlancz.gumiszerviz.service.LoginService;
import com.komlancz.gumiszerviz.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    AdminRepository repository;

    @Override
    public boolean checkLoginEmail(String email, String password) {

        try {
            Admin admin = repository.getByEmail(email);

            return isSamePassword(password, admin.getPassword());
        } catch (NullPointerException e) {
            logger.warn("Admin is not found by email: " + email, e);
        }
        return false;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        try {
            Admin admin = repository.getByUsername(username);
            return isSamePassword(password, admin.getPassword());
        } catch (NullPointerException e) {
            logger.warn("Admin is not found by user name: " + username, e);
        }
        return false;
    }

    @Override
    public boolean registerAdmin(Admin newAdmin) {
        try {
            newAdmin.setPassword(PasswordService.passwordHashing(newAdmin.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        repository.save(newAdmin);
        return true;
    }

    private boolean isSamePassword(String currentPassword, String adminPassword){
        try {
            return PasswordService.checkPassword(currentPassword, adminPassword);
        }
        catch (NullPointerException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return null;
    }
}
