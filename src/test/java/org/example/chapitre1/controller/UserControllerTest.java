package org.example.chapitre1.controller;

import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

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
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/users");
    }

    @Test
    void whenFindAll_thenRetrieveUsersFromDB() {
        ResponseEntity<UserDto[]> responseEntity = restTemplate.getForEntity(baseUrl, UserDto[].class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDto[] users = responseEntity.getBody();
        assert users != null;
        Assertions.assertTrue(users.length > 0);
    }

    @Test
    public void givenUserId_whenFindById_thenRetrieveUserFromDB() {
        Long userId = 1L;
        ResponseEntity<UserDto> response = restTemplate.getForEntity(baseUrl+"/{id}", UserDto.class, userId);
        assertAll("Grouped Assertions of UserController",
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> Assertions.assertNotNull(response.getBody()),
                () -> Assertions.assertEquals(userId, response.getBody().getId()));
    }

    @Test
    public void givenUserIdNotExist_whenFindById_thenThrowHttpClientErrorException() throws Exception {
        Long userId = 9L;
        assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(baseUrl+"/{id}", UserDto.class, userId));
    }

    @Test
    public void givenUserDto_whenSaveUser_thenSaveUser(){
        userRepository.deleteAll();
        UserDto userDto = new UserDto("firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        restTemplate.postForObject(baseUrl,userDto,UserDto.class);
        Assertions.assertEquals(userRepository.findAll().size(), 1);
    }
}