package org.mbarek0.web.citronix.event;

import java.util.UUID;

public class FarmDeletedEvent {
    private final UUID farmId;

    public FarmDeletedEvent(UUID farmId) {
        this.farmId = farmId;
    }

    public UUID getFarmId() {
        return farmId;
    }
}
