package com.ppk.dummyservice.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    private String labelResponseOptions;
    private RequestedShipment requestedShipment;
    private AccountNumber accountNumber;
}