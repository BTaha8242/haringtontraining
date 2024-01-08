package org.example.chapitre1.controller;


import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {



    private final UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> usersDto = userService.findAll();
        HttpStatus status = (usersDto != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(usersDto, status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> findById(@PathVariable Long id) throws UserNotFoundException {
        UserDto userDto = userService.findById(id);
        HttpStatus status = (userDto != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(userDto, status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto userSaved = userService.save(userDto);
        HttpStatus status = (userSaved != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(userSaved, status);
    }

}
