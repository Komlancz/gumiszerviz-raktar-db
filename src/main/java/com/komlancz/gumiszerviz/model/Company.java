package com.komlancz.gumiszerviz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nev_ceg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company implements Serializable{


    private static final long serialVersionUID = -3084925307795293925L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "cegId")
    private Integer companyId;

    @JoinColumn(name = "nev")
    private String name;

    @JoinColumn(name = "cim")
    private String address;

    @JoinColumn(name = "telefon")
    private String phone;

    @JoinColumn(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
