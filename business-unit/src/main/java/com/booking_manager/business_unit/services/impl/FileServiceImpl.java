package com.booking_manager.business_unit.services.impl;

import com.booking_manager.business_unit.models.dtos.BaseResponse;
import com.booking_manager.business_unit.models.dtos.FileComplexResponse;
import com.booking_manager.business_unit.services.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements IFileService {
    @Override
    public FileComplexResponse handleUploadFile(List<MultipartFile> fileList, Long id) throws Exception {
        List<String> errorList = new ArrayList<>();
        List<String> fileSavedList = new ArrayList<>();
        var fileSavedCount = 0;
        for(MultipartFile file: fileList){

            byte[] bytes = file.getBytes();
            FileComplexResponse newFileName = getString(file);

            if (newFileName.getBaseResponse() != null && !newFileName.getBaseResponse().hastErrors()) {

                File folder = new File("business-unit/src/main/resources/assets/rental-units-img/" + id + "/");
                if (!folder.exists()){
                    folder.mkdirs();
                }

                Path path = Paths.get("business-unit/src/main/resources/assets/rental-units-img/"+ id + "/" + newFileName.getResponse());

                Files.write(path, bytes);
                fileSavedList.add(file.getOriginalFilename());
                fileSavedCount ++;
            }else{
                errorList.add(Arrays.asList(newFileName.getBaseResponse().errorMessage()).toString());
                log.info("Error list: {}",errorList);
            }
        }
        var resultWithErrors = fileSavedCount > 0 ? FileComplexResponse.builder().response("Files upload successful: " + fileSavedList.toString()).baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build() : FileComplexResponse.builder().response("").baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = FileComplexResponse.builder().response("File upload successfully").baseResponse(new BaseResponse(null)).build();
        return errorList.isEmpty() ? resultWithoutErrors : resultWithErrors;
    }

    private FileComplexResponse getString(MultipartFile file) throws Exception {

        String fileNameExtension = getFileNameExtension();
        List<String> errorList = new ArrayList<>();
        String fileFullName = null;

        FileComplexResponse fileOriginalNameResponse = getFileOriginalName(file);

        if (fileOriginalNameResponse.getBaseResponse() != null && !fileOriginalNameResponse.getBaseResponse().hastErrors()) {
            String fileOriginalName = fileOriginalNameResponse.getResponse();

            String fileFormat = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String fileName = fileOriginalName.substring(0, fileOriginalName.lastIndexOf("."));
            fileFullName = fileName + "_" + fileNameExtension + fileFormat;
        }else{
            log.info("Error when trying to save the file: {}", (Object[]) fileOriginalNameResponse.getBaseResponse().errorMessage());
            errorList = Arrays.asList(fileOriginalNameResponse.getBaseResponse().errorMessage());
        }
        var resultWithErrors = FileComplexResponse.builder().baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = FileComplexResponse.builder().response(fileFullName).baseResponse(new BaseResponse(null)).build();
        return !errorList.isEmpty() ?  resultWithErrors : resultWithoutErrors;
    }

    private String getFileNameExtension() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        return now.format(formatter);
    }

    private FileComplexResponse getFileOriginalName(MultipartFile file) throws IOException {
        List<String> errorList = new ArrayList<>();
        String fileOriginalName = file.getOriginalFilename();

        long fileSize = file.getSize();
        long maxFileSize = 5 * 1024 * 1024;

        if (fileSize > maxFileSize){
            errorList.add(fileOriginalName + ": File size must be less or equal 5 mb");
        }

        if (
                !fileOriginalName.endsWith(".jpeg") &&
                !fileOriginalName.endsWith(".jpg") &&
                !fileOriginalName.endsWith(".png")
        ){
            errorList.add("Error in \"" + fileOriginalName + "\". Because only JPEG, PNG files are allowed");
        }
        var resultWithErrors = FileComplexResponse.builder().baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = FileComplexResponse.builder().response(fileOriginalName).baseResponse(new BaseResponse(null)).build();
        return !errorList.isEmpty() ?  resultWithErrors : resultWithoutErrors;
    }
}
