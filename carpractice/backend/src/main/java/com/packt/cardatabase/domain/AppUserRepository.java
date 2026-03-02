package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false) // REST 리소스로 노출 되는 것을 방지
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    // Optional -> “값이 있을 수도 있고, 없을 수도 있다”는 것을 명확하게 표현하는 객체, 없을 경우 null 반환
    Optional<AppUser> findByUsername(String username);
}
