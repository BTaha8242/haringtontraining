package org.example.chapitre1.controller;

import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.entity.Operation;
import org.example.chapitre1.entity.OperationTypeEnum;
import org.example.chapitre1.repository.OperationRepository;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class OperationControllerTest {

    @Autowired
    private OperationRepository operationRepository;

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
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/operations");
    }

    @Test
    void whenFindAll_thenRetrieveOperationsFromDB() {
        ResponseEntity<OperationDto[]> responseEntity = restTemplate.getForEntity(baseUrl, OperationDto[].class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OperationDto[] operationDtos = responseEntity.getBody();
        assert operationDtos != null;
        Assertions.assertTrue(operationDtos.length > 0);
    }

    @Test
    public void givenOperationDtosId_whenFindById_thenRetrieveOperationDtosFromDB() {
        Long accountId = 1L;
        ResponseEntity<AccountDto> response = restTemplate.getForEntity(baseUrl + "/{id}", AccountDto.class, accountId);
        assertAll("Grouped Assertions of UserController",
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> Assertions.assertNotNull(response.getBody()),
                () -> Assertions.assertEquals(accountId, response.getBody().getId()));
    }

    @Test
    public void givenOperationIdNotExist_whenFindById_thenThrowHttpClientErrorException() throws Exception {
        operationRepository.deleteById(1L);
        assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(baseUrl + "/{id}", Operation.class, 1));
    }

    @Test
    public void givenOperationDto_whenSaveOperation_thenSaveOperation() {
        operationRepository.deleteAll();
        OperationDto operationDto = new OperationDto(OperationTypeEnum.DEPOSIT,1L,500.4f);
        restTemplate.postForObject(baseUrl, operationDto, AccountDto.class);
        Assertions.assertEquals(operationRepository.findAll().size(), 1);
    }
}