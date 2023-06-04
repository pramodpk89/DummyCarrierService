package com.ppk.dummyservice;

import com.ppk.dummyservice.model.response.CarrierResponse;
import com.ppk.dummyservice.model.DummyCarrierResponse;
import com.ppk.dummyservice.model.ShipmentRequest;
import com.ppk.dummyservice.model.request.CarrierRequest;
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

    @PostMapping("tracker")
    public Mono<CarrierResponse> getTrackingDetails(@RequestBody CarrierRequest carrierRequest){
        Mono<CarrierResponse> response= dummyCarrierService.getTrackingDetailsForShipment(carrierRequest);
        response.subscribe(carrierResponse -> {
            logger.info("Sending response - Shipment ID: {}, Request ID: {}, Tracking ID: {}, Tracking URL: {}",
                    carrierResponse.getShipmentId(), carrierResponse.getRequestId(), carrierResponse.getTrackingId(), carrierResponse.getTrackingUrl());
        },
                throwable -> {
                    logger.error("An error occurred while processing the request", throwable);

                }
        );
        return response;
    }
}

