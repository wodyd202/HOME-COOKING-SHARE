package com.homecookingshare.common.fileUpload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
	String uploadFile(MultipartFile file, String fileName);
}
