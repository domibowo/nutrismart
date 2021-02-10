package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Account;
import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.repository.AccountRepository;
import com.enigma.nutrismartbe.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AccountController controller;

    @Autowired
    AccountService service;

    @Autowired
    AccountRepository repository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    void getAccount_shouldReturnStatusOK_whenSuccess() throws Exception {
        Account account = new Account();
        account.setUserName("c");
        account.setEmail("d");
        account.setPassword("e");
        service.saveAccount(account);

        controller.getAccount(account.getId());
        RequestBuilder builder = MockMvcRequestBuilders
                .get("/account/profile/"+account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveProfileAccount_shouldReturnStatusOK_whenAbleToUpdateProfileAccount() throws Exception {

        Account account = new Account();
        account.setUserName("a");
        account.setEmail("b");
        account.setPassword("c");
        service.saveAccount(account);

        Profile profile = account.getProfile();
        profile.setFirstName("An");
        profile.setLastName("An");

        controller.saveProfileAccount(account);
        RequestBuilder builder = MockMvcRequestBuilders.put("/account/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void signIn_shouldReturnStatusOK_whenAbleToSignIn() throws Exception {
        Account account = new Account();
        account.setUserName("d");
        account.setEmail("e");
        account.setPassword("f");
        service.saveAccount(account);

        Account login = new Account();
        login.setUserName(account.getUserName());
        login.setPassword(account.getPassword());
        controller.signIn(login);
        RequestBuilder builder = MockMvcRequestBuilders.post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(login));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveAccount_shouldReturnStatusOK_whenAbleToSignUp() throws Exception {
        Account account = new Account();
        account.setUserName("b");
        account.setEmail("c");
        account.setPassword("d");
        controller.saveAccount(account);

        RequestBuilder builder = MockMvcRequestBuilders.post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andReturn().getResponse().getContentAsString();
    }

    @Test
    void signInAdmin_shouldReturnStatusOK_whenAbleToSignInAsAdmin() throws Exception {
        Account account = new Account();
        account.setUserName("e");
        account.setEmail("f");
        account.setPassword("g");
        service.saveAdminAccount(account);

        Account login = new Account();
        login.setUserName(account.getUserName());
        login.setPassword(account.getPassword());
        controller.signInAdmin(login);

        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/sign-in")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(login));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveAdminAccount_shouldReturnStatusOK_whenAbleToCreateAdminAccount() throws Exception {
        Account account = new Account();
        account.setUserName("f");
        account.setEmail("g");
        account.setPassword("h");
        controller.saveAdminAccount(account);

        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andReturn().getResponse().getContentAsString();
    }

    @Test
    void deleteAccount_shouldReturnStatusOK_whenSuccessfullyInactivateAnAccount() throws Exception {
        Account account = new Account();
        account.setUserName("g");
        account.setEmail("h");
        account.setPassword("i");
        service.saveAccount(account);

        controller.deleteAccount(account.getId());

        RequestBuilder builder = MockMvcRequestBuilders.put("/admin/account/"+account.getId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllAccount_shouldReturnStatusOK_whenAbleToGetAllAccount() throws Exception {
        Account account = new Account();
        account.setUserName("h");
        account.setEmail("i");
        account.setPassword("j");
        service.saveAccount(account);

        Account account1 = new Account();
        account1.setUserName("i");
        account1.setEmail("j");
        account1.setPassword("k");
        service.saveAccount(account1);

        controller.getAllAccount(1,2);

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/account")
                .param("page", "1").param("size", "2")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePassword_shouldReturnStatusOK_whenSuccessfullyUpdateUserPassword() throws Exception {
        Account account = new Account();
        account.setUserName("j");
        account.setEmail("k");
        account.setPassword("l");
        service.saveAccount(account);

        account.setPassword("password");
        controller.updatePassword(account);

        RequestBuilder builder = MockMvcRequestBuilders.put("/account/reset-password")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(account));

        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}