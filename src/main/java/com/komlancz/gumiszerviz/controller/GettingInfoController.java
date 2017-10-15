package com.komlancz.gumiszerviz.controller;

import com.komlancz.gumiszerviz.model.CarInfo;
import com.komlancz.gumiszerviz.model.StatesEnum;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.service.PrinterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class GettingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(GettingInfoController.class);
    @Autowired
    CarInfoRepository carInfoRepository;

    @Autowired
    PrinterService printerService;

    @RequestMapping(value = "/carById/{carId}", method = RequestMethod.GET)
    public @ResponseBody
    CarInfo testing(@PathVariable("carId") String carId){
        int carIdNum = Integer.parseInt(carId);
        return carInfoRepository.getOne(carIdNum);
    }

    @RequestMapping(value = "/create-excel", method = RequestMethod.GET)
    public @ResponseBody
    String createExcel(){
        try {
            logger.info("Try to create Excel file");
            printerService.getPrintableFile();
            logger.info("Excel file has been created in 'ready-for-print' folder. File name: now day date");
            return "SUCCESS";
        }
        catch (Exception e){
            logger.warn("Can not create excel", e);
        }
        return "ERROR";
    }

    @RequestMapping(value = "/licencePlate/{licencePlate}", method = RequestMethod.GET)
    public @ResponseBody CarInfo getByLicencePlate(@PathVariable(name = "licencePlate") String licencePlate){
        logger.info("Search by licence plate: " + licencePlate);
        licencePlate = handleGlyph(licencePlate);
        logger.info("Result: " + carInfoRepository.getByLicencePlate(licencePlate.toUpperCase()));
        return carInfoRepository.getByLicencePlate(licencePlate.toUpperCase());
    }

    @RequestMapping(value = "/all-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> allClients(){
        logger.info("Get all clients");
        return carInfoRepository.findAll();
    }

    @RequestMapping(value = "/paid-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> paidClients(){
        return getPaidClients(true);
    }

    @RequestMapping(value = "/not-paid-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> notPaidClients(){
        return getPaidClients(false);
    }

    @RequestMapping(value = "/ready-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> readyClients(){
        return getReadyClients(StatesEnum.READY);
    }

    @RequestMapping(value = "/passive-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> passiveClients(){
        return getReadyClients(StatesEnum.PASSIVE);
    }

    @RequestMapping(value = "/not-ready-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> notReadyClients(){
        return getReadyClients(StatesEnum.NOT_READY);
    }

    private List<CarInfo> getReadyClients(StatesEnum currentState){
        List<CarInfo> readyClients = new ArrayList<>();
        if (carInfoRepository.findAll() != null && !carInfoRepository.findAll().isEmpty()){
            List<CarInfo> allClients = carInfoRepository.findAll();
            for (CarInfo client : allClients) {
                if (client.getState() != null && client.getState().getStateText().equals(currentState.toString())){
                    readyClients.add(client);
                }
            }
        }
        return readyClients;
    }
    private List<CarInfo> getPaidClients(boolean isPaid){
        List<CarInfo> paidClients = new ArrayList<>();
        if (!carInfoRepository.findAll().isEmpty() && carInfoRepository.findAll() != null){
            List<CarInfo> allClients = carInfoRepository.findAll();
            for (CarInfo client : allClients) {
                if (isPaid && client.getPaidStateId().getPaid().equals("Fizetos")) paidClients.add(client);
                if (!isPaid && client.getPaidStateId().getPaid().equals("Nem fizetos")) paidClients.add(client);
            }
        }
        return paidClients;
    }

    private String handleGlyph(String value){
        value = value.replace("/", "");
        value = value.replace("-", "");
        value = value.replace("+", "");
        value = value.replace("?", "");
        value = value.replace("*", "");
        value = value.replace("(", "");
        value = value.replace(")", "");
        value = value.replace(".", "");
        return value;
    }

}
