package de.barzahlen.api.online.requests.resulthandler;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class ErrorResultParserTest {
    @Test
    public void testGetResultReturnsCorrectResult() throws IOException, SAXException, ParserConfigurationException {
        String result = "result";
        String errorMessage = "errorMessage";

        ErrorResultParser errorResultParser = new ErrorResultParser();
        errorResultParser.parse(buildResultStream(result, errorMessage));

        assertEquals(result, errorResultParser.getResult());
    }

    @Test
    public void testGetResultReturnsCorrectErrorMessage() throws IOException, SAXException,
            ParserConfigurationException {
        String result = "result";
        String errorMessage = "errorMessage";

        ErrorResultParser errorResultParser = new ErrorResultParser();
        errorResultParser.parse(buildResultStream(result, errorMessage));

        assertEquals(errorMessage, errorResultParser.getErrorMessage());
    }

    public InputStream buildResultStream(String result, String errorMessage) {
        InputStream resultStream;
        try {
            resultStream = new ByteArrayInputStream(
                    ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "  <response>\n" +
                            "  <result>" + result + "</result>\n" +
                            "  <error-message>" + errorMessage + "</error-message>\n" +
                            "  </response>").getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            resultStream = null;
            e.printStackTrace();
        }

        return resultStream;
    }
}
