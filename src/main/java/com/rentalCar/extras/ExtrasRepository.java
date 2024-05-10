package com.rentalCar.extras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtrasRepository extends JpaRepository<Extras,Long> {


}
