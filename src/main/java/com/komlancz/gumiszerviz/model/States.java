package com.komlancz.gumiszerviz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class States implements Serializable{


    private static final long serialVersionUID = -4437601252751678490L;
    @Id
    @JoinColumn(name = "allapotId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    @JoinColumn
    private String stateText;

//    @OneToOne(mappedBy = "state")
//    private CarInfo carInfo;


    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

//    public CarInfo getCarInfo() {
//        return carInfo;
//    }
//
//    public void setCarInfo(CarInfo carInfo) {
//        this.carInfo = carInfo;
//    }
}
