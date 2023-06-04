package com.ppk.dummyservice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppk.dummyservice.model.request.CarrierRequest;
import com.ppk.dummyservice.model.response.CarrierResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class ControllerTest {

    @Mock
    private Service dummyCarrierService;

    @InjectMocks
    private Controller controller;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void testPing() {
        webTestClient.get().uri("/dummyCarrier/ping")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("yes, yes I am here!");
    }

    @SneakyThrows
    @Test
    public void testGetTrackingDetails() {
        String json = "{\n" +
                "\t\"labelResponseOptions\": \"URL_ONLY\",\n" +
                "\t\"requestedShipment\": {\n" +
                "\t\t\"shipper\": {\n" +
                "\t\t\t\"contact\": {\n" +
                "\t\t\t\t\"personName\": \"SHIPPERNAME\",\n" +
                "\t\t\t\t\"phoneNumber\": 1234567890,\n" +
                "\t\t\t\t\"companyName\": \"ShipperCompanyName\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"address\": {\n" +
                "\t\t\t\t\"addressLine1\": \"addressLine1\",\n" +
                "\t\t\t\t\"state\": \"STATE\",\n" +
                "\t\t\t\t\"postalCode\": 72601,\n" +
                "\t\t\t\t\"countryCode\": \"US\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"recipients\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"contact\": {\n" +
                "\t\t\t\t\t\"personName\": \"RECIPIENTNAME\",\n" +
                "\t\t\t\t\t\"phoneNumber\": 1234567890,\n" +
                "\t\t\t\t\t\"companyName\": \"RecipientCompanyName\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"address\": {\n" +
                "\t\t\t\t\t\"addressLine1\": \"RECIPIENTSTREETLINE1\",\n" +
                "\t\t\t\t\t\"state\": \"STATE\",\n" +
                "\t\t\t\t\t\"postalCode\": 72601,\n" +
                "\t\t\t\t\t\"countryCode\": \"US\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"shipDatestamp\": \"2021-06-30\",\n" +
                "\t\t\"serviceType\": \"SMART_POST\",\n" +
                "\t\t\"packagingType\": \"YOUR_PACKAGING\",\n" +
                "\t\t\"pickupType\": \"DROPOFF_AT_FEDEX_LOCATION\"\n" +
                "\t},\n" +
                "\t\"accountNumber\": {\n" +
                "\t\t\"value\": \"carrier1\"\n" +
                "\t}\n" +
                "}";  // Your JSON string here
        CarrierRequest carrierRequest = jsonToCarrierRequest(json);
        CarrierResponse carrierResponse = new CarrierResponse(); // Initialize your CarrierResponse object

        given(dummyCarrierService.getTrackingDetailsForShipment(carrierRequest)).willReturn(Mono.just(new CarrierResponse("DUMMY","DUMMY","123","www.example.com/123")));

        CarrierResponse actualResponse = webTestClient.post().uri("/dummyCarrier/tracker")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(carrierRequest), CarrierRequest.class)
                .exchange()
                .returnResult(CarrierResponse.class)
                .getResponseBody()
                .blockFirst();

        assertEquals("123", actualResponse.getTrackingId());
        assertEquals("www.example.com/123", actualResponse.getTrackingUrl());
    }



    private CarrierRequest jsonToCarrierRequest(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper.readValue(json, CarrierRequest.class);
    }

}
