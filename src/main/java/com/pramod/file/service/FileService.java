package com.pramod.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pramod.file.entity.FileEntity;

public interface FileService {
	
	public String uploadFile(MultipartFile file, Long userId);
	
	public FileEntity downloadFile(String fileName);
	
	public String deleteFileByNameOrId(Long id,String fileName);

	 public List<FileEntity> getAllFiles();
}
