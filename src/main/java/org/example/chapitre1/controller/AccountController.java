package org.example.chapitre1.controller;


import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) throws UserNotFoundException {
        AccountDto accountDtoSaved = accountService.save(accountDto);
        HttpStatus status = (accountDtoSaved != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(accountDtoSaved, status);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> findAll() {
        return accountService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountDto findById(@PathVariable Long id) throws AccountNotFoundException {
        return accountService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws AccountNotFoundException {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
