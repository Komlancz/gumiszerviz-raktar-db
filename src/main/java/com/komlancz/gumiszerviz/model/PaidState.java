package com.komlancz.gumiszerviz.model;

import javax.persistence.*;

@Entity
@Table(name = "elszamolas")
public class PaidState {

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
