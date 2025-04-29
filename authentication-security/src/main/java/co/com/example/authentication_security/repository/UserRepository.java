package co.com.example.authentication_security.repository;

import co.com.example.authentication_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

    Optional<User> findByUsername(String username);

}
