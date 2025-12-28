package com.platform.mantenimientobackend.maintenance.domain.model.commands;

import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.MaintenanceType;

import java.util.Date;

public record CreateMaintenanceOrderCommand(
        Long assetId,
        String description,
        MaintenanceType type,
        Date scheduledDate
) {}
