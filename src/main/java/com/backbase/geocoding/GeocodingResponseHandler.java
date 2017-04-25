package com.backbase.geocoding;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
public class GeocodingResponseHandler {

    private final DocumentBuilderFactory factory;

    public GeocodingResponseHandler() {
        factory = DocumentBuilderFactory.newInstance();
    }

    public GeocodingJsonResponse convertToJson(final String xmlResponse)
            throws ParserConfigurationException, IOException, SAXException {
        final InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlResponse));
        final Document document = factory.newDocumentBuilder().parse(is);

        final String formattedAddress = getElementByTagName(document, "formatted_address");
        final double latitude = Double.parseDouble(getElementByTagName(document, "lat"));
        final double longitude = Double.parseDouble(getElementByTagName(document, "lng"));

        return new GeocodingJsonResponse(formattedAddress, latitude, longitude);
    }

    private String getElementByTagName(final Document document, final String tagName) {
        return document.getElementsByTagName(tagName)
                .item(0).getChildNodes()
                .item(0).getNodeValue();

    }
}
