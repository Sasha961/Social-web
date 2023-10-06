package com.skillbox.socialnetwork.jwt;

import com.skillbox.socialnetwork.dto.user.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtils {

	@Value("${jwt.secret}")
	private String secret;

//	@Value("${jwt.lifetime}")
//	private Duration jwtLifetime;


	public String generateToken(@NonNull CustomUserDetails userDetails, @NotNull Duration duration, SignatureAlgorithm algorithm) {
		Map<String, Object> claims = collectClaims(userDetails);
		Date issuedDate = new Date();
		Date expiredDate = new Date(issuedDate.getTime() + duration.toMillis());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(issuedDate)
				.setExpiration(expiredDate)
				.signWith(algorithm, secret)
				.compact();
	}

	@NonNull
	private static Map<String, Object> collectClaims(@NonNull CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		List<String> rolesList = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		claims.put("roles", rolesList);
		claims.put("username", userDetails.getUsername());
		claims.put("userId", userDetails.getId());
		return claims;
	}

	public String getUsername(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public List<String> getRoles(String token) {
		return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
	}

	public  <T> T getClaimFromToken(String token, @NonNull Function<Claims, T> claimsResolver) {
	Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
	}

	public Boolean isJwtTokenIsNotExpired(String bearerToken) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
