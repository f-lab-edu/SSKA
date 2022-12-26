package com.skka.studyseat;

import com.skka.domain.studyseat.StudySeat;

public class StudySeatFixture {

    public static StudySeat STUDY_SEAT = StudySeat.of(
        1L,
        "1",
        true
    );

    public static StudySeat studySeatConstructor() {

        return StudySeat.of(
            1L,
            "1",
            true
        );
    }
}
