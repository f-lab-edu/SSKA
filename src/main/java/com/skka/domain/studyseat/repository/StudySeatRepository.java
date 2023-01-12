package com.skka.domain.studyseat.repository;

import com.skka.domain.studyseat.StudySeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudySeatRepository extends JpaRepository<StudySeat, Long> {

}
