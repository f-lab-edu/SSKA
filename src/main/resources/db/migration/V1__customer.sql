CREATE TABLE `customer`
(
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(20)         NOT NULL                  COMMENT '고객 이름',
    `email`         VARCHAR(50)         NOT NULL                  COMMENT '고객 이메일',
    `password`      VARCHAR(100)        NOT NULL                  COMMENT '고객 비밀번호',
    `tel`           VARCHAR(20)         NOT NULL                  COMMENT '고객 전화번호',
    `created_at`    DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`    DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '고객 정보';