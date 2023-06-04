package com.ppk.dummyservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Contact {
    private String personName;
    private Long phoneNumber;
    private String companyName;
}