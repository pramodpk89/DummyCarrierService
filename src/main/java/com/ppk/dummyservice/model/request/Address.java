package com.ppk.dummyservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Address {
    private String addressLine1;
    private String state;
    private Integer postalCode;
    private String countryCode;
}
