package com.komlancz.gumiszerviz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "elszamolas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PaidState implements Serializable{


    private static final long serialVersionUID = 4808511057046396009L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name = "payId")
    private Integer paidId;

    @JoinColumn(name = "fizetos")
    private String paid;

    public Integer getPaidId() {
        return paidId;
    }

    public void setPaidId(Integer paidId) {
        this.paidId = paidId;
    }

    public String  getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

}
