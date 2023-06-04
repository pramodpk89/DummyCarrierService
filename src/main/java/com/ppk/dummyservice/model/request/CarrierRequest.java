package com.ppk.dummyservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierRequest {
    private String labelResponseOptions;
    private RequestedShipment requestedShipment;
}
