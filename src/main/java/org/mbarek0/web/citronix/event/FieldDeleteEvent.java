package org.mbarek0.web.citronix.event;

import java.util.UUID;

public class FieldDeleteEvent {
    private final UUID fieldId;


    public FieldDeleteEvent(UUID fieldId) {
        this.fieldId = fieldId;

    }

    public UUID getFieldId() {
        return fieldId;
    }

}
