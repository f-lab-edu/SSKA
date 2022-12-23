CREATE TABLE `schedule`
(
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `customer_id`   BIGINT(20) UNSIGNED     NULL                  COMMENT '고객 id',
    `study_seat_id` BIGINT(20) UNSIGNED     NULL                  COMMENT '좌석 id',
    `started_time`  DATETIME(6)         NOT NULL                  COMMENT '시작 시긱',
    `end_time`      DATETIME(6)         NOT NULL                  COMMENT '끝나는 시각',
    PRIMARY KEY (`id`),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (study_seat_id) REFERENCES study_seat (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '스케쥴 정보';