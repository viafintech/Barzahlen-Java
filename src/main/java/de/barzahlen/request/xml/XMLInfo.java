/**
 * Barzahlen Payment Module SDK
 *
 * NOTICE OF LICENSE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 *
 * @copyright Copyright (c) 2012 Zerebro Internet GmbH (http://www.barzahlen.de/)
 * @author Jesus Javier Nuno Garcia
 * @license http://opensource.org/licenses/GPL-3.0  GNU General Public License, version 3 (GPL-3.0)
 */
package de.barzahlen.request.xml;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Parent class for all xml parsing classes.
 *
 * @author Jesus Javier Nuno Garcia
 */
public abstract class XMLInfo {

	private static final Logger logger = LoggerFactory.getLogger(XMLInfo.class);
	private static final int NUM_ESCAPING_ITERATIONS = 2;

	/**
	 * Parser for the xml data
	 */
	protected SAXParser saxParser;

	/**
	 * Shows whether the parsing was success or not.
	 */
	protected boolean successParsing;

	/**
	 * The number of params the class is supposed to receive.
	 */
	protected int paramsAmountExpected;

	/**
	 * How many parameters are received in the request.
	 */
	protected int paramsAmountReceived;

	/**
	 * Handler for normal responses
	 */
	protected DefaultHandler normalHandler;

	/**
	 * Handler for wrong responses
	 */
	protected DefaultHandler errorHandler;

	/**
	 * Default constructor
	 */
	public XMLInfo() {
		try {
			this.paramsAmountExpected = -1;
			this.saxParser = SAXParserFactory.newInstance().newSAXParser();
			this.successParsing = true;
			this.paramsAmountReceived = 0;
			initHandlers();
		} catch (ParserConfigurationException e) {
			this.successParsing = false;
		} catch (SAXException e) {
			this.successParsing = false;
		}
	}

	/**
	 * Reads, loads and prints xml content stored in a string
	 *
	 * @param content      The string containing the xml content
	 * @param responseCode The response code from the post request
	 * @return True if the response was successful. False otherwise.
	 * @throws IOException
	 * @throws SAXException
	 */
	public boolean readXMLFile(String content, int responseCode) throws SAXException, IOException {
		this.paramsAmountReceived = 0;

		if (responseCode == 200) {
			String escapedHtml = content.trim();

			// Escape multiple times to convert string like "&amp;uuml;" to "&uuml;" to a "ü"
			for (int i = 0; i < NUM_ESCAPING_ITERATIONS; i++) {
				escapedHtml = StringEscapeUtils.unescapeHtml(escapedHtml);
			}

			StringReader stringReader = new StringReader(escapedHtml);
			InputSource inputSource = new InputSource(stringReader);

			this.saxParser.parse(inputSource, this.normalHandler);
			return true;
		}

		this.saxParser.parse(new InputSource(new StringReader(StringEscapeUtils.unescapeHtml(content.trim()))), this.errorHandler);
		return false;
	}

	/**
	 * Checks the consistency of the parameters received against the parameters
	 * that are suppose to receive.
	 *
	 * @return True if the amount of parameters are enough. False otherwise.
	 */
	public boolean checkParameters() {
		if (this.successParsing) {
			if (this.paramsAmountExpected != this.paramsAmountReceived) {
				this.successParsing = false;
			}
		}

		this.paramsAmountReceived = 0;

		return this.successParsing;
	}

	/**
	 * Init the handler for successful responses and the handler for erroneous
	 * responses. They should parse the xml tags.
	 */
	protected abstract void initHandlers();
}
