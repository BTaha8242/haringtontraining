package org.example.chapitre1.controller;

import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    private static RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/accounts");
    }

    @Test
    void whenFindAll_thenRetrieveAccountsFromDB() {
        ResponseEntity<AccountDto[]> responseEntity = restTemplate.getForEntity(baseUrl, AccountDto[].class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AccountDto[] accountDtos = responseEntity.getBody();
        assert accountDtos != null;
        Assertions.assertTrue(accountDtos.length > 0 );
    }

    @Test
    @Sql({"/jdbc/FILL_USER_TABLE.sql", "/jdbc/FILL_ACCOUNT_TABLE.sql"})
    public void givenAccountId_whenFindById_thenRetrieveAccountFromDB() {
        Long accountId = 100L;
        ResponseEntity<AccountDto> response = restTemplate.getForEntity(baseUrl + "/{id}", AccountDto.class, accountId);
        assertAll("Grouped Assertions of AccountController",
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> Assertions.assertNotNull(response.getBody()),
                () -> Assertions.assertEquals(accountId, Objects.requireNonNull(response.getBody()).getId()));
    }

    @Test
    public void givenIdAccountNotExist_whenFindById_thenThrowHttpClientErrorException() throws Exception {
        accountRepository.deleteById(1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(baseUrl + "/{id}", AccountDto.class, 1));
    }

    @Test
    public void givenAccountDto_whenSaveAccount_thenSavedAccount() {
        accountRepository.deleteAll();
        AccountDto accountDto = new AccountDto(1L, 500.4f);
        restTemplate.postForObject(baseUrl, accountDto, AccountDto.class);
        assertAll("Grouped Assertions of AccountController",
                () -> Assertions.assertEquals(accountRepository.findAll().size(), 1),
                () -> assertEquals(accountRepository.findAll().stream().findFirst().get().getBalance(), 500.4f));
    }
}