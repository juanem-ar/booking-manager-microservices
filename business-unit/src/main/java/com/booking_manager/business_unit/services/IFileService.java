package com.booking_manager.business_unit.services;

import com.booking_manager.business_unit.models.dtos.FileComplexResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    FileComplexResponse handleUploadFile(List<MultipartFile> fileList, Long id) throws Exception;
}
