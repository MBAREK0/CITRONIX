package org.mbarek0.web.citronix.listener;

import lombok.AllArgsConstructor;
import org.mbarek0.web.citronix.event.FieldDeleteEvent;
import org.mbarek0.web.citronix.service.TreeService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TreeEventListener {
    private final TreeService treeService;

    @EventListener
    public void handleFieldDeletedEvent(FieldDeleteEvent event) {
        treeService.deleteAllTreesByFieldId(event.getFieldId());
    }
}
