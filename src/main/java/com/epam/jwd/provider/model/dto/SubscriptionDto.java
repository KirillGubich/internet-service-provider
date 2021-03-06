package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.entity.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class SubscriptionDto {
    private final Integer id;
    private final String tariffName;
    private final String tariffDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal price;
    private final SubscriptionStatus status;
    private final AddressDto address;

    public Integer getId() {
        return id;
    }

    public String getTariffName() {
        return tariffName;
    }

    public String getTariffDescription() {
        return tariffDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public AddressDto getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionDto that = (SubscriptionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(tariffName, that.tariffName) && Objects.equals(tariffDescription, that.tariffDescription) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(price, that.price) && status == that.status && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tariffName, tariffDescription, startDate, endDate, price, status, address);
    }

    @Override
    public String toString() {
        return "SubscriptionDto{" +
                "id=" + id +
                ", tariffName='" + tariffName + '\'' +
                ", tariffDescription='" + tariffDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", status=" + status +
                ", address=" + address +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String tariffName;
        private String tariffDescription;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal price;
        private SubscriptionStatus status;
        private AddressDto address;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withStartDate(LocalDate date) {
            this.startDate = date;
            return this;
        }

        public Builder withEndDate(LocalDate date) {
            this.endDate = date;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withTariffName(String tariffName) {
            this.tariffName = tariffName;
            return this;
        }

        public Builder withTariffDescription(String tariffDescription) {
            this.tariffDescription = tariffDescription;
            return this;
        }

        public Builder withStatus(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public Builder withAddress(AddressDto address) {
            this.address = address;
            return this;
        }

        public SubscriptionDto build() {
            return new SubscriptionDto(this);
        }
    }

    private SubscriptionDto(Builder builder) {
        this.id = builder.id;
        this.tariffName = builder.tariffName;
        this.tariffDescription = builder.tariffDescription;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.price = builder.price;
        this.status = builder.status;
        this.address = builder.address;
    }
}
