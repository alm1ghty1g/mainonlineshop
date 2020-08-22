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
            System.out.println("inside login " + loginForm.toString());

            UsernamePasswordAuthenticationToken utoken = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());
            System.out.println("utoken " + utoken);
            Authentication authentication = authenticationManager.authenticate(utoken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("after setAuthentication");
            String jwt = jwtProvider.generate(authentication);//fix me
            System.out.println("jwt " + jwt);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userService.findOneByEmail(userDetails.getUsername());

            System.out.println("user " + user.toString());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getPassword(), user.getRole()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());

    }


    @GetMapping("/readUser/{email}")
    public ResponseEntity<UserDto> readUser(@PathVariable("email") String email) {

        return ResponseEntity.ok(userService.findByEmail(email));

    }


    @GetMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> getUserForUpdate(@PathVariable("id") int id) {


        return ResponseEntity.ok(userService.findById(id));

    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.updateUser(userDto));

    }


    @GetMapping("/deleteUser/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {

        UserDto userDto = userService.findById(id);

        userService.deleteUser(userDto);

        return ResponseEntity.ok().build();

    }

}
