package com.cl.registercl.repositories;

import com.cl.registercl.entities.Phone;
import com.cl.registercl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}