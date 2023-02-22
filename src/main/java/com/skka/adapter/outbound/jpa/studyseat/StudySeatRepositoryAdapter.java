package com.skka.adapter.outbound.jpa.studyseat;

import static com.skka.adapter.util.EntityConverter.toStudySeatEntityWithScheduleEntity;

import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@RequiredArgsConstructor
public class StudySeatRepositoryAdapter implements StudySeatRepository {

    private final StudySeatJpaRepository jpaRepository;

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    @Override
    public StudySeat save(StudySeat studySeat) {
        StudySeatEntity entity = toStudySeatEntityWithScheduleEntity(studySeat);
        jpaRepository.save(entity);
        return entity.toStudySeatReturn();
    }

    @Override
    public StudySeat findById(long id) {
        StudySeatEntity foundEntity = jpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
        return foundEntity.toStudySeatReturn();
    }
}
