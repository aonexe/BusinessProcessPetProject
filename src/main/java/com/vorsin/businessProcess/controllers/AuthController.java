//package com.vorsin.businessProcess.controllers;
//
//import com.vorsin.businessProcess.dto.AuthReqBody;
//import com.vorsin.businessProcess.dto.AuthResBody;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    // TODO Delete
//    @GetMapping("/login")
//    public String loginEndpoint() {
//        return "Login get request!";
//    }
//
//    @PostMapping("/login")
//    public AuthResBody authenticate(@RequestBody AuthReqBody authReqBody) {
//        System.out.println("Auth Details: " + authReqBody);
//
//        UsernamePasswordAuthenticationToken token = new
//                UsernamePasswordAuthenticationToken(
//                authReqBody.getUsername(),
//                authReqBody.getPassword());
//
//        System.out.println("\nAuthentication Token Before Authentication: " + token);
//
//        Authentication authResult = authenticationManager.authenticate(token);
//
//        System.out.println();
//        System.out.println("Authentication Token After Authentication: " + authResult);
//        System.out.println();
//
//        System.out.println("Authentication Token in Security Context: " + SecurityContextHolder.getContext().getAuthentication());
//
//        System.out.println();
//        if(authResult.isAuthenticated())
//            System.out.println("User is Authenticated");
//
//        return new AuthResBody(true);
//    }
//}
//
//
//
