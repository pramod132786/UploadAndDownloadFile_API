package com.pramod.file.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    
    private String fileName;
    private String fileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;
    
    private Long userId;

    public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public FileEntity() {
    }


    public static FileEntityBuilder builder() {
        return new FileEntityBuilder();
    }

    public static class FileEntityBuilder {
        private Long id;
        private String fileName;
        private String fileType;
        private byte[] fileData;

        public FileEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FileEntityBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileEntityBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public FileEntityBuilder fileData(byte[] fileData) {
            this.fileData = fileData;
            return this;
        }

        public FileEntity build() {
            FileEntity fileEntity = new FileEntity();
            fileEntity.id = this.id;
            fileEntity.fileName = this.fileName;
            fileEntity.fileType = this.fileType;
            fileEntity.fileData = this.fileData;
            return fileEntity;
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public String getBase64Data() {
		return base64Data;
	}


	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}


	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	 @Transient
	    private String base64Data;

    
}
