package com.skka.adapter.outbound.jpa.studyseat;

import static com.skka.adapter.outbound.jpa.studyseat.StudySeatEntity.toStudySeatEntityWithScheduleEntity;

import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@RequiredArgsConstructor
public class StudySeatRepositoryAdapter implements StudySeatRepository {

    private final StudySeatJpaRepository jpaRepository;

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
