package com.komlancz.gumiszerviz.service.impl;

import com.komlancz.gumiszerviz.exception.DataBaseUpdateException;
import com.komlancz.gumiszerviz.model.*;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.repository.CompanyRepository;
import com.komlancz.gumiszerviz.repository.PaidStateRepository;
import com.komlancz.gumiszerviz.repository.StatesRepository;
import com.komlancz.gumiszerviz.service.DataBaseService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Iterator;

@Controller
public class DataBaseServiceImpl implements DataBaseService{

    @Autowired
    CarInfoRepository carInfoRepository;
    @Autowired
    CompanyRepository companyRepository;
//    @Autowired
    private PaidStateRepository paidStateRepository;
    @Autowired
    StatesRepository statesRepository;

    public static final Logger logger = LoggerFactory.getLogger(DataBaseServiceImpl.class);

    private final int LICENCE_PLATE = 0;
    private final int STATE = 1;
    private final int POSITION = 2;
    private final int NAME = 3;
    private final int PAID_STATE = 4;
    private final int ADDRESS = 5;
    private final int PHONE = 6;
    private final int WINTER_SIZE = 7;
    private final int WINTER_BRAND = 8;
    private final int SUMMER_SIZE = 9;
    private final int SUMMER_BRAND = 10;
    private final int IS_ON_WHEEL = 11;
    private final int COMMENT = 12;


    @Autowired
    public void setPaidStateRepository(PaidStateRepository paidStateRepository) {
        this.paidStateRepository = paidStateRepository;
    }

    private boolean fillInDataBaseTables(XSSFWorkbook workbook, int sheetNum) throws DataBaseUpdateException {

        CarInfo carInfo;
        Company company;

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

        logger.info("Update PAID STATE table in DB");
        paidStateRepository.save(Arrays.asList(paidStateFalse, paidStateTrue));
        logger.info("Update STATE table in DB");
        statesRepository.save(Arrays.asList(statesNotReady, statesPassive,statesReady));

        try {
            Sheet actualSheet = workbook.getSheetAt(sheetNum);
            Iterator<Row> rowIterator = actualSheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();

                carInfo = new CarInfo();
                company = new Company();

                // set states info
                if ("i".equals(row.getCell(STATE).toString())){
                    carInfo.setState(statesRepository.getOneByStateText("READY"));
                } else if ("p".equals(row.getCell(STATE).toString())){
                    carInfo.setState(statesRepository.getOneByStateText("PASSIVE"));
                } else carInfo.setState(statesRepository.getOneByStateText("NOT_READY"));


                // set company info
                logger.info("Update COMPANY table ind DB");
                company.setName(row.getCell(NAME).toString());
                company.setAddress(row.getCell(ADDRESS).toString());
                company.setPhone(row.getCell(PHONE).toString());
                System.out.println(company.getPhone());
                companyRepository.save(company);

                // set paid state
                if ("0".equals(row.getCell(PAID_STATE).toString())) carInfo.setPaidStateId(paidStateRepository.getOneByPaid("Nem fizetos"));
                else carInfo.setPaidStateId(paidStateRepository.getOneByPaid("Fizetos"));

                // set car infos
                logger.info("Update CAR INFO table in DB");
                String licencePlate = LicencePlateHandler.handleLicencePlate(row.getCell(LICENCE_PLATE).toString().toUpperCase());
                carInfo.setLicencePlate(licencePlate);
                carInfo.setWinterSize(row.getCell(WINTER_SIZE).toString());
                carInfo.setWinterBrand(row.getCell(WINTER_BRAND).toString());
                carInfo.setSummerSize(row.getCell(SUMMER_SIZE).toString());
                carInfo.setSummerBrand(row.getCell(SUMMER_BRAND).toString());
                carInfo.setComment(row.getCell(COMMENT).toString());
                carInfo.setPosition(row.getCell(POSITION).toString());
                // is on wheel
                if ("x".equals(row.getCell(IS_ON_WHEEL).toString().toLowerCase())){
                    carInfo.setOnWheel(true);
                }else carInfo.setOnWheel(false);
                carInfo.setCompany(company);

                carInfoRepository.save(carInfo);
            }
            logger.info("Car info table has been updated - SUCCESS");
            return true;
        }
        catch (Exception e){
            throw new DataBaseUpdateException("Can not update database", e);
        }
    }

    @Override
    public boolean updateDataBaseWithReleaseData() {
        XSSFWorkbook workbook = getReleaseDataFile();

        try {
            clearDataBase();
            return fillInDataBaseTables(workbook, 0);
        } catch (DataBaseUpdateException e) {
            logger.info(e.toString());
        }
        return false;
    }

    private XSSFWorkbook getReleaseDataFile(){
        return FileHandler.createWorkbookFromReleaseRawFile();
    }

    private void clearDataBase(){
        logger.info("Clear database tables");
        carInfoRepository.deleteAll();
        paidStateRepository.deleteAll();
        companyRepository.deleteAll();
        statesRepository.deleteAll();
        logger.info("Database tables are empty");
    }
}
