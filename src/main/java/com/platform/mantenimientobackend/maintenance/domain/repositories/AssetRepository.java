package com.platform.mantenimientobackend.maintenance.domain.repositories;

import com.platform.mantenimientobackend.maintenance.domain.model.aggregates.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findBySerialNumber(String serialNumber);
    boolean existsBySerialNumber(String serialNumber);
}
