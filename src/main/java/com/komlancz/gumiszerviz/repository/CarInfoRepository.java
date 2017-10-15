package com.komlancz.gumiszerviz.repository;

import com.komlancz.gumiszerviz.model.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Integer> {
    public CarInfo getByLicencePlate(String licencePlate);


}
