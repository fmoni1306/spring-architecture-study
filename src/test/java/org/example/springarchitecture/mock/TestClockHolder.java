package org.example.springarchitecture.mock;

import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.common.service.port.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;

    @Override
    public long millis() {
        return millis;
    }
}
