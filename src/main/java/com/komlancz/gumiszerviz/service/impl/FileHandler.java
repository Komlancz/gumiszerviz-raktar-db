package com.komlancz.gumiszerviz.service.impl;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileHandler {

    private static final String ACTUAL_PATH = "src/main/resources/static/actual-data/data.xlsx";
    private static final String RELEASE_PATH = "src/main/resources/static/release-data/data.xlsx";
    private static final String OUT_OF_DATE_PATH = "src/main/resources/static/out-of-date-data/data.xlsx";

    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    public static File convertToFile(MultipartFile file) throws IOException {

        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean moveFileToActual(File file){

        if(file.renameTo(new File(ACTUAL_PATH))){
            System.out.println("File is moved successful!");
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean moveFileToOutOfDate(File file){

        if(file.renameTo(new File(OUT_OF_DATE_PATH))){
            System.out.println("File is moved successful!");
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean moveFileToRelease(File file){

        if(file.renameTo(new File(RELEASE_PATH))){
            System.out.println("File is moved successful!");
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateDataFile(){
        File releaseFile = new File(RELEASE_PATH);
        File actualFile = new File(ACTUAL_PATH);
        File outOfDateFile = new File(OUT_OF_DATE_PATH);

        if (outOfDateFile.getTotalSpace() > 0 && outOfDateFile.delete()){
            System.out.println("Remove from out of date");
            if (moveFileToOutOfDate(releaseFile)){
                System.out.println("Remove from release");
                if (moveFileToRelease(actualFile)){
                    System.out.println("Add to release");
                    return true;
                }
            }
        }
        return false;
    }

    public static XSSFWorkbook createWorkbookFromReleaseRawFile(){
        try {
            InputStream rawFile = new FileInputStream(RELEASE_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(rawFile);
            return workbook;
        } catch (FileNotFoundException e) {
            logger.warn("File not found", e);
        } catch (IOException e) {
            logger.warn("XSSF Workbook exception ", e);
        }
        return null;
    }

    public static boolean isXLSXFile(MultipartFile multipartFile){
        return multipartFile.getContentType().contains("xlsx");
    }

}
