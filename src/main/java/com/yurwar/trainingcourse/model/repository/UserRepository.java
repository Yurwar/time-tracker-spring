package com.yurwar.trainingcourse.model.repository;

import com.yurwar.trainingcourse.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository that provides access to user entity mapping in database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
