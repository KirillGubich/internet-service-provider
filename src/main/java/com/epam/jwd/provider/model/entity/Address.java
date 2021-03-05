package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.util.Objects;

public class Address extends BaseEntity {
    private final String city;
    private final String address;

    public Address(Integer id, String city, String address) {
        super(id);
        this.city = city;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(city, address1.city) && Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, address);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + getId() + '\'' +
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
