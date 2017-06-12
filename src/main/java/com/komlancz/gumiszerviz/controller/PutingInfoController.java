package com.komlancz.gumiszerviz.controller;

import com.komlancz.gumiszerviz.model.CarInfo;
import com.komlancz.gumiszerviz.model.Company;
import com.komlancz.gumiszerviz.model.PaidState;
import com.komlancz.gumiszerviz.model.States;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.repository.CompanyRepository;
import com.komlancz.gumiszerviz.repository.PaidStateRepository;
import com.komlancz.gumiszerviz.repository.StatesRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutingInfoController {

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
            CarInfo newCar = saveDetails(data, null);
            carInfoRepository.save(newCar);
            retV = "SUCCES";
        }
        return retV;
    }

    @RequestMapping(value = "/update/{carId}", method = RequestMethod.POST)
    public String updateDetails(@PathVariable String carId, @RequestBody String body){
        String retv= "ERROR";
        int carIdNum = Integer.parseInt(carId);

        if (!carId.equals("") && !carId.isEmpty()){
            CarInfo car = carInfoRepository.getOne(carIdNum);
            if (car != null){
                carInfoRepository.save(saveDetails(body, car));
                carInfoRepository.delete(carIdNum);
                retv = "SUCCESS";
            }
        }
     return retv;
    }

    private CarInfo saveDetails(String data, CarInfo newCar){
        JSONObject newCarDetails = new JSONObject(data);

        Company company = companyRepository.getOneByName(newCarDetails.getString("ceg_nev"));
        if (company == null) {
            company = new Company();
            company.setName(newCarDetails.getString("ceg_nev"));
            companyRepository.save(company);
        }

        States state = statesRepository.getOneByStateText(newCarDetails.getString("state"));
        PaidState paidState = paidStateRepository.getOneByPaid(newCarDetails.getString("pay_status"));

        if (newCar != null) {
            newCar = new CarInfo();
        }
        newCar.setLicencePlate(newCarDetails.getString("licence_plate"));
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
