package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car,Long> { // 엔티티 클래스에 CRUD 기능 제공
    // 수정
}
