package com.komlancz.gumiszerviz.controller;

import com.komlancz.gumiszerviz.model.CarInfo;
import com.komlancz.gumiszerviz.model.StatesEnum;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GettingInfoController {
    @Autowired
    CarInfoRepository carInfoRepository;

    @RequestMapping(value = "/carById/{carId}", method = RequestMethod.GET)
    public @ResponseBody
    CarInfo testing(@PathVariable("carId") String carId){
        int carIdNum = Integer.parseInt(carId);
        return carInfoRepository.getOne(carIdNum);
    }

    @RequestMapping(value = "/licencePlate/{licencePlate}", method = RequestMethod.GET)
    public @ResponseBody CarInfo getByLicencePlate(@PathVariable(name = "licencePlate") String licencePlate){
        return carInfoRepository.getByLicencePlate(licencePlate.toUpperCase());
    }

    @RequestMapping(value = "/all-clients", method = RequestMethod.GET)
    public @ResponseBody List<CarInfo> allClients(){
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
        if (!carInfoRepository.findAll().isEmpty() && carInfoRepository.findAll() != null){
            List<CarInfo> allClients = carInfoRepository.findAll();
            for (CarInfo client : allClients) {
                if (client.getState().getStateText().equals(currentState.toString())) readyClients.add(client);
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
}
