package com.skka.adaptor.common.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@RequiredArgsConstructor
@ToString
@Getter
public abstract class BaseEntity {

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreatedDate
    private final LocalDateTime createdAt;

    @Column(nullable = false, name = "last_modified")
    @LastModifiedDate
    private final LocalDateTime lastModified;
}
