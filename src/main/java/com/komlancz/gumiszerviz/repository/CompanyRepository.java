package com.komlancz.gumiszerviz.repository;

import com.komlancz.gumiszerviz.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company getOneByName(String name);
    Company getOneByEmail(String email);
}
