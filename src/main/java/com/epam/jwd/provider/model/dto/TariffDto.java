package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.BaseDto;

import java.math.BigDecimal;
import java.util.Objects;

public class TariffDto implements BaseDto {
    private final String name;
    private final BigDecimal costPerDay;
    private final Double downloadSpeed;
    private final Double uploadSpeed;

    public TariffDto(String name, BigDecimal costPerDay, double downloadSpeed, double uploadSpeed) {
        this.name = name;
        this.costPerDay = costPerDay;
        this.downloadSpeed = downloadSpeed;
        this.uploadSpeed = uploadSpeed;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffDto tariffDto = (TariffDto) o;
        return Objects.equals(name, tariffDto.name) && Objects.equals(costPerDay, tariffDto.costPerDay) && Objects.equals(downloadSpeed, tariffDto.downloadSpeed) && Objects.equals(uploadSpeed, tariffDto.uploadSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, costPerDay, downloadSpeed, uploadSpeed);
    }

    @Override
    public String toString() {
        return "TariffDto{" +
                "name='" + name + '\'' +
                ", costPerDay=" + costPerDay +
                ", downloadSpeed=" + downloadSpeed +
                ", uploadSpeed=" + uploadSpeed +
                '}';
    }
}
