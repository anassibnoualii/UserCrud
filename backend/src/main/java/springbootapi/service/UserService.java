package springbootapi.service;

import org.springframework.stereotype.Repository;

import springbootapi.dto.UserDTO;

@Repository
public interface UserService {
	UserDTO findUser(Long id);

}
