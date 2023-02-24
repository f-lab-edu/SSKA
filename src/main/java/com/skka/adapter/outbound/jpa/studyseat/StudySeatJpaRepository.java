package com.skka.adapter.outbound.jpa.studyseat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudySeatJpaRepository extends JpaRepository<StudySeatEntity, Long> {

}
