package com.ppk.dummyservice;

import com.ppk.dummyservice.model.DummyCarrierResponse;
import com.ppk.dummyservice.model.ShipmentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dummyCarrier")
public class Controller {

    private final Service dummyCarrierService;
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(Service dummyCarrierService) {
        this.dummyCarrierService = dummyCarrierService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "yes, yes I am here!";
    }

    @PostMapping("/tracker")
    //test comment
    public Mono<DummyCarrierResponse> getTrackingURL(@RequestBody ShipmentRequest shipmentRequest) {
        String shipmentId = shipmentRequest.getShipmentId();
        String requestId = shipmentRequest.getReqId();

        logger.info("Received request - Shipment ID: {}, Request ID: {}", shipmentId, requestId);

        Mono<DummyCarrierResponse> response = dummyCarrierService.getTrackingDetails(shipmentId, requestId);

        response.subscribe(
                dummyCarrierResponse -> {
                    logger.info("Sending response - Shipment ID: {}, Request ID: {}, Tracking ID: {}, Tracking URL: {}",
                            shipmentId, requestId, dummyCarrierResponse.getTrackingId(), dummyCarrierResponse.getTrackingUrl());
                },
                throwable -> {
                    logger.error("An error occurred while processing the request", throwable);
                }
        );

        return response;
    }
}

