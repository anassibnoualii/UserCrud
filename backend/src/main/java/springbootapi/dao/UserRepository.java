package springbootapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootapi.entities.User;
public interface UserRepository extends JpaRepository<User,Long> {


}
