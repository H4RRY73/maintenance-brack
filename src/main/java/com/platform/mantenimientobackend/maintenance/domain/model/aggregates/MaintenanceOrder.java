package com.platform.mantenimientobackend.maintenance.domain.model.aggregates;

import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.platform.mantenimientobackend.maintenance.domain.model.valueobjects.MaintenanceType;
import com.platform.mantenimientobackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class MaintenanceOrder extends AuditableAbstractAggregateRoot<MaintenanceOrder> {

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @Enumerated(EnumType.STRING)
    private MaintenanceType type;

    @Temporal(TemporalType.DATE)
    private Date scheduledDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    public MaintenanceOrder(String description, MaintenanceType type, Date scheduledDate, Asset asset) {
        this.description = description;
        this.type = type;
        this.scheduledDate = scheduledDate;
        this.asset = asset;
        this.status = MaintenanceStatus.SCHEDULED;
    }

    public void startWork() {
        this.status = MaintenanceStatus.IN_PROGRESS;
    }

    public void completeWork() {
        this.status = MaintenanceStatus.COMPLETED;
    }
}
