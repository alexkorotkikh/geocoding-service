package com.backbase.geocoding;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.query;

@Component
public class Router extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/geocoding")
                .consumes("application/json")
                .produces("application/json")

                .get().description("Geocode provided address").outType(GeocodingJsonResponse.class)
                    .param().name("address").type(query).dataType("string").endParam()
                    .responseMessage().code(200).endResponseMessage()
                    .to("direct:geocoding");

        from("direct:geocoding")
                .to("http://maps.googleapis.com/maps/api/geocode/xml?bridgeEndpoint=true&address=${address}")
                .convertBodyTo(String.class, "UTF-8")
                .to("bean:geocodingResponseHandler?method=convertToJson(${body})");
    }

}
