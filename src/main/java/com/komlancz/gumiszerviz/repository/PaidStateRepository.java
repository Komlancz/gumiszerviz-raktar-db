package com.komlancz.gumiszerviz.repository;

import com.komlancz.gumiszerviz.model.PaidState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaidStateRepository extends JpaRepository<PaidState, Integer> {
    PaidState getOneByPaid(String paid);
}
