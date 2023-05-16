package com.sanhaehong.project.techview.login;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class OAuthLoginTest {

    private static final String[] permitAllURI = {"/",
            "/login",
            "/question/lists?page=0", "/question/view/2"};

    private static final String[] hasRoleUserURI = {};
    private static final String[] hasRoleAdminURI = {};

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("로그인 페이지 정상 접근")
    void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Google OAuth 서비스 진입")
    void OAuthLogin() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/google"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", containsString("https://accounts.google.com/o/oauth2/v2/auth")));
    }

    @Test
    @DisplayName("익명 접근 권한 확인")
    void anonymousAccess() throws Exception {
        String[] permitURI = Arrays.copyOf(permitAllURI, permitAllURI.length);
        for (String uri : permitURI) {
            mockMvc.perform(get(uri)).andExpect(status().is(200));
        }
        String[] deniedURI = Stream.concat(Arrays.stream(hasRoleUserURI), Arrays.stream(hasRoleAdminURI)).toArray(String[]::new);
        for (String uri : deniedURI) {
            mockMvc.perform(get(uri)).andExpect(status().is(403));
        }
    }
}
