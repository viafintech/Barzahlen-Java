package de.barzahlen.api.online.requests.resulthandler;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parse error results
 */
public class ErrorResultParser {

    private String result;
    private String errorMessage;

    /**
     * Parse InputStream
     *
     * @param resultStream
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public void parse(final InputStream resultStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(resultStream);

        result = document.getElementsByTagName("result").item(0).getTextContent();
        errorMessage = document.getElementsByTagName("error-message").item(0).getTextContent();
    }

    /**
     * Returns Result
     *
     * @return
     */
    public String getResult() {
        return result;
    }

    /**
     * Returns error message
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
