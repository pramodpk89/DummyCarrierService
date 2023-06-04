package com.ppk.dummyservice;

import com.ppk.dummyservice.model.DummyCarrierResponse;
import com.ppk.dummyservice.model.request.CarrierRequest;
import com.ppk.dummyservice.model.response.CarrierResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/dummyCarrier")
public class Service {
    @Value("${server.port}")
    private int serverPort;
    private String generateTrackingId() {
        String trackingId = UUID.randomUUID().toString().replaceAll("-", "");
        trackingId = trackingId.substring(0, 10);
        return trackingId;
    }


    private String generateTrackingUrl(String trackingId) {
        return "https://dummy-carrier.com:" + serverPort + "/tracking/" + trackingId;
    }

    public Mono<CarrierResponse> getTrackingDetailsForShipment(CarrierRequest carrierRequest) {
        String trackingId=generateTrackingId();
        return Mono.just(new CarrierResponse("DUMMY_SHIPMENT_ID","DUMMY_REQUEST_ID",trackingId,generateTrackingUrl(trackingId)));
    }
}

