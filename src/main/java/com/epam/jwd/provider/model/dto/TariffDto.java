package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.BaseDto;
import com.epam.jwd.provider.model.entity.Tariff;

import java.math.BigDecimal;
import java.util.Objects;

public class TariffDto implements BaseDto {
    private final String name;
    private final String description;
    private final Boolean isSpecialOffer;
    private final BigDecimal costPerDay;
    private final Double uploadSpeed;
    private final Double downloadSpeed;

    public String getName() {
        return name;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public Double getDownloadSpeed() {
        return downloadSpeed;
    }

    public Double getUploadSpeed() {
        return uploadSpeed;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getSpecialOffer() {
        return isSpecialOffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffDto tariffDto = (TariffDto) o;
        return Objects.equals(name, tariffDto.name)
                && Objects.equals(description, tariffDto.description)
                && Objects.equals(isSpecialOffer, tariffDto.isSpecialOffer)
                && Objects.equals(costPerDay, tariffDto.costPerDay)
                && Objects.equals(uploadSpeed, tariffDto.uploadSpeed)
                && Objects.equals(downloadSpeed, tariffDto.downloadSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, isSpecialOffer, costPerDay, uploadSpeed, downloadSpeed);
    }

    @Override
    public String toString() {
        return "TariffDto{" +
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
        private String name;
        private String description;
        private Boolean isSpecialOffer;
        private BigDecimal costPerDay;
        private Double uploadSpeed;
        private Double downloadSpeed;

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

        public TariffDto build() {
            return new TariffDto(this);
        }
    }

    public TariffDto(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.isSpecialOffer = builder.isSpecialOffer;
        this.costPerDay = builder.costPerDay;
        this.uploadSpeed = builder.uploadSpeed;
        this.downloadSpeed = builder.downloadSpeed;
    }
}
