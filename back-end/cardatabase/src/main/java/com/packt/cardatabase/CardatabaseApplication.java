package com.packt.cardatabase;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner { // 애플리케이션 시작 전 예제 데이터 준비용

    private static final Logger logger = LoggerFactory.getLogger(
            CardatabaseApplication.class
    );

    // 의존성 주입
    private final CarRepository repository;
    private final OwnerRepository orepository;

    public CardatabaseApplication(CarRepository repository, OwnerRepository orepository) {
        this.repository = repository;
        this.orepository = orepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner("John", "Johnson");
        Owner owner2 = new Owner("Mary", "Robinson");
        orepository.saveAll(Arrays.asList(owner1, owner2)); // DB에 저장

        repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2023, 59000, owner1));
        repository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000, owner2));
        repository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000, owner2));

        // 모든 자동차를 가져와 Console에 로깅
        for (Car car : repository.findAll()) {
            logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
        }
    }
}
