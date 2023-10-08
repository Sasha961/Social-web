- В конфигурационном файле Spring Security, в котором живет метод
``public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception`` допишите следующий метод:

```java
@Bean	
CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	configuration.setAllowedOrigins(Arrays.asList("http://192.168.84.187:8101", "http://5.63.154.191:8098"));
	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "HEAD", "OPTIONS"));
	configuration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
	configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
	configuration.setAllowCredentials(true);
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", configuration);
	return source;
}
```
 Сам ``filterChain`` должен иметь следующий вид (приведена основа без конкретных antMatcher):

```java
@Bean
public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception {

	http
        .headers().frameOptions().disable()
        .and().csrf().disable()
        .cors().configurationSource(corsConfigurationSource())
        .and().authorizeRequests()
        .your-mathcer-1
        .your-mathcer-2
        .your-mathcer-x
        .anyRequest().permitAll()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	return http.build();
}
```

В ApplicationController (Application - название вашего микросервиса, если используете интерфейс для контроллера, то тут работаем с его имплементацией) добавить аннотацию 

``@CrossOrigin(origins = {"http://192.168.84.187:8101", "http://5.63.154.191:8098"}, maxAge = 3600)``

Примерно так это будет выглядеть:

```java
@CrossOrigin(origins = {"http://192.168.84.187:8101", "http://5.63.154.191:8098"}, maxAge = 3600)
public class AuthControllerImpl implements AuthController {

	private final PasswordRecoveryService passwordRecoveryService;
	private final AuthService authService;
	private final AuthResponses authResponses;
	private final UserServiceImpl userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final KafkaProducer kafkaProducer;
    ...
    
}
```
Ниже на всякий случай полный листинг класса ``SecurityConfig.class``
<details>
  <summary>SecurityConfig.class</summary>

  ```java
  @Slf4j
@Configuration
@EnableWebSecurity
@PropertySource("secrets.properties")

@AllArgsConstructor
public class SecurityConfig { 
private final UserServiceImpl userService;
private final JwtRequestFilter jwtRequestFilter;
	
    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {

	http
            .headers().frameOptions().disable()
            .and().csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and().authorizeRequests()
            .antMatchers("/your-endpoint/**").permitAll()
            .anyRequest().permitAll()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	return http.build();
}


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://192.168.84.187:8101", "http://5.63.154.191:8098"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "HEAD", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
		configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
  ```
</details>

---

<sub><sup>author: Artem Lebedev / date: 2023-08-23</sup></sub>

---
