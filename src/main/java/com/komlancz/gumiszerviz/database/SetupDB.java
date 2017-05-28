package com.komlancz.gumiszerviz.database;

import com.komlancz.gumiszerviz.model.*;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.repository.CompanyRepository;
import com.komlancz.gumiszerviz.repository.PaidStateRepository;
import com.komlancz.gumiszerviz.repository.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class SetupDB {


    @Autowired
    CarInfoRepository carInfoRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    PaidStateRepository paidStateRepository;
    @Autowired
    StatesRepository statesRepository;

    private CarInfo carInfo;
    private Company company;
    private PaidState paidState;
    private States states;

    private SetupDB(CarInfoRepository carInfoRepository, CompanyRepository companyRepository, PaidStateRepository paidStateRepository, StatesRepository statesRepository){
        this.carInfoRepository = carInfoRepository;
    }

    @RequestMapping(value = "/update-table", method = RequestMethod.GET)
    public void findAllCarInfo(){
        String csvFile = "data-file.csv";
        File file = new File("/home/komlancz/Java/RaktarKezeles/src/main/resources/static/data-file.csv");
        String filePath = file.getAbsolutePath();
        String line = "";
        String cvsSplitBy = ",";

        States statesReady = new States();
        statesReady.setStateText(StatesEnum.READY.toString());
        States statesPassive = new States();
        statesPassive.setStateText(StatesEnum.PASSIVE.toString());
        States statesNotReady = new States();
        statesNotReady.setStateText(StatesEnum.NOT_READY.toString());

        PaidState paidStateTrue = new PaidState();
        paidStateTrue.setPaid("Fizetos");
        PaidState paidStateFalse = new PaidState();
        paidStateFalse.setPaid("Nem fizetos");

        paidStateRepository.save(Arrays.asList(paidStateFalse, paidStateTrue));
        statesRepository.save(Arrays.asList(statesNotReady, statesPassive,statesReady));

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                carInfo = new CarInfo();
                company = new Company();
                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                // set states info
                if ("i".equals(data[1])){
                    carInfo.setState(statesRepository.getOne(3));
                } else if ("p".equals(data[1])){
                    carInfo.setState(statesRepository.getOne(2));
                }
                carInfo.setState(statesRepository.getOne(1));

                // set company info
                company.setName(data[3]);
                company.setAddress(data[4]);
                company.setPhone(data[5]);
                companyRepository.save(company);

                // set paid state
                if ("0".equals(data[4])){
                    carInfo.setPaidStateId(paidStateRepository.findOne(1));
                }
                carInfo.setPaidStateId(paidStateRepository.findOne(2));
                // set carInfo
                carInfo.setLicencePlate(data[0]);
                carInfo.setWinterSize(data[7]);
                carInfo.setWinterBrand(data[8]);
                carInfo.setSummerSize(data[9]);
                carInfo.setSummerBrand(data[10]);
                carInfo.setComment(data[12]);
                carInfo.setPosition(data[2]);
                if ("x".equals(data[11])){
                    carInfo.setOnWheel(true);
                }else carInfo.setOnWheel(false);
                carInfo.setCompany(company);

                carInfoRepository.save(carInfo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
