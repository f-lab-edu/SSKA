package com.skka.domain.studyseat.repository;

import com.skka.domain.studyseat.StudySeat;
import java.util.Optional;

public interface StudySeatRepository {
    public StudySeat save(final StudySeat studySeat);

    public Optional<StudySeat> findById(final long id);

    public Optional<StudySeat> findByIdForLock(final long id);
}
