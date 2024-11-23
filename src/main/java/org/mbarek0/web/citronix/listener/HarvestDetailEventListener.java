package org.mbarek0.web.citronix.listener;

import lombok.AllArgsConstructor;
import org.mbarek0.web.citronix.event.FieldDeleteEvent;
import org.mbarek0.web.citronix.event.TreeDeletedEvent;
import org.mbarek0.web.citronix.service.HarvestService;
import org.mbarek0.web.citronix.service.TreeService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HarvestDetailEventListener {
    private final HarvestService harvestService;

    @EventListener
    public void handleFieldDeletedEvent(TreeDeletedEvent event) {
        harvestService.deleteAllHarvestDetailsByTreeId(event.getTreeId());
    }
}
