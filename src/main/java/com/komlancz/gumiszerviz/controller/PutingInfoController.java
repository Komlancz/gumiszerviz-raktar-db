package com.komlancz.gumiszerviz.controller;

import com.komlancz.gumiszerviz.model.*;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.repository.CompanyRepository;
import com.komlancz.gumiszerviz.repository.PaidStateRepository;
import com.komlancz.gumiszerviz.repository.StatesRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PutingInfoController {
    private static final Logger logger = LoggerFactory.getLogger(PutingInfoController.class);

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CarInfoRepository carInfoRepository;

    @Autowired
    PaidStateRepository paidStateRepository;

    @Autowired
    StatesRepository statesRepository;

    @RequestMapping(value = "/add-new-car", method = RequestMethod.POST)
    public String addNewCar(@RequestBody String data){

        String retV = "ERROR";

        if (!data.isEmpty() && !data.equals("")) {
            logger.info("Try to save new car: " + data);
            CarInfo newCar = saveDetails(data);
            carInfoRepository.save(newCar);
            retV = "SUCCES";
        }
        logger.info("Saved: " + data);
        return retV;
    }

    @RequestMapping(value = "/update/{carId}", method = RequestMethod.POST)
    public String updateDetails(@PathVariable String carId, @RequestBody String body){
        String retv= "ERROR";
        int carIdNum = Integer.parseInt(carId);

        if (!carId.equals("") && !carId.isEmpty()){
            try {
                CarInfo car = carInfoRepository.getOne(carIdNum);
                if (car != null){
                    carInfoRepository.save(saveDetails(body));
                    carInfoRepository.delete(carIdNum);
                    logger.info("Car info has been updated by ID: " + carIdNum);
                    retv = "SUCCESS";
                }
            }catch (EmptyResultDataAccessException exception){
                logger.warn("Can not find this ID in DB: " + carIdNum + " | " + exception);
            }
        }
     return retv;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteFromDbById(@RequestBody String carId){
        JSONObject body = new JSONObject(carId);
        Integer carIdNum = body.getInt("carId");
        logger.info("Try to delete wit id: " + carIdNum);
        String retV = "ERROR";
        if (carId != null && carIdNum > 0){
            try {
                CarInfo carInfo = carInfoRepository.getOne(carIdNum);
                carInfoRepository.delete(carIdNum);
                logger.info("Car info has been deleted by ID: " + carIdNum + " " + carInfo);
                retV = "SUCCESS";
            }
            catch (EmptyResultDataAccessException exception){
                logger.warn("Can not find this ID in DB: " + carIdNum + " | " + exception);
            }
        }
        return retV;
    }

    private CarInfo saveDetails(String data){
        JSONObject newCarDetails = new JSONObject(data);

        CarInfo newCar = new CarInfo();
        Company company = new Company();
        company.setName(newCarDetails.getString("ceg_nev"));
        company.setPhone(newCarDetails.getString("phone"));
        company.setAddress(newCarDetails.getString("address"));
        company.setEmail(newCarDetails.getString("email"));
        companyRepository.save(company);

        States state = statesRepository.getOneByStateText(newCarDetails.getString("state"));
        PaidState paidState = paidStateRepository.getOneByPaid(newCarDetails.getString("pay_status"));

        newCar.setLicencePlate(LicencePlateHandler.handleLicencePlate(newCarDetails.getString("licence_plate")).toUpperCase());
        newCar.setComment(newCarDetails.getString("comment"));
        newCar.setPosition(newCarDetails.getString("position"));
        newCar.setWinterBrand(newCarDetails.getString("winter_brand"));
        newCar.setWinterSize(newCarDetails.getString("winter_size"));
        newCar.setSummerBrand(newCarDetails.getString("summer_brand"));
        newCar.setSummerSize(newCarDetails.getString("summer_size"));
        newCar.setCompany(company);
        newCar.setState(state);
        newCar.setPaidStateId(paidState);
        return newCar;
    }
}
