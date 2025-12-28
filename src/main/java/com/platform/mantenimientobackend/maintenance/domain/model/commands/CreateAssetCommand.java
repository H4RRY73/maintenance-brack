package com.platform.mantenimientobackend.maintenance.domain.model.commands;

public record CreateAssetCommand(String name, String serialNumber, String model) {
}
