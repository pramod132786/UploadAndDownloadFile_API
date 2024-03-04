package com.pramod.file.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramod.file.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

	Optional<UserEntity> findByEmailAndPazz(String email, String pazz);
}
