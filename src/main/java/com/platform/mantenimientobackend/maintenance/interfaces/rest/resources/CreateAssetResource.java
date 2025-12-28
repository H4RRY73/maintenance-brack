package com.platform.mantenimientobackend.maintenance.interfaces.rest.resources;

public record CreateAssetResource(
        String name,
        String serialNumber,
        String model
) {
    public CreateAssetResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (serialNumber == null || serialNumber.isBlank()) throw new IllegalArgumentException("Serial number is required");
    }
}
