package com.platform.mantenimientobackend.maintenance.domain.services;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.DeleteAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.UpdateAssetCommand;

import java.util.Optional;

public interface AssetCommandService {
    Long handle(CreateAssetCommand command);
    Optional<Asset> handle(UpdateAssetCommand command);
    void handle(DeleteAssetCommand command);
}
