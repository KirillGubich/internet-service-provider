package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Subscription extends BaseEntity {
    private final Integer userId;
    private final Integer tariffId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal price;
    private final String tariffName;
    private final String tariffDescription;
    private final SubscriptionStatus status;
    private final Address address;

    public Integer getUserId() {
        return userId;
    }

    public Integer getTariffId() {
        return tariffId;
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

    public String getTariffName() {
        return tariffName;
    }

    public String getTariffDescription() {
        return tariffDescription;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(userId, that.userId) && Objects.equals(tariffId, that.tariffId)
                && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate)
                && Objects.equals(price, that.price) && Objects.equals(tariffName, that.tariffName)
                && Objects.equals(tariffDescription, that.tariffDescription) && status == that.status
                && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tariffId, startDate, endDate, price, tariffName, tariffDescription, status, address);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + getId() +
                "userId=" + userId +
                ", tariffId=" + tariffId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", tariffName='" + tariffName + '\'' +
                ", tariffDescription='" + tariffDescription + '\'' +
                ", status=" + status +
                ", address=" + address.toString() +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer userId;
        private Integer tariffId;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal price;
        private String tariffName;
        private String tariffDescription;
        private SubscriptionStatus status;
        private Address address;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder withTariffId(Integer tariffId) {
            this.tariffId = tariffId;
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

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }

    private Subscription(Builder builder) {
        super(builder.id);
        this.userId = builder.userId;
        this.tariffId = builder.tariffId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.price = builder.price;
        this.tariffName = builder.tariffName;
        this.tariffDescription = builder.tariffDescription;
        this.status = builder.status;
        this.address = builder.address;
    }
}
