package com.komlancz.gumiszerviz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarInfo implements Serializable {


    private static final long serialVersionUID = -8040206495598145050L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carInfoId;

    @JoinColumn(name = "rendszam")
    private String licencePlate;

    @OneToOne
    @JoinColumn(name = "cegId")
    private Company company;

    @JoinColumn(name = "teli_meret")
    private String winterSize;

    @JoinColumn(name = "teli_marka")
    private String winterBrand;

    @JoinColumn(name = "nyari_meret")
    private String summerSize;

    @JoinColumn(name = "nyari_marka")
    private String summerBrand;

    @JoinColumn(name = "felnis")
    private boolean onWheel;

    @JoinColumn(name = "megjegyzes")
    private String comment;

    @OneToOne
    @JoinColumn(name = "allapotId")
    private States state;

    @OneToOne
    @JoinColumn(name = "payId")
    private PaidState paidStateId;

    @JoinColumn(name = "pozicio")
    private String position;

    public Integer getCarInfoId() {
        return carInfoId;
    }

    public void setCarInfoId(Integer carInfoId) {
        this.carInfoId = carInfoId;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getWinterSize() {
        return winterSize;
    }

    public void setWinterSize(String winterSize) {
        this.winterSize = winterSize;
    }

    public String getWinterBrand() {
        return winterBrand;
    }

    public void setWinterBrand(String winterBrand) {
        this.winterBrand = winterBrand;
    }

    public String getSummerSize() {
        return summerSize;
    }

    public void setSummerSize(String summerSize) {
        this.summerSize = summerSize;
    }

    public String getSummerBrand() {
        return summerBrand;
    }

    public void setSummerBrand(String summerBrand) {
        this.summerBrand = summerBrand;
    }

    public boolean isOnWheel() {
        return onWheel;
    }

    public void setOnWheel(boolean onWheel) {
        this.onWheel = onWheel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public PaidState getPaidStateId() {
        return paidStateId;
    }

    public void setPaidStateId(PaidState paidStateId) {
        this.paidStateId = paidStateId;
    }
}
