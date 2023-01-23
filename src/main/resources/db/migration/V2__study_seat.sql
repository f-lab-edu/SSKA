CREATE TABLE `study_seat`
(
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `customer_id`   BIGINT(20) UNSIGNED     NULL                  COMMENT '좌석을 차지한 고객의 id',
    `seat_number`   VARCHAR(30)         NOT NULL                  COMMENT '좌석 번호',
    `occupied`      TINYINT(1)          NOT NULL                  COMMENT '좌석 차지 여부',
    `started_time`  DATETIME(6)             NULL                  COMMENT '해당 자리에서 시작 날짜, 시간',
    `end_time`      DATETIME(6)             NULL                  COMMENT '해당 자리에서 끝나는 날짜, 시간',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '좌석 정보';