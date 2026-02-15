package com.packt.cardatabase;

import com.packt.cardatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CardatabaseApplicationTests {

    @Autowired
    private CarController controller;

    @Test
    @DisplayName("First example test case") // 테스트 케이스 이름 지정
    void contextLoads() {
        assertThat(controller).isNotNull(); // 컨트롤러 인스턴스가 null인지 확인
    }

}
