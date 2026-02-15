package com.packt.cardatabase;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 테스트가 JPA 컴포넌트에 초점을 맞추는 경우 해당 어노테이션 사용
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository repository;

    @Test
    void saveOwner(){ // CREATE
        repository.save(new Owner("Lucy", "Smith"));
        assertThat(repository.findByFirstName("Lucy").isPresent()).isTrue();
    }

    @Test
    void deleteOwner(){ // DELETE
        repository.save(new Owner("Lisa", "Morrison"));
        repository.deleteAll();
        assertThat(repository.count()).isEqualTo(0);
    }
}
