package com.platform.mantenimientobackend.maintenance.domain.services;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllMaintenanceOrdersQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrderByIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrdersByAssetIdQuery;

import java.util.List;
import java.util.Optional;

public interface MaintenanceQueryService {
    Optional<MaintenanceOrder> handle(GetMaintenanceOrderByIdQuery query);
    List<MaintenanceOrder> handle(GetMaintenanceOrdersByAssetIdQuery query);
    List<MaintenanceOrder> handle(GetAllMaintenanceOrdersQuery query);
}
