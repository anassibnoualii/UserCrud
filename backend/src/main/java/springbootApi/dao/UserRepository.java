package springbootApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootApi.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {


}
