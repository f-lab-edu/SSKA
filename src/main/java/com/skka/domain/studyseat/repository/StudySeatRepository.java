package com.skka.domain.studyseat.repository;

import com.skka.domain.studyseat.StudySeat;

public interface StudySeatRepository {
    public StudySeat save(StudySeat studySeat);

    public StudySeat findById(long id);

    public StudySeat findByIdForLock(long id);
}
