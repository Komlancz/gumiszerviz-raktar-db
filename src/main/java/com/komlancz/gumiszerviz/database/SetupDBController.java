package com.komlancz.gumiszerviz.database;

import com.komlancz.gumiszerviz.service.DataBaseService;
import com.komlancz.gumiszerviz.service.impl.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@CrossOrigin
@RestController
public class SetupDBController {

    @Autowired
    DataBaseService dataBaseService;

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public @ResponseBody String refreshDataFile(MultipartHttpServletRequest request, HttpServletResponse response){
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = request.getFile(iterator.next());

        try {
                File file = FileHandler.convertToFile(multipartFile);
                if (FileHandler.moveFileToActual(file)){
                    return "SUCCESS";
                }
                else {
                    return "ERROR";
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @RequestMapping(value = "/use-release-data")
    public @ResponseBody String updateDBWithReleaseData(){
        FileHandler.updateDataFile();
            if (dataBaseService.updateDataBaseWithReleaseData()){
                return "Sikeressen firissitve";

            }
            return "Nem sikerult";
        }

//    @RequestMapping(value = "/update-table", method = RequestMethod.GET)
//    public boolean findAllCarInfo(){
//        File file = new File("src/main/resources/static/release-data/data.csv");
//        String filePath = file.getAbsolutePath();
//        String line = "";
//        String cvsSplitBy = ",";
//
//        States statesReady = new States();
//        statesReady.setStateText(StatesEnum.READY.toString());
//        States statesPassive = new States();
//        statesPassive.setStateText(StatesEnum.PASSIVE.toString());
//        States statesNotReady = new States();
//        statesNotReady.setStateText(StatesEnum.NOT_READY.toString());
//
//        PaidState paidStateTrue = new PaidState();
//        paidStateTrue.setPaid("Fizetos");
//        PaidState paidStateFalse = new PaidState();
//        paidStateFalse.setPaid("Nem fizetos");
//
//        paidStateRepository.save(Arrays.asList(paidStateFalse, paidStateTrue));
//        statesRepository.save(Arrays.asList(statesNotReady, statesPassive,statesReady));
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            int counter = 0;
//            while ((line = br.readLine()) != null) {
//                System.out.println("Row: " + counter);
//                carInfo = new CarInfo();
//                company = new Company();
//                // use comma as separator
//                String[] data = line.split(cvsSplitBy);
//
//                // set states info
//                if ("i".equals(data[1])){
//                    carInfo.setState(statesRepository.getOne(3));
//                } else if ("p".equals(data[1])){
//                    carInfo.setState(statesRepository.getOne(2));
//                } else carInfo.setState(statesRepository.getOne(1));
//
//                // set company info
//                company.setName(data[3]);
//                company.setAddress(data[5]);
//                company.setPhone(data[6]);
//                companyRepository.save(company);
//
//                // set paid state
//                if ("0".equals(data[4])) carInfo.setPaidStateId(paidStateRepository.findOne(1));
//                else carInfo.setPaidStateId(paidStateRepository.findOne(2));
//                // set carInfo
//                String plate = LicencePlateHandler.handleLicencePlate(data[0]).toUpperCase();
//                carInfo.setLicencePlate(plate);
//                carInfo.setWinterSize(data[7]);
//                carInfo.setWinterBrand(data[8]);
//                carInfo.setSummerSize(data[9]);
//                carInfo.setSummerBrand(data[10]);
//                carInfo.setComment(data[12]);
//                carInfo.setPosition(data[2]);
//                if ("x".equals(data[11].toLowerCase())){
//                    carInfo.setOnWheel(true);
//                }else carInfo.setOnWheel(false);
//                carInfo.setCompany(company);
//
//                carInfoRepository.save(carInfo);
//                counter++;
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
