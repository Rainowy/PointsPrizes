package pl.coderslab.login;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.login.entity.User;
import pl.coderslab.login.repository.UserRepository;
import pl.coderslab.login.service.UserService;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class LoginApplicationTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

//	@Test
//	void contextLoads() {
//	}

//	@Test(expected = NullPointerException.class)
	public void when_save_return_user(){
		User testUser = new User();
		testUser.setName("test_user");

		when(userRepository.save(any(User.class))).thenReturn(new User());

		User created = userService.saveUser(testUser);
		String name =created.getName();
//		String name= testUser.getName();

//		assertThat(name).isSameAs(testUser.getName());
		assertEquals(name, testUser.getName());
	}


}
