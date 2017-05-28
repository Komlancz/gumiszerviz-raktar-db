package com.komlancz.gumiszerviz.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "allapotok")
public class States implements Serializable{

    @Id
    @Column
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
