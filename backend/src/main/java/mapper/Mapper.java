package mapper;

import dto.UserDTO;
import springbootapi.entities.User;

public class Mapper {
	public UserDTO userDto(User user) {
		UserDTO userDto= new UserDTO();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		
		return userDto;
	}
	public User user(UserDTO userDto) {
		User user= new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		
		return user;
	}
	

}
