package com.platform.mantenimientobackend.maintenance.application.internal;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.CreateAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.DeleteAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.model.commands.UpdateAssetCommand;
import com.platform.mantenimientobackend.maintenance.domain.repositories.AssetRepository;
import com.platform.mantenimientobackend.maintenance.domain.services.AssetCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetCommandServiceImpl implements AssetCommandService {
    private final AssetRepository assetRepository;

    public AssetCommandServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public Long handle(CreateAssetCommand command) {
        if (assetRepository.existsBySerialNumber(command.serialNumber())) {
            throw new IllegalArgumentException("Asset with serial number " + command.serialNumber() + " already exists");
        }
        var asset = new Asset(command.name(), command.serialNumber(), command.model());
        assetRepository.save(asset);
        return asset.getId();
    }

    @Override
    public Optional<Asset> handle(UpdateAssetCommand command) {
        var result = assetRepository.findById(command.id());
        if (result.isEmpty()) throw new IllegalArgumentException("Asset not found");

        var assetToUpdate = result.get();
        assetToUpdate.updateInformation(command.name(), command.model());
        return Optional.of(assetRepository.save(assetToUpdate));
    }

    @Override
    public void handle(DeleteAssetCommand command) {
        if (!assetRepository.existsById(command.id())) throw new IllegalArgumentException("Asset not found");
        assetRepository.deleteById(command.id());
    }
}
