package com.platform.mantenimientobackend.maintenance.domain.model.commands;

public record UpdateAssetCommand(Long id, String name, String model) {
}
