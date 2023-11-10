package com.example.numo.repositories.postgres;

import com.example.numo.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

}
