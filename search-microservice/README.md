# Search Microservice

![](image/1.png)


## Содержание
- [Описание](#Описание)
- [Технологии](#технологии)
- [Возможности](#Возможности)

## Описание
Микросервис поиска представляет собой Spring-приложение для проекта социальная сеть, отвечающее за поиск по имени людей, названию лайков комментариев, имеющее API, через который им можно управлять и получать результаты по запросу.

## Технологии
- Java Core
- Spring Boot
- Spring Security, JWT
- Spring Cloud
- Swagger

## Возможности

### API
В данном примере использован [Postman](https://www.postman.com/downloads/)

### Просмотр документации
http://localhost:8082/swagger-ui/index.html#/

#### Поиск пользователей:
GET - запрос http://localhost:8082/api/v1/search/users

- При успешном ответе


