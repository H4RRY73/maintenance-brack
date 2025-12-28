package com.platform.mantenimientobackend.maintenance.domain.model.aggregates;

import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.AssetStatus;
import com.platform.mantenimientobackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Asset extends AuditableAbstractAggregateRoot<Asset> {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String serialNumber;

    private String model;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    public Asset(String name, String serialNumber, String model) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.model = model;
        this.status = AssetStatus.OPERATIONAL;
    }

    public void updateStatus(AssetStatus newStatus) {
        this.status = newStatus;
    }

    public void updateInformation(String name, String model) {
        this.name = name;
        this.model = model;
    }
}
