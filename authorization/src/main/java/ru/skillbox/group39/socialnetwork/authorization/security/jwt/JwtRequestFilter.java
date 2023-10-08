//package ru.skillbox.socialnetwork.authorization.security.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.security.web.context.SecurityContextRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//	private final JwtTokenUtils jwtTokenUtils;
//	private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
//
//	@Override
//	protected void doFilterInternal(@NonNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		String authHeader = request.getHeader("Authorization");
//		String username = null;
//		String jwt = null;
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//			jwt = authHeader.substring(7);
//			try {
//				username = jwtTokenUtils.getUsername(jwt);
//			} catch (ExpiredJwtException e) {
//				log.debug("Token lifetime expired");
//			} catch (SignatureException e) {
//				log.debug("Wrong signature");
//			}
//		}
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			UsernamePasswordAuthenticationToken token =
//					new UsernamePasswordAuthenticationToken(
//							username,
//							null,
//							jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//			);
//			token.setDetails(new WebAuthenticationDetailsSource().buildDetails(Objects.requireNonNull(request)));
//			SecurityContextHolder.getContext().setAuthentication(token);
//			securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
//
//		}
//		filterChain.doFilter(request, response);
//
//	}
//}
//
