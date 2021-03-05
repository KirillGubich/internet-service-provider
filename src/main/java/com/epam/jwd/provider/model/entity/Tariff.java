package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Tariff extends BaseEntity {
    private final String name;
    private final String description;
    private final Boolean isSpecialOffer;
    private final BigDecimal costPerDay;
    private final Double uploadSpeed;
    private final Double downloadSpeed;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getSpecialOffer() {
        return isSpecialOffer;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public Double getUploadSpeed() {
        return uploadSpeed;
    }

    public Double getDownloadSpeed() {
        return downloadSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(name, tariff.name) && Objects.equals(description, tariff.description)
                && Objects.equals(isSpecialOffer, tariff.isSpecialOffer) && Objects.equals(costPerDay, tariff.costPerDay)
                && Objects.equals(uploadSpeed, tariff.uploadSpeed) && Objects.equals(downloadSpeed, tariff.downloadSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, isSpecialOffer, costPerDay, uploadSpeed, downloadSpeed);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isSpecialOffer=" + isSpecialOffer +
                ", costPerDay=" + costPerDay +
                ", uploadSpeed=" + uploadSpeed +
                ", downloadSpeed=" + downloadSpeed +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String description;
        private Boolean isSpecialOffer;
        private BigDecimal costPerDay;
        private Double uploadSpeed;
        private Double downloadSpeed;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withSpecialOffer(Boolean isSpecialOffer) {
            this.isSpecialOffer = isSpecialOffer;
            return this;
        }

        public Builder withCostPerDay(BigDecimal cost) {
            this.costPerDay = cost;
            return this;
        }

        public Builder withUploadSpeed(Double uploadSpeed) {
            this.uploadSpeed = uploadSpeed;
            return this;
        }

        public Builder withDownloadSpeed(Double downloadSpeed) {
            this.downloadSpeed = downloadSpeed;
            return this;
        }

        public Tariff build() {
            return new Tariff(this);
        }
    }

    private Tariff(Builder builder) {
        super(builder.id);
        this.name = builder.name;
        this.description = builder.description;
        this.isSpecialOffer = builder.isSpecialOffer;
        this.costPerDay = builder.costPerDay;
        this.uploadSpeed = builder.uploadSpeed;
        this.downloadSpeed = builder.downloadSpeed;
    }
}
