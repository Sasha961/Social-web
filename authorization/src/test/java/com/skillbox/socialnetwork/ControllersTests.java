//package com.skillbox.socialnetwork;
//
//import com.skillbox.socialnetwork.client.ExternalClient;
//import com.skillbox.socialnetwork.dto.auth.AuthenticateResponseDto;
//import com.skillbox.socialnetwork.dto.user.AccountSecureDto;
//import com.skillbox.socialnetwork.dto.user.UserResponseDto;
//import com.skillbox.socialnetwork.exceptions.AppError;
//import com.skillbox.socialnetwork.jwt.JwtTokenUtils;
//import com.skillbox.socialnetwork.jwt.dto.JwtRequest;
//import com.skillbox.socialnetwork.responses.AuthResponses;
//import com.skillbox.socialnetwork.services.AuthService;
//import com.skillbox.socialnetwork.services.PasswordRecoveryService;
//import com.skillbox.socialnetwork.services.UserService;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.Duration;
//import java.util.ArrayList;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ControllersTests {
//
//	@Autowired
//	private MockMvc mockito;
//
//	@Autowired
//	private JwtTokenUtils jwtTokenUtils;
//
//	@Autowired
//	private UserService userService;
//
//	@MockBean
//	private AuthService authService;
//
//	@MockBean
//	private PasswordRecoveryService passwordRecoveryService;
//
//	@MockBean
//	private AuthResponses authResponses;
//
//	@MockBean
//	private ExternalClient externalClient;
//
////	@Autowired
////	private WebApplicationContext webApplicationContext;
////
////	@Before("")
////	public void setUp() {
////		mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////	}
//
//	@Test
//	void loginTest_Successfully() throws Exception {
//		JwtRequest jwtRequest = new JwtRequest("foo@bar.com", "foobar");
//		Duration duration = Duration.ofMillis(31337);
//		UserDetails userDetails = new User("foo", "foobar", new ArrayList<SimpleGrantedAuthority>());
//		AccountSecureDto accountSecureDto = new AccountSecureDto("1",
//				false,
//				"foo",
//				"bar",
//				"foo@bar.com",
//				"foobar",
//				"ADMIN, USER");
//
//		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
//				jwtTokenUtils.generateToken(userDetails, duration, SignatureAlgorithm.HS512),
//				jwtTokenUtils.generateToken(userDetails, duration.minusMillis(31000), SignatureAlgorithm.HS512));
//		UserResponseDto userResponseDto = new UserResponseDto(accountSecureDto, true);
//		when(externalClient.getUserDetails(anyString())).thenReturn(userResponseDto);
////		when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
//		when(authService.login(jwtRequest)).thenReturn(authenticateResponseDto);
//
//		mockito.perform(post("/login")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{\n" +
//								"\"email\": \"Artem_Lebedev@gmail.com\", \n" +
//								"\"password\": \"5\"" +
//								"\n}"))
//				.andDo(print())
//				.andExpect(status().is2xxSuccessful());
//
//	}
//
//	@Test
//	void loginTest_BadRequest() throws Exception {
//		JwtRequest jwtRequest = new JwtRequest("foo@bar.com", "foobar");
//		Duration duration = Duration.ofMillis(31337);
//		UserDetails userDetails = new User("foo", "foobar", new ArrayList<SimpleGrantedAuthority>());
//		AccountSecureDto accountSecureDto = new AccountSecureDto("1",
//				false,
//				"foo",
//				"bar",
//				"foo@bar.com",
//				"foobar",
//				"ADMIN, USER");
//		UserResponseDto userResponseDto = new UserResponseDto(accountSecureDto, true);
//		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
//				jwtTokenUtils.generateToken(userDetails, duration, SignatureAlgorithm.HS512),
//				jwtTokenUtils.generateToken(userDetails, duration.minusMillis(31000), SignatureAlgorithm.HS512));
//		when(externalClient.getUserDetails(anyString())).thenReturn(userResponseDto);
////		when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
//		when(authService.login(jwtRequest)).thenReturn(authenticateResponseDto);
//
//		mockito.perform(post("/login")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(""))
//				.andDo(print())
//				.andExpect(status().isBadRequest());
//
//	}
//
//	@Test
//	@WithMockUser
//	void loginTest_Unauthorized() throws Exception {
//		JwtRequest jwtRequest = new JwtRequest("foo@bar.com", "foobar");
//		Duration duration = Duration.ofMillis(31337);
//		UserDetails userDetails = new User("foo", "foobar", new ArrayList<SimpleGrantedAuthority>());
//		AccountSecureDto accountSecureDto = new AccountSecureDto("1",
//				false,
//				"foo",
//				"bar",
//				"foo@bar.com",
//				"foobar",
//				"ADMIN, USER");
//
//		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
//				jwtTokenUtils.generateToken(userDetails, duration, SignatureAlgorithm.HS512),
//				jwtTokenUtils.generateToken(userDetails, duration.minusMillis(31000), SignatureAlgorithm.HS512));
//
//		when(externalClient.getUserDetails(anyString())).thenReturn(null);
////		when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
//
//		when(authService.login(jwtRequest)).thenReturn(
//				new ResponseEntity<>(
//						new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong password!"), HttpStatus.UNAUTHORIZED));
//
//		mockito.perform(post("/login")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{\n" +
//								"\"email\": \"artlebedev2006@gmail.com\", \n" +
//								"\"password\": \"5\"" +
//								"\n}"))
//				.andDo(print())
//				.andExpect(status().isUnauthorized());
//
//	}
//
//}
//
////
////	JwtRequest jwtRequest = new JwtRequest("foo@bar.com", "bar");
////	User user = new User("foo", "bar", new ArrayList<SimpleGrantedAuthority>());
////	Duration duration = Duration.ofMillis(31337);
////	String refreshToken = jwtTokenUtils.generateToken(user, duration, SignatureAlgorithm.HS512);
////	String accessToken = jwtTokenUtils.generateToken(user, duration.minusMillis(31000), SignatureAlgorithm.HS256);
