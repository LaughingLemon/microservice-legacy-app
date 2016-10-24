/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import javax.mail.MessagingException;
import nl.conspect.legacy.user.UserService;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Shaun
 */
@ContextConfiguration()
@WebAppConfiguration
public class LogoutControllerIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() throws IOException {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLogoutUser() throws InterruptedException, IOException, MessagingException, Exception {
        mvc.perform(
                get("/logout")
                .param("id", "123"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/app/index"));
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
