package com.platform.mantenimientobackend.maintenance.interfaces.rest;

import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.DeleteAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.UpdateAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllAssetsQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAssetByIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.services.AssetCommandService;
import com.platform.mantenimientobackend.maintenance.domain.services.AssetQueryService;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.AssetResource;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.CreateAssetResource;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.UpdateAssetResource;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.transform.AssetResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/assets", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://maintenance-dev-43f4b.web.app")
@Tag(name = "Assets", description = "Asset Management Endpoints")
public class AssetsController {
    private final AssetCommandService assetCommandService;
    private final AssetQueryService assetQueryService;

    public AssetsController(AssetCommandService assetCommandService, AssetQueryService assetQueryService) {
        this.assetCommandService = assetCommandService;
        this.assetQueryService = assetQueryService;
    }

    @PostMapping
    public ResponseEntity<AssetResource> createAsset(@RequestBody CreateAssetResource resource) {
        var command = new CreateAssetCommand(resource.name(), resource.serialNumber(), resource.model());
        var assetId = assetCommandService.handle(command);

        var query = new GetAssetByIdQuery(assetId);
        var asset = assetQueryService.handle(query);

        return asset.map(a -> new ResponseEntity<>(AssetResourceFromEntityAssembler.toResourceFromEntity(a), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<AssetResource>> getAllAssets() {
        var assets = assetQueryService.handle(new GetAllAssetsQuery());
        var resources = assets.stream()
                .map(AssetResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an asset", description = "Updates the name and model of an existing asset.")
    public ResponseEntity<AssetResource> updateAsset(@PathVariable Long id, @RequestBody UpdateAssetResource resource) {
        var command = new UpdateAssetCommand(id, resource.name(), resource.model());
        var updatedAsset = assetCommandService.handle(command);

        return updatedAsset.map(asset -> ResponseEntity.ok(AssetResourceFromEntityAssembler.toResourceFromEntity(asset)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an asset", description = "Deletes an asset from the system.")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        var command = new DeleteAssetCommand(id);

        try {
            assetCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot delete asset because it has related maintenance orders.");
        }
    }
}
