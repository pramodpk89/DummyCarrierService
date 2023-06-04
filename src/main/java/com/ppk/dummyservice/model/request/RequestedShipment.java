package com.ppk.dummyservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RequestedShipment {
    private Shipper shipper;
    private List<Recipient> recipients;
    private String shipDatestamp;
    private String serviceType;
    private String packagingType;
    private String pickupType;
}