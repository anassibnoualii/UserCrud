package springbootapi.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootapi.dao.UserRepository;
import springbootapi.dto.UserDTO;
import springbootapi.entities.User;
import springbootapi.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Api(value = "users", tags = "manage users")

public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

    @ApiOperation(value = "retrieve all users from data base")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/users")
	public List<User> findAll() {
		return userRepository.findAll();
	}

    @ApiOperation(value = "retrieve user by id  from data base")

    @GetMapping(path = { "/users/{id}" })
	public UserDTO findOne(@PathVariable("id") Long id) {
		return userService.findUser(id);
	}

    @ApiOperation(value = "add user user to data base")
    @PostMapping("/users")
	public User create(@RequestBody User user) {
		return userRepository.save(user);

	}

    @ApiOperation(value = "updating a user userin a data base")
	@PutMapping("/users")
	public User update(@RequestBody User user) {
		return userRepository.saveAndFlush(user);
	}

    @ApiOperation(value = "delete a user from data base")
    @DeleteMapping(path = { "/users/{id}" })
	public void delete(@PathVariable("id") Long id) {
		
		userRepository.deleteById(id);
		
	}
}
