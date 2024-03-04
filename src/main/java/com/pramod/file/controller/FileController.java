package com.pramod.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pramod.file.entity.FileEntity;
import com.pramod.file.service.FileService;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {

	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@Value("${video.storage.path}")
    private String videoStoragePath;

    @GetMapping("/get")
    public ResponseEntity<Resource> getVideo(@RequestParam String fileName) {
        try {
            // Construct the file path
            String filePath = videoStoragePath + File.separator + fileName;
            System.out.println(filePath);
            // Check if the file exists
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Create a FileSystemResource to serve the file
            FileSystemResource resource = new FileSystemResource(filePath);

            // Return the file as a response
            return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("video/mp4")) // Adjust content type based on your file type
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@PostMapping("/upload/{userId}")
	public ResponseEntity<String> uploadFile(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
		try {
			System.out.println("entered into controller");
			String uploadFile = fileService.uploadFile(file,userId);
			return ResponseEntity.ok(uploadFile);
		} catch (Exception e) {
			System.out.println("entered into controller");
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error uploading file.");
		}
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		FileEntity fileData = fileService.downloadFile(fileName);
//      Files.readAllBytes(fileData.getFileData());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileData.getFileType()))
				.header("Content-Disposition", "attachment; filename=" + fileName)
				.body(new ByteArrayResource(fileData.getFileData()));
	}

	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> ViewFile(@PathVariable String fileName) {
		FileEntity fileData = fileService.downloadFile(fileName);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileData.getFileType()))
				.header("Content-Disposition", "inline; filename=" + fileName) // Change to inline
				.body(new ByteArrayResource(fileData.getFileData()));
	}
	@DeleteMapping("/delete")
    public String deleteFileByNameOrId(@RequestParam(required = false) Long id,
                                       @RequestParam(required = false) String fileName) {
        return fileService.deleteFileByNameOrId(id, fileName);
    }
	
	@GetMapping("/getAll")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }
	

}