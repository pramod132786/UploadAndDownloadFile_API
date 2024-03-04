package com.pramod.file.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramod.file.entity.FileEntity;

@Repository
public interface FileRepo extends JpaRepository<FileEntity, Long>{

	public Optional<FileEntity> findByFileName(String fileName);
	
}
