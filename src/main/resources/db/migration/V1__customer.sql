CREATE TABLE `customer`
(
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(20)         NOT NULL                  COMMENT '고객 이름',
    `email`         VARCHAR(50)         NOT NULL                  COMMENT '고객 이메일',
    `password`      VARCHAR(100)        NOT NULL                  COMMENT '고객 비밀번호',
    `tel`           VARCHAR(20)         NOT NULL                  COMMENT '고객 전화번호',
    `createdAt`     TIMESTAMP           NOT NULL   DEFAULT CURRENT_TIMESTAMP,
    `updatedAt`     TIMESTAMP           NOT NULL   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '고객 정보';