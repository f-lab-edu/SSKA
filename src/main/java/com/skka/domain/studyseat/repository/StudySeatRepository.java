package com.skka.domain.studyseat.repository;

import com.skka.domain.studyseat.StudySeat;

public interface StudySeatRepository {
    public StudySeat save(final StudySeat studySeat);

    public StudySeat findById(final long id);

    public StudySeat findByIdForLock(final long id);
}
