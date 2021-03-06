package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.BaseDto;

import java.util.Objects;

public class AddressDto implements BaseDto {
    private final String city;
    private final String address;

    public AddressDto(String city, String address) {
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
        AddressDto that = (AddressDto) o;
        return Objects.equals(city, that.city) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, address);
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
