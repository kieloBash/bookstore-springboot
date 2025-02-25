package com.store.bookstore.repository;

import com.store.bookstore.model.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<OurUsers, Integer> {
    Optional<OurUsers> findByEmail(String email);
}
