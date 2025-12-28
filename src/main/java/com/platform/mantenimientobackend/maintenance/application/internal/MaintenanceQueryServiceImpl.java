package com.platform.mantenimientobackend.maintenance.application.internal;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllMaintenanceOrdersQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrderByIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrdersByAssetIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.repositories.MaintenanceOrderRepository;
import com.platform.mantenimientobackend.maintenance.domain.services.MaintenanceQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceQueryServiceImpl implements MaintenanceQueryService {

    private final MaintenanceOrderRepository maintenanceOrderRepository;

    public MaintenanceQueryServiceImpl(MaintenanceOrderRepository maintenanceOrderRepository) {
        this.maintenanceOrderRepository = maintenanceOrderRepository;
    }

    @Override
    public Optional<MaintenanceOrder> handle(GetMaintenanceOrderByIdQuery query) {
        return maintenanceOrderRepository.findById(query.orderId());
    }

    @Override
    public List<MaintenanceOrder> handle(GetMaintenanceOrdersByAssetIdQuery query) {
        return maintenanceOrderRepository.findByAssetId(query.assetId());
    }

    @Override
    public List<MaintenanceOrder> handle(GetAllMaintenanceOrdersQuery query) {
        return maintenanceOrderRepository.findAll();
    }
}