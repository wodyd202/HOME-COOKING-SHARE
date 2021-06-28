package com.homecookingshare.common.fileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService implements FileUploader {

	@Value("${spring.fileUpload.url}")
	private String fileRoot;

	@Override
	public String uploadFile(MultipartFile file, String fileName) {
		String originalFileName = file.getOriginalFilename(); // 오리지날 파일명
		if (originalFileName.equals("")) {
			throw new IllegalArgumentException("파일명을 입력해주세요.");
		}
		File targetFile = new File(fileRoot + fileName);
		try {
			InputStream fileStream = file.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
			e.printStackTrace();
		}
		return fileName;
	}
}
