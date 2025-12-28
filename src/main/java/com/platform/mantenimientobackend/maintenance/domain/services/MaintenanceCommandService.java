package com.platform.mantenimientobackend.maintenance.domain.services;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CompleteMaintenanceOrderCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateMaintenanceOrderCommand;

import java.util.Optional;

public interface MaintenanceCommandService {
    Long handle(CreateMaintenanceOrderCommand command);
    Optional<MaintenanceOrder> handle(CompleteMaintenanceOrderCommand command);
}
