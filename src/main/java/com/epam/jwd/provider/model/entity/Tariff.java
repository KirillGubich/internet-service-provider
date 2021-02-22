package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Tariff extends BaseEntity {
    private final String name;
    private BigDecimal costPerDay;
    private Double uploadSpeed;
    private Double downloadSpeed;

    public Tariff(Integer id, String name, BigDecimal costPerDay, double uploadSpeed, double downloadSpeed) {
        super(id);
        this.name = name;
        this.costPerDay = costPerDay;
        this.uploadSpeed = uploadSpeed;
        this.downloadSpeed = downloadSpeed;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Double getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(Double uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public Double getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(Double downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(name, tariff.name) && Objects.equals(costPerDay, tariff.costPerDay) && Objects.equals(uploadSpeed, tariff.uploadSpeed) && Objects.equals(downloadSpeed, tariff.downloadSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, costPerDay, uploadSpeed, downloadSpeed);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", costPerDay=" + costPerDay +
                ", uploadSpeed=" + uploadSpeed +
                ", downloadSpeed=" + downloadSpeed +
                '}';
    }
}
