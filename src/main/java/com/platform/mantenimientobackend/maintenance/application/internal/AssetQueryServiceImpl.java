package com.platform.mantenimientobackend.maintenance.application.internal;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllAssetsQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAssetByIdQuery;
import com.platform.mantenimientobackend.maintenance.domain.repositories.AssetRepository;
import com.platform.mantenimientobackend.maintenance.domain.services.AssetQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetQueryServiceImpl implements AssetQueryService {
    private final AssetRepository assetRepository;

    public AssetQueryServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Asset> handle(GetAllAssetsQuery query) {
        return assetRepository.findAll();
    }

    @Override
    public Optional<Asset> handle(GetAssetByIdQuery query) {
        return assetRepository.findById(query.id());
    }
}
