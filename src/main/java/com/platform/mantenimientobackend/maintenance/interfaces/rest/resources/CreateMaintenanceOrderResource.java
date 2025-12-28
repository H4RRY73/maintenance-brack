package com.platform.mantenimientobackend.maintenance.interfaces.rest.resources;

import java.util.Date;

public record CreateMaintenanceOrderResource(
        Long assetId,
        String description,
        String type,
        Date scheduledDate
) {
    public CreateMaintenanceOrderResource {
        if (assetId == null) throw new IllegalArgumentException("Asset ID is required");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description is required");
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Type is required");
    }
}
