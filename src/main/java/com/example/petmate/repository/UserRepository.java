package com.example.petmate.repository;

import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	User findByEmail(String email);

	User findById(String userId);

	@Query(nativeQuery = true, value = """
            SELECT * FROM users WHERE user_role = 'EMPLOYEE'
            """)
	List<User> getAllEmployee();
}
