package com.komlancz.gumiszerviz.repository;

import com.komlancz.gumiszerviz.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin getByEmail(String email);
    Admin getByUsername(String username);
}
