package com.example.userservice.repo;

import com.example.userservice.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<userEntity,Long> {
    
}
