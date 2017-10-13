package com.komlancz.gumiszerviz.service.impl;

import com.komlancz.gumiszerviz.model.CarInfo;
import com.komlancz.gumiszerviz.model.LicencePlateHandler;
import com.komlancz.gumiszerviz.repository.CarInfoRepository;
import com.komlancz.gumiszerviz.service.PrinterService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class PrinterServiceImpl implements PrinterService {

    @Autowired
    CarInfoRepository carInfoRepository;

    @Override
    public void getPrintableFile() {

        List<CarInfo> cars = getAllClients();

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        Row row;
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Courier New");
        font.setItalic(true);
        font.setBold(true);

        headerStyle.setFont(font);
//        font.setStrikeout(true);
        // Create a row and put some cells in it. Rows are 0 based.

        row = sheet.createRow(0);
        setCurrentCell(row, sheet, 0, "Rendszám", headerStyle);
        setCurrentCell(row, sheet, 1, "Kész", headerStyle);
        setCurrentCell(row, sheet, 2, "Pozíció", headerStyle);
        setCurrentCell(row, sheet, 3, "Név", headerStyle);
        setCurrentCell(row, sheet, 4, "Cím", headerStyle);
        setCurrentCell(row, sheet, 5, "Telefon", headerStyle);
        setCurrentCell(row, sheet, 6, "Téli Gumi", headerStyle);
        setCurrentCell(row, sheet, 8, "Nyári Gumi", headerStyle);
        setCurrentCell(row, sheet, 10, "Komment", headerStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,6,7));
        sheet.addMergedRegion(new CellRangeAddress(0,0,8,9));

        for (int i = 1; i < cars.size(); i++){
            CarInfo currentCar = cars.get(i);
            System.out.println("Car id: " + currentCar.getCarInfoId());

            row = sheet.createRow(i);

            // Fonts are set into a style so create a new one to use.
            CellStyle style = wb.createCellStyle();
            style.setFont(font);

            // Licence plate
            setCurrentCell(row, sheet, 0, LicencePlateHandler.handleLicencePlate(currentCar.getLicencePlate()));

            // Ready stat
            setCurrentCell(row, sheet,1, currentCar.getState().getStateText());

            // Position
            setCurrentCell(row, sheet,2, currentCar.getPosition());

            // Company Name
            setCurrentCell(row, sheet,3, currentCar.getCompany().getName());

//            // Paid State
//            setCurrentCell(row, sheet,4, currentCar.getPaidStateId().toString());

            // Address
            setCurrentCell(row, sheet,4, currentCar.getCompany().getAddress());

            // Phone
            setCurrentCell(row, sheet,5, currentCar.getCompany().getPhone());

            // Winter size
            setCurrentCell(row, sheet,6, currentCar.getWinterSize());

            // Winter brand
            setCurrentCell(row, sheet,7, currentCar.getWinterBrand());

            // Summer size
            setCurrentCell(row, sheet,8, currentCar.getSummerSize());

            // Summer brand
            setCurrentCell(row, sheet,9, currentCar.getSummerBrand());

//            // Is on Wheel
//            setCurrentCell(row, sheet,cellNum, currentCar.isOnWheel());

            // Comment
            setCurrentCell(row, sheet,10, currentCar.getComment());

        }

        // Write the output to a file
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("src/main/resources/static/ready-for-print/test.xlsx");
            wb.write(fileOut);
            fileOut.close();
            System.out.println("sikerült menteni");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<CarInfo> getAllClients(){
        return carInfoRepository.findAll();
    }

    private void setCurrentCell(Row row, Sheet sheet, int cellNum, String cellValue){
        setCurrentCell(row, sheet, cellNum, cellValue, null);
    }

    private void setCurrentCell(Row row, Sheet sheet, int cellNum, String cellValue, CellStyle cellStyle){
        Cell cell = row.createCell(cellNum);
        cell.setCellValue(cellValue);
        System.out.println(cellNum);
        sheet.autoSizeColumn(cellNum);

        if (cellStyle != null){
            cell.setCellStyle(cellStyle);
        }
    }
}
