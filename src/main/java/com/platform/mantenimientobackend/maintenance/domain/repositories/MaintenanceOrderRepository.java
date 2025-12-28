package com.platform.mantenimientobackend.maintenance.domain.repositories;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceOrderRepository extends JpaRepository<MaintenanceOrder, Long> {
    //listar las órdenes de un activo
    List<MaintenanceOrder> findByAssetId(Long assetId);
}
