package com.sj.resources;

import com.sj.model.UserModel;
import com.sj.resources.user.UserResource;
import com.sj.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResourceTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserResource userResource;
    @Before
    public void beforeEachTest() throws Exception {

    }

    @Test
    public void testGetUser() {
        UserModel userModel = new UserModel("suman");
        when(userService.getUser(1l)).thenReturn(userModel);
        when(userService.saveUser(userModel)).thenReturn(true);
        Response response = userResource.findById(1l);
        assertTrue("Response http status should be 204 i.e. NO_CONTENT", response.getStatus() == Response.Status.OK.getStatusCode());

    }
}
