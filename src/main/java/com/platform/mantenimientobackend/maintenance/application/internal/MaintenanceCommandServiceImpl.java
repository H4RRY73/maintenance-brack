package com.platform.mantenimientobackend.maintenance.application.internal;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.MaintenanceOrder;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CompleteMaintenanceOrderCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateMaintenanceOrderCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.AssetStatus;
import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.platform.mantenimientobackend.maintenance.domain.repositories.AssetRepository;
import com.platform.mantenimientobackend.maintenance.domain.repositories.MaintenanceOrderRepository;
import com.platform.mantenimientobackend.maintenance.domain.services.MaintenanceCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MaintenanceCommandServiceImpl implements MaintenanceCommandService {

    private final MaintenanceOrderRepository maintenanceOrderRepository;
    private final AssetRepository assetRepository;

    public MaintenanceCommandServiceImpl(MaintenanceOrderRepository maintenanceOrderRepository, AssetRepository assetRepository) {
        this.maintenanceOrderRepository = maintenanceOrderRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateMaintenanceOrderCommand command) {
        //Validar existencia del Asset
        Asset asset = assetRepository.findById(command.assetId())
                .orElseThrow(() -> new IllegalArgumentException("Asset with ID " + command.assetId() + " not found"));

        //No se puede programar mantenimiento si ya está dado de baja
        if (asset.getStatus() == AssetStatus.OUT_OF_SERVICE) {
            throw new IllegalStateException("Cannot schedule maintenance for an OUT_OF_SERVICE asset");
        }

        //Crear la Orden
        MaintenanceOrder order = new MaintenanceOrder(
                command.description(),
                command.type(),
                command.scheduledDate(),
                asset
        );

        // Al crear la orden, asumimos que se bloquea el equipo o se prepara para ello
        asset.updateStatus(AssetStatus.UNDER_MAINTENANCE);

        // Guardar ambos
        assetRepository.save(asset);
        maintenanceOrderRepository.save(order);

        return order.getId();
    }

    @Override
    @Transactional
    public Optional<MaintenanceOrder> handle(CompleteMaintenanceOrderCommand command) {
        //Buscar la orden
        Optional<MaintenanceOrder> orderOptional = maintenanceOrderRepository.findById(command.orderId());

        if (orderOptional.isPresent()) {
            MaintenanceOrder order = orderOptional.get();

            //Validar que no esté ya completada
            if (order.getStatus() == MaintenanceStatus.COMPLETED) {
                return Optional.of(order);
            }

            //Completar la orden
            order.completeWork();

            //Liberar el Activo (Volver a Operativo)
            Asset asset = order.getAsset();
            asset.updateStatus(AssetStatus.OPERATIONAL);

            //Guardar cambios
            assetRepository.save(asset);
            maintenanceOrderRepository.save(order);

            return Optional.of(order);
        }

        return Optional.empty();
    }
}
