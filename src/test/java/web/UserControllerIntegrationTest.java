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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
public class UserControllerIntegrationTest extends AbstractJUnit4SpringContextTests {

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
    public void testNewUser() throws InterruptedException, IOException, MessagingException, Exception {
        mvc.perform(
                post("/newuser")
                .param("id", "123")
                .param("username", "foobar")
                .param("password", "password")
                .param("displayName", "Foo Bar")
                .param("emailAddress", "foo@somewhere.org")
                .param("emailValidation", "foo@somewhere.org")
                .param("passwordValidation", "password"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("newuser-success"));

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService, times(1)).save(argumentCaptor.capture());

        User user = argumentCaptor.getValue();
        assertThat(user.getUsername()).isNotEmpty().contains("foobar");
        assertThat(user.getPassword()).isNotEmpty().contains("password");
        assertThat(user.getDisplayName()).isNotEmpty().contains("Foo Bar");
        assertThat(user.getEmailAddress()).isNotEmpty().contains("foo@somewhere.org");
        assertThat(user.getId()).isEqualTo(0L); // Binding id is disallowed hence it should remain 0    }
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
