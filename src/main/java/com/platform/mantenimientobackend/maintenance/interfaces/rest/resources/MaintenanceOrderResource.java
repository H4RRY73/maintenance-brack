package com.platform.mantenimientobackend.maintenance.interfaces.rest.resources;

import java.util.Date;

public record MaintenanceOrderResource(Long id, String description, String status, String type, Date scheduledDate,
        Long assetId, String assetName, String assetSerialNumber
) {}
