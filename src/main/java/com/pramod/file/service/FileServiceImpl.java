package com.pramod.file.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pramod.file.entity.FileEntity;
import com.pramod.file.entity.UserEntity;
import com.pramod.file.exception.UserNotFoundException;
import com.pramod.file.repo.FileRepo;
import com.pramod.file.repo.UserRepo;

@Service
public class FileServiceImpl implements FileService {

	private final FileRepo repo;

	public FileServiceImpl(FileRepo repo) {
		this.repo = repo;
	}
	@Autowired
	private UserRepo userRepo;
	
	 @Value("${video.storage.path}")
	    private String videoStoragePath; // Path to store videos

	    @Override
	    public String uploadFile(MultipartFile file, Long userId) {
	        try {
	        	System.out.println("entered into service");
	            Optional<UserEntity> findById = userRepo.findById(userId);
	            if (findById.isPresent()) {
	                String fileName = file.getOriginalFilename();
	                System.out.println(fileName);
	                String fileExtension = FilenameUtils.getExtension(fileName);
	                System.out.println(fileExtension);
	                // Check if the file is a video
	                List<String> videoExtensions = Arrays.asList("mp4", "avi", "mov", "wmv");
	                if (videoExtensions.contains(fileExtension.toLowerCase())) {
	                    // Generate a unique filename for the video
	                	System.out.println("Entered");
//	                    String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
	                    File videoFile = new File(videoStoragePath + File.separator + fileName);
	                    file.transferTo(videoFile);
	                    FileEntity fileEntity= new FileEntity();
	                    fileEntity.setFileName(fileName);
	                    fileEntity.setFileType(fileExtension);
	                    fileEntity.setUserId(userId);
	                    repo.save(fileEntity);
	                    
	                    // Return the file path or any identifier you prefer to save in the database
	                    return "Video uploaded successfully. File path: " + videoFile.getAbsolutePath();
	                } else {
	                    // For other file types, save to the database as before
	                    FileEntity fileEntity = FileEntity.builder()
	                            .fileName(fileName)
	                            .fileType(file.getContentType())
	                            .fileData(file.getBytes())
	                            .build();
	                    fileEntity.setUserId(userId);
	                    repo.save(fileEntity);
	                    return "File uploaded successfully. File ID: " + fileEntity.getId();
	                }
	            }
	            throw new UserNotFoundException("User not found with user Id " + userId);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error uploading file.";
	        }
	    }


//	@Override
//	public String uploadFile(MultipartFile file,Long userId ) {
//		try {
//			Optional<UserEntity> findById = userRepo.findById(userId);
//			if(findById.isPresent()) {
//				FileEntity fileEntity = FileEntity.builder().fileName(file.getOriginalFilename())
//						.fileType(file.getContentType()).fileData(file.getBytes()).build();
//				System.out.println("File Data Length: " + fileEntity.getFileData().length);
//				fileEntity.setUserId(userId);
//				
//				repo.save(fileEntity);
//
//				return "File uploaded successfully. File ID: " + fileEntity.getId();
//			}
//			throw new UserNotFoundException("User not found with user Id "+userId);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Error uploading file.";
//		}
//	}

	@Override
	public FileEntity downloadFile(String fileName) {
		Optional<FileEntity> fileEntityOptional = repo.findByFileName(fileName);

		if (fileEntityOptional.isPresent()) {

			FileEntity fileEntity = fileEntityOptional.get();
			return fileEntity;
		} else {
			throw new RuntimeException("File not found");
		}
	}

	@Override
	public String deleteFileByNameOrId(Long id, String fileName) {
		if (id != null) {
			// Delete file by ID
			repo.deleteById(id);
			return "File deleted by ID: " + id;
		} else if (fileName != null && !fileName.isEmpty()) {
			// Delete file by name
			Optional<FileEntity> fileEntityOptional = repo.findByFileName(fileName);
			if (fileEntityOptional.isPresent()) {
				FileEntity fileEntity = fileEntityOptional.get();
				repo.delete(fileEntity);
				return "File deleted by name: " + fileName;
			} else {
				return "File not found with name: " + fileName;
			}
		} else {
			return "Invalid parameters provided.";
		}

	}
	
	 @Override
	    public List<FileEntity> getAllFiles() {
	        List<FileEntity> files = repo.findAll();
	       
			return files;
	    }
}
