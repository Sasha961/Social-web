 - Создайте класс с любым названием, у меня это CorsFilter.class
в нем должен быть следующий код

```java
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CorsFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "http://5.63.154.191:8098");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addIntHeader("Access-Control-Max-Age", 10);
		filterChain.doFilter(request, response);
	}
}

```

 - В файле **SecurityConfig.class** в методе **filterChain** добавьте строку

``java
.addFilterBefore(new CorsFilter(), org.springframework.web.filter.CorsFilter.class)
``

Закомментируйте строку

``.cors().disable()``

Должно получиться следующее (см. нижк)

```java
@Bean
	public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception {

		http
				.headers().frameOptions().disable()
				.and()
				.addFilterBefore(new CorsFilter(), org.springframework.web.filter.CorsFilter.class)
				.csrf().disable()
//				.cors().disable()
				.authorizeRequests()
				.antMatchers("ваш матчер").permitAll()
				.antMatchers("ваш матчер 2").authenticated()
				.antMatchers("ваш матчер 3").hasRole("USER")
				.anyRequest().permitAll()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		return http.build();
	}
```
