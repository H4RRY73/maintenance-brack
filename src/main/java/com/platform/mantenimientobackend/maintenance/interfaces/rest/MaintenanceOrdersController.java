package com.platform.mantenimientobackend.maintenance.interfaces.rest;

import com.platform.mantenimientobackend.maintenance.domain.model.commands.CompleteMaintenanceOrderCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateMaintenanceOrderCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllMaintenanceOrdersQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrderByIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetMaintenanceOrdersByAssetIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.MaintenanceType;
import com.platform.mantenimientobackend.maintenance.domain.services.MaintenanceCommandService;
import com.platform.mantenimientobackend.maintenance.domain.services.MaintenanceQueryService;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.CreateMaintenanceOrderResource;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.MaintenanceOrderResource;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.transform.MaintenanceOrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/maintenance-orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Maintenance Orders", description = "Operations related to equipment maintenance")
public class MaintenanceOrdersController {

    private final MaintenanceCommandService maintenanceCommandService;
    private final MaintenanceQueryService maintenanceQueryService;

    public MaintenanceOrdersController(MaintenanceCommandService maintenanceCommandService, MaintenanceQueryService maintenanceQueryService) {
        this.maintenanceCommandService = maintenanceCommandService;
        this.maintenanceQueryService = maintenanceQueryService;
    }

    @PostMapping
    @Operation(summary = "Schedule a new maintenance order", description = "Creates a maintenance order and updates the asset status to UNDER_MAINTENANCE.")
    public ResponseEntity<MaintenanceOrderResource> createMaintenanceOrder(@RequestBody CreateMaintenanceOrderResource resource) {

        MaintenanceType typeEnum;
        try {
            typeEnum = MaintenanceType.valueOf(resource.type());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        var command = new CreateMaintenanceOrderCommand(
                resource.assetId(),
                resource.description(),
                typeEnum,
                resource.scheduledDate()
        );

        var orderId = maintenanceCommandService.handle(command);

        if (orderId == null) return ResponseEntity.badRequest().build();

        var order = maintenanceQueryService.handle(new GetMaintenanceOrderByIdQuery(orderId));

        return order.map(o -> new ResponseEntity<>(MaintenanceOrderResourceFromEntityAssembler.toResourceFromEntity(o), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{orderId}/complete")
    @Operation(summary = "Complete a maintenance order", description = "Marks the order as COMPLETED and restores the asset status to OPERATIONAL.")
    public ResponseEntity<MaintenanceOrderResource> completeMaintenanceOrder(@PathVariable Long orderId) {
        var command = new CompleteMaintenanceOrderCommand(orderId);
        var completedOrder = maintenanceCommandService.handle(command);

        return completedOrder.map(o -> ResponseEntity.ok(MaintenanceOrderResourceFromEntityAssembler.toResourceFromEntity(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get orders by Asset ID", description = "Retrieve all maintenance orders associated with a specific asset.")
    public ResponseEntity<List<MaintenanceOrderResource>> getOrdersByAsset(@RequestParam Long assetId) {
        var orders = maintenanceQueryService.handle(new GetMaintenanceOrdersByAssetIdQuery(assetId));

        var resources = orders.stream()
                .map(MaintenanceOrderResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all orders", description = "Retrieve all maintenance orders.")
    public ResponseEntity<List<MaintenanceOrderResource>> getAllOrders() {
        var orders = maintenanceQueryService.handle(new GetAllMaintenanceOrdersQuery());

        var resources = orders.stream()
                .map(MaintenanceOrderResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
}
