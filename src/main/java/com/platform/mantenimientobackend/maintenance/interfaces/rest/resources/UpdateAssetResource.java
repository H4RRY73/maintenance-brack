package com.platform.mantenimientobackend.maintenance.interfaces.rest.resources;

public record UpdateAssetResource(String name, String model) {
    public UpdateAssetResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (model == null || model.isBlank()) throw new IllegalArgumentException("Model is required");
    }
}
