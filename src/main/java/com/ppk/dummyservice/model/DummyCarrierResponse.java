package com.ppk.dummyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DummyCarrierResponse {
    private String shipmentId;
    private String requestId;
    private String trackingId;
    private String trackingUrl;

}