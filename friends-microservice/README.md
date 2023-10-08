# Friends Microservice

![](image/1.png)

## Содержание
- [Описание](#Описание)
- [Технологии](#технологии)
- [Возможности](#Возможности)

## Описание 
Микросервис друзья представляет собой Spring-приложение для проекта социальная сеть, отвечающее за добавление, удаление, блокировку друзей и выдачу рекомендаций о возможных друзьях, работающее с базой данных PostgreSQL, имеющее API, через который им можно управлять и получать результаты по запросу.

## Технологии
- Java Core 
- PostgreSQL
- Kafka
- Spring Boot
- Spring Security, JWT
- Spring Cloud
- Swagger

## Возможности

### API
В данном примере использован [Postman](https://www.postman.com/downloads/)

### Просмотр документации
http://localhost:8083/swagger-ui/index.html#/

#### Подписка на пользователя:
POST - запрос http://localhost:8083/api/v1/friends/subscribe/{id}

- При успешном запуске

  ![](image/2.png)


- При неудаче

  ![](image/3.png)


#### Подтверждение запроса на дружбу:
PUT - запрос http://localhost:8083/api/v1/friends/{id}/approve

- При успешном запуске

  ![](image/4.png)


- При неудаче

  ![](image/3.png)

#### Блокировка пользователя:
PUT - запрос http://localhost:8083/api/v1/friends/block/{id}

- При успешном запуске

  ![](image/5.png)


- При неудаче

  ![](image/3.png)


#### Разблокировка пользователя:
PUT - запрос http://localhost:8083/api/v1/friends/unblock/{id}

- При успешном запуске

  ![](image/6.png)


- При неудаче

  ![](image/3.png)

#### Запрос на пользователя:
POST - запрос http://localhost:8083/api/v1/friends/{id}/request

- При успешном запуске

  ![](image/2.png)


- При неудаче

  ![](image/3.png)

#### Получение записи о дружбе по id записи:
GET - запрос http://localhost:8083/api/v1/friends/{id}

- При успешном запуске

  ![](image/7.png)


- При неудаче

  ![](image/3.png)


#### Удаление существующих отношений с пользователем по идентификатору:
DELETE - запрос http://localhost:8083/api/v1/friends/{id}

- При успешном запуске

  ![](image/8.png)

#### Получение идентификаторов пользователей имеющих заданный статус:
GET - запрос http://localhost:8083/api/v1/friends/status

- При успешном запуске

  ![](image/9.png)


#### Получение списка идентификаторов друзей:
GET - запрос http://localhost:8083/api/v1/friends/friendId

- При успешном запуске

  ![](image/10.png)

#### Получение списка идентификаторов друзей для пользователя с id:
GET - запрос http://localhost:8083/api/v1/friends/friendId/{id}

- При успешном запуске

  ![](image/11.png)


- При неудаче

  ![](image/3.png)

#### Получение количества заявок в друзья:
GET - запрос http://localhost:8083/api/v1/friends/count

- При успешном запуске

  ![](image/12.png)

#### получение идентификаторов пользователей, заблокировавших текущего пользователя:
GET - запрос http://localhost:8083/api/v1/friends/blockFriendId

- При успешном запуске


#### Получение статусов отношений для заданного списка идентификаторов пользователей:
GET - запрос http://localhost:8083/api/v1/friends/check

- При успешном запуске
