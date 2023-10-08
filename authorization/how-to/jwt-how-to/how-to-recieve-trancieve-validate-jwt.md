# Небольшой how-to при работе с JWT #

## Прием ##

Передавать мы токен будет в HEADER Authentication REST API запроса. Я пока не знаю как \то будет делать фронт, но наш контроллер, который должен (кому и что не понятно) его принять, будет выглядеть так:


```java
public Object getClaims(@RequestHeader("Authorization") @NonNull String bearerToken) 
{
    String[] parts = bearerToken.split(" ");
    String jwtToken = parts[1];
    return ResponseEntity.ok;
}
```

JWT != Authorization token, поэтому мы отсекаем первые 7-мь символов.

| Name | Value |
|:-----|:------|
|  JWT     |  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnRlbV9MZWJlZGV2QGdtYWlsLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImV4cCI6MTY4OTAyMjgyOCwiaWF0IjoxNjg5MDIyNTI4fQ.wXOn2Mmgv5OgsGsjiyHwPDJ04jpmUe91p_1RP9tMTbY      |
|   Auhorization HEADER |  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnRlbV9MZWJlZGV2QGdtYWlsLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImV4cCI6MTY4OTAyMjgyOCwiaWF0IjoxNjg5MDIyNTI4fQ.wXOn2Mmgv5OgsGsjiyHwPDJ04jpmUe91p_1RP9tMTbY |

## Валидация ##

Для того, чтобы проверить не истек ли срок действия токена, достаточно его попытаться спарсить.

Можно вот так:

```java	
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
```

После того, как стало понятно, просрочен он или нет, можно получить Claims, а из claims - username, roles и другую инфу, зашитую в токен.

[Claims]: https://www.multitran.com/m.exe?s=claims&amp;l1=2&amp;l2=1	"заявленное значение"

``````java
	public Claims getAllClaimsFromToken(String token) {
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
	}
``````

Коды выше даст нам следующий ответ, если мы его вернем как ResponseEntity

> {
>
>       "sub": "Artem_Lebedev@gmail.com",
>
>       "roles": [
>           "ADMIN"
>                ],
> 
>       "exp": 1689108210,
>       "iat": 1689107910,
>       "username": "Artem_Lebedev@gmail.com"
> }



Получим username (в случае сервиса аутентификации это email, но не важно, это внутреннее обозначение), Обратите внимание, что без **secret** мы получим 

> JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.

![getClaimsFromToken](.\getclaims.gif)



Пока все. Если получиться и будет необходимость в работе с JWT через Interceptor - добавлю сюда

[JwtTokenUtils](.\JwtTokenUtils.java) Почти шаблонный код из многих свободных источников
