package com.ppk.dummyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppk.dummyservice.model.DummyCarrierResponse;
import com.ppk.dummyservice.model.ShipmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service dummyCarrierService;

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dummyCarrier/ping"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("yes, yes I am there!"));
    }

    @Test
    public void testGetTrackingURL() throws Exception {
        String shipmentId = "S123";
        String requestId = "R123";
        String trackingId = "T123";
        String trackingUrl = "https://dummy-carrier.com/tracking/T123";
        DummyCarrierResponse dummyCarrierResponse = new DummyCarrierResponse(shipmentId, requestId, trackingId, trackingUrl);

        when(dummyCarrierService.getTrackingDetails(any(String.class), any(String.class)))
                .thenReturn(Mono.just(dummyCarrierResponse));

        ShipmentRequest shipmentRequest = new ShipmentRequest();
        shipmentRequest.setShipmentId(shipmentId);
        shipmentRequest.setReqId(requestId);

        mockMvc.perform(MockMvcRequestBuilders.post("/dummyCarrier/tracker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shipmentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
