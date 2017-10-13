package com.komlancz.gumiszerviz.model;

public class LicencePlateHandler {
    public static String handleLicencePlate(String licencePlate){
        licencePlate = licencePlate.replace(" ", "");
        licencePlate = licencePlate.replace("-", "");
        licencePlate = licencePlate.replace("/", "");
        licencePlate = licencePlate.replace("*", "");
        licencePlate = licencePlate.replace(".", "");
        return licencePlate;
    }
}
