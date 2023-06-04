package com.ppk.dummyservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Recipient {
    private Contact contact;
    private Address address;
}
