package com.example.usersapi.controller;

import com.example.usersapi.exception.LoginException;
import com.example.usersapi.exception.SignUpException;
import com.example.usersapi.model.*;
import com.example.usersapi.model.wrapper.CommentWithDetails;
import com.example.usersapi.model.wrapper.PostWithUser;
import com.example.usersapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.ApiParam;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  @ApiOperation(value = "Signup a new user", notes = "provide username, email and password", response = JwtResponse.class)
  public ResponseEntity<?> signup(
      @ApiParam(value = "user body: username, email and password", required = true) @Valid @RequestBody User user)
      throws SignUpException {
    String token = userService.signup(user);
    User savedUser = userService.findByEmail(user.getEmail());
    return ResponseEntity.ok(new JwtResponse(token, savedUser.getUsername()));
  }

  @PostMapping("/login")
  @ApiOperation(value = "Login an user", notes = "provide email and password", response = JwtResponse.class)
  public ResponseEntity<?> login(
      @ApiParam(value = "user body: email and password", required = true) @RequestBody User user)
      throws LoginException {
    String token = userService.login(user);
    User foundUser = userService.findByEmail(user.getEmail());
    return ResponseEntity.ok(new JwtResponse(token, foundUser.getUsername()));
  }

  @GetMapping("/list")
  @ApiOperation(value = "List all the users", notes = "get all the users", response = User.class, responseContainer = "List")
  public List<User> listUsers() {
    return userService.listUsers();
  }

  @GetMapping("/post")
  @ApiOperation(value = "Get all posts of a user", notes = "get posts by username", response = PostWithUser.class, responseContainer = "List")
  public List<PostWithUser> getPostsByUser(@RequestHeader String username) {
    return userService.getPostsByUser(username);
  }

  @GetMapping("/comment")
  @ApiOperation(value = "Get all comments of a user", notes = "get comments by username", response = CommentWithDetails.class, responseContainer = "List")
  public List<CommentWithDetails> getCommentsByUser(@RequestHeader String username)
      throws JsonProcessingException {
    return userService.getCommentsByUser(username);
  }
}
