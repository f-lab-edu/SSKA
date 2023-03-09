package com.skka.adapter.outbound.jpa.studyseat;

import static com.skka.adapter.outbound.jpa.studyseat.StudySeatEntity.toStudySeatEntityWithScheduleEntity;

import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@RequiredArgsConstructor
public class StudySeatRepositoryAdapter implements StudySeatRepository {

    private final StudySeatJpaRepository jpaRepository;

    private final StudySeatJpaLockRepository studySeatJpaLockRepository;

    @Override
    public StudySeat save(StudySeat studySeat) {
        StudySeatEntity entity = toStudySeatEntityWithScheduleEntity(studySeat);
        jpaRepository.save(entity);
        return entity.toStudySeatReturn();
    }

    @Override
    public Optional<StudySeat> findById(long id) {
        return jpaRepository.findById(id)
            .map(StudySeatEntity::toStudySeatReturn);
    }

    @Override
    public Optional<StudySeat> findByIdForLock(long id) {
        return studySeatJpaLockRepository.findById(id)
            .map(StudySeatEntity::toStudySeatReturn);
    }
}
