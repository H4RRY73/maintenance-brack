package com.platform.mantenimientobackend.maintenance.domain.services;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAllAssetsQuery;
import com.platform.mantenimientobackend.maintenance.domain.model.queries.GetAssetByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AssetQueryService {
    List<Asset> handle(GetAllAssetsQuery query);
    Optional<Asset> handle(GetAssetByIdQuery query);
}