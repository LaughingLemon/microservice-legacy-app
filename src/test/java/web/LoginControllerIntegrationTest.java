/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import javax.mail.MessagingException;
import nl.conspect.legacy.user.User;
import nl.conspect.legacy.user.UserService;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Shaun
 */
@ContextConfiguration()
@WebAppConfiguration
public class LoginControllerIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() throws IOException {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLoginUser() throws InterruptedException, IOException, MessagingException, Exception {
        when(userService.login("foobar", "password")).thenReturn(new User());
        mvc.perform(
                post("/login")
                .param("id", "123")
                .param("j_username", "foobar")
                .param("j_password", "password"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("account"));

        verify(userService).login(Matchers.eq("foobar"), Matchers.eq("password"));
    }

    @Test
    public void testLoginInvalidUser() throws InterruptedException, IOException, MessagingException, Exception {
        when(userService.login("foobar", "password")).thenReturn(null);
        mvc.perform(
                post("/login")
                .param("id", "123")
                .param("j_username", "barfoo")
                .param("j_password", "password"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("index"));

        verify(userService).login(Matchers.eq("barfoo"), Matchers.eq("password"));
    }

    @Configuration
    @ImportResource("classpath:/META-INF/spring/dispatcher-servlet.xml")
    public static class TestConfiguration {

        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }
}
