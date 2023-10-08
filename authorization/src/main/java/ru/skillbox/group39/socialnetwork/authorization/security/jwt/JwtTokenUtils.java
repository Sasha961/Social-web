package ru.skillbox.group39.socialnetwork.authorization.security.jwt;

import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

	@Value("${jwt.secret}")
	private String SECRET;
	private final SignatureAlgorithm TOKEN_ALG = SignatureAlgorithm.HS256;

	public String generateToken(@NonNull final Person person,
	                            @NotNull final Duration duration) {
		final Map<String, Object> claims = collectClaims(person);
		final Date issuedDate = new Date();
		final Date expiredDate = new Date(issuedDate.getTime() + duration.toMillis());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(person.getUsername())
				.setIssuedAt(issuedDate)
				.setExpiration(expiredDate)
				.signWith(TOKEN_ALG, SECRET)
				.compact();
	}

	@NonNull
	private static Map<String, Object> collectClaims(@NonNull final Person person) {
		final Map<String, Object> claims = new HashMap<>();
		final List<String> rolesList = person.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		claims.put("roles", rolesList);
		claims.put("userId", person.getId());
		claims.put("firstName", person.getFirstName());
		claims.put("lastName", person.getLastName());
		return claims;
	}

	public String getUsername(final String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public List<String> getRoles(final String token) {
		return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
	}

	public <T> T getClaimFromToken(final String token, @NonNull final Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(final String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			claims =  e.getClaims();
		}
		return claims;
	}

	public Boolean isJwtTokenIsNotExpired(final String bearerToken) {
		boolean isNotExpired = false;
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(bearerToken);
			isNotExpired = true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return isNotExpired;
	}
}
