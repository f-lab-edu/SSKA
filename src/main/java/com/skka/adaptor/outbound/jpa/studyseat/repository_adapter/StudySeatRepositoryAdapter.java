package com.skka.adaptor.outbound.jpa.studyseat.repository_adapter;

import com.skka.adaptor.outbound.jpa.studyseat.StudySeatEntity;
import com.skka.adaptor.outbound.jpa.studyseat.repository.StudySeatJpaRepository;
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
        System.out.println("!!! = " + studySeat);
        StudySeatEntity entity = studySeat.toStudySeatEntity();
        System.out.println("@@@@@ = " + entity);
        jpaRepository.save(entity);
        return entity.toStudySeat();
    }

    @Override
    public StudySeat findById(long id) {
        StudySeatEntity foundEntity = jpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
        return foundEntity.toStudySeat();
    }
}
