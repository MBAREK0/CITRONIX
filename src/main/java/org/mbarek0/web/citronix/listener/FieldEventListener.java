package org.mbarek0.web.citronix.listener;

import lombok.AllArgsConstructor;
import org.mbarek0.web.citronix.event.FarmDeletedEvent;
import org.mbarek0.web.citronix.service.FieldService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FieldEventListener {

    private final FieldService fieldService;

    @EventListener
    public void handleFarmDeletedEvent(FarmDeletedEvent event) {
        fieldService.deleteAllFieldsByFarmId(event.getFarmId());
    }
}
