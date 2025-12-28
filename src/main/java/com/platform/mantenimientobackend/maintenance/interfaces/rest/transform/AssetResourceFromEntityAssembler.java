package com.platform.mantenimientobackend.maintenance.interfaces.rest.transform;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.interfaces.rest.resources.AssetResource;

public class AssetResourceFromEntityAssembler {
    public static AssetResource toResourceFromEntity(Asset entity) {
        return new AssetResource(
                entity.getId(),
                entity.getName(),
                entity.getSerialNumber(),
                entity.getModel(),
                entity.getStatus().name()
        );
    }
}