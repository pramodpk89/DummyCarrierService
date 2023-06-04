# DummyCarrierService


##Sample Request:

````
curl --location 'http://localhost:8081/dummyCarrier/tracker' \
--header 'Content-Type: application/json' \
--data '{
"labelResponseOptions": "URL_ONLY",
"requestedShipment": {
"shipper": {
"contact": {
"personName": "SHIPPERNAME",
"phoneNumber": 1234567890,
"companyName": "ShipperCompanyName"
},
"address": {
"addressLine1": "addressLine1",
"state": "STATE",
"postalCode": 72601,
"countryCode": "US"
}
},
"recipients": [
{
"contact": {
"personName": "RECIPIENTNAME",
"phoneNumber": 1234567890,
"companyName": "RecipientCompanyName"
},
"address": {
"addressLine1": "RECIPIENTSTREETLINE1",
"state": "STATE",
"postalCode": 72601,
"countryCode": "US"
}
}
],
"shipDatestamp": "2021-06-30",
"serviceType": "SMART_POST",
"packagingType": "YOUR_PACKAGING",
"pickupType": "DROPOFF_AT_FEDEX_LOCATION"
},
"accountNumber": {
"value": "carrier1"
}
}'
````


## Sample Response:

````
{
"shipmentId": "DUMMY_SHIPMENT_ID",
"requestId": "DUMMY_REQUEST_ID",
"trackingId": "590d0bdfb4",
"trackingUrl": "https://dummy-carrier.com:8081/tracking/590d0bdfb4"
}
````


## To run this via docker:

1.Navigate to Project directory

2. run ``docker build -t dummycarrier:v1 .`` to build image

3.run ``docker run -p 8081:8081 dummycarrier:v1`` to spin up a new container.
