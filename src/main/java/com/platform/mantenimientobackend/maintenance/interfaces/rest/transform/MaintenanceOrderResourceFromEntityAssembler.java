package com.platform.mantenimientobackend.maintenance.interfaces.rest.transform;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.MaintenanceOrderResource;

public class MaintenanceOrderResourceFromEntityAssembler {
    public static MaintenanceOrderResource toResourceFromEntity(MaintenanceOrder entity) {
        return new MaintenanceOrderResource(
                entity.getId(),
                entity.getDescription(),
                entity.getStatus().name(),
                entity.getType().name(),
                entity.getScheduledDate(),
                entity.getAsset().getId(),
                entity.getAsset().getName(),
                entity.getAsset().getSerialNumber()
        );
    }
}
