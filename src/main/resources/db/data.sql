INSERT INTO USER (username, password, email, nickname, userId, role, createDate)
VALUES("test1", "1234", "test1@naver.com", "test1","test1", "ROLE_USER", now());

update user set role="ROLE_ADMIN" where username="admin";

