package net.ghj.journalApp.controller;

import net.ghj.journalApp.entity.User;
import net.ghj.journalApp.service.Impl.UserDetailsServiceImpl;
import net.ghj.journalApp.service.UserService;
import net.ghj.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController // @RestController is a specialised version of @Component.
@RequestMapping("public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("The request was successful.");
    }

    @PostMapping("/user/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean status = userService.saveNewUser(user);
        if (status) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
    }

    @PostMapping("/user/sign-in")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String JWT = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(JWT, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while create authentication token ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}

//  Controller ---> Service ---> Repository
