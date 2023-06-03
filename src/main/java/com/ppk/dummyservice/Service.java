package com.ppk.dummyservice;

import com.ppk.dummyservice.model.DummyCarrierResponse;
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

    public Mono<DummyCarrierResponse> getTrackingDetails(String shipmentId, String requestId) {
        String trackingId = generateTrackingId();
        String trackingUrl = generateTrackingUrl(trackingId);
        return Mono.just(new DummyCarrierResponse(shipmentId, requestId, trackingId, trackingUrl));
    }

}

