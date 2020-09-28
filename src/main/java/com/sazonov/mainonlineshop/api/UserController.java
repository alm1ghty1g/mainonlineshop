package com.sazonov.mainonlineshop.api;


import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formDto.LoginFormDto;
import com.sazonov.mainonlineshop.dto.formDto.UserSingUpDtoRequest;
import com.sazonov.mainonlineshop.mapper.UserMapper;
import com.sazonov.mainonlineshop.security.JWT.JwtProvider;
import com.sazonov.mainonlineshop.security.JWT.JwtResponse;
import com.sazonov.mainonlineshop.serviceinterface.UserService;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/register")
    public ResponseEntity<UserDto> save(@RequestBody UserSingUpDtoRequest userSingUpDto) {

        UserDto userDto = userMapper.getUserDtoForSignUp(userSingUpDto);

        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginFormDto loginForm) {
        // throws Exception if authentication failed

        try {

            UsernamePasswordAuthenticationToken utoken = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());

            Authentication authentication = authenticationManager.authenticate(utoken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generate(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            UserEntity user = userService.findOneByEmail(userDetails.getUsername());

            System.out.println("privet user " + user.toString());

            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getPassword(), user.getRole()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());

    }


    @GetMapping("/find-by/{email}")
    public ResponseEntity<List<UserDto>> findByEmail(@PathVariable("email") String email) {

        return ResponseEntity.ok(userService.findByEmail(email));

    }


    @GetMapping("/update/{id}")
    public ResponseEntity<UserDto> getUserForUpdate(@PathVariable("id") int id) {


        return ResponseEntity.ok(userService.findById(id));

    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {

        System.out.println("______> " + userDto.getPassword());

        return ResponseEntity.ok(userService.updateUser(userDto));

    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id") int id) {

        UserDto userDto = userService.findById(id);

        userService.deleteUser(userDto);

        return ResponseEntity.ok().build();

    }

}
