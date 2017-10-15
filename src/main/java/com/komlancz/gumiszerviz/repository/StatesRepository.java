package com.komlancz.gumiszerviz.repository;

import com.komlancz.gumiszerviz.model.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<States, Integer> {
    States getOneByStateText(String stateText);
}
