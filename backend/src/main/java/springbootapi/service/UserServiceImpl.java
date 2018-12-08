package springbootapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootapi.dao.UserRepository;
import springbootapi.dto.UserDTO;
import springbootapi.entities.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO findUser(Long id) {
		User user = new User();
		return user.fromUserToUserDto(userRepository.findById(id).orElse(null));
		
		
	}

}
