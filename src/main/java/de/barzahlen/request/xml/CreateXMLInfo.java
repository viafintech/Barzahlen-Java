/**
 * Barzahlen Payment Module SDK NOTICE OF LICENSE This program is free software:
 * you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; version 3 of the
 * License This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/
 *
 * @copyright Copyright (c) 2012 Zerebro Internet GmbH
 *            (http://www.barzahlen.de/)
 * @author Jesus Javier Nuno Garcia
 * @license http://opensource.org/licenses/GPL-3.0 GNU General Public License,
 *          version 3 (GPL-3.0)
 */
package de.barzahlen.request.xml;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses the XML data retrieved from the server for the "create" request
 *
 * @author Jesus Javier Nuno Garcia
 */
public class CreateXMLInfo extends XMLInfo {

	/**
	 * The transaction id
	 */
	protected String transactionId;

	/**
	 * The payment slip link
	 */
	protected String paymentSlipLink;

	/**
	 * Information about the expiration
	 */
	protected String expirationNotice;

	/**
	 * Information text
	 */
	protected String infotext1;

	/**
	 * Information text
	 */
	protected String infotext2;

	/**
	 * Result value
	 */
	protected String result;

	/**
	 * Information hash
	 */
	protected String hash;

	/**
	 * The error message in case of bad request
	 */
	protected String errorMessage;

	/**
	 * Default constructor
	 */
	public CreateXMLInfo() {
		super();
		this.paramsAmountExpected = 7;
		this.transactionId = "";
		this.paymentSlipLink = "";
		this.expirationNotice = "";
		this.infotext1 = "";
		this.infotext2 = "";
		this.result = "0";
		this.hash = "";
		this.errorMessage = "";
	}

	@Override
	protected void initHandlers() {
		this.normalHandler = new DefaultHandler() {

			String currentStartingTag = "";

			String currentEndingTag = "";

			String currentLine = "";

			boolean setCurrentLine = false;

			boolean tid = false;

			boolean psl = false;

			boolean exn = false;

			boolean in1 = false;

			boolean in2 = false;

			boolean res = false;

			boolean hsh = false;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

				if (qName.equalsIgnoreCase("transaction-id")) {
					this.currentStartingTag = "<" + qName + ">";
					this.tid = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("payment-slip-link")) {
					this.currentStartingTag = "<" + qName + ">";
					this.psl = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("expiration-notice")) {
					this.currentStartingTag = "<" + qName + ">";
					this.exn = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("infotext-1")) {
					this.currentStartingTag = "<" + qName + ">";
					this.in1 = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("infotext-2")) {
					this.currentStartingTag = "<" + qName + ">";
					this.in2 = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("result")) {
					this.currentStartingTag = "<" + qName + ">";
					this.res = true;
					this.setCurrentLine = true;
				}

				if (qName.equalsIgnoreCase("hash")) {
					this.currentStartingTag = "<" + qName + ">";
					this.hsh = true;
					this.setCurrentLine = true;
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				this.currentEndingTag = "<" + qName + ">";

				if (this.currentStartingTag.equals(this.currentEndingTag)) {
					if (this.tid) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.transactionId = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
								currentEndingTagPosition));
						this.tid = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.psl) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.paymentSlipLink = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
								currentEndingTagPosition));
						this.psl = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.exn) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.expirationNotice = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
								currentEndingTagPosition));
						this.exn = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.in1) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.infotext1 = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
								currentEndingTagPosition));
						this.in1 = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.in2) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.infotext2 = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
								currentEndingTagPosition));
						this.in2 = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.res) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.result = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0, currentEndingTagPosition));
						this.res = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}

					if (this.hsh) {
						int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
						CreateXMLInfo.this.hash = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0, currentEndingTagPosition));
						this.hsh = false;
						CreateXMLInfo.this.paramsAmountReceived++;
					}
				}
			}

			@Override
			public void characters(char ch[], int start, int length) throws SAXException {

				if (this.setCurrentLine) {
					if (this.tid) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.psl) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.exn) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.in1) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.in2) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.res) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}

					if (this.hsh) {
						this.currentLine = new String(ch);
						this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
								+ this.currentStartingTag.length());
						this.setCurrentLine = false;
					}
				}
			}
		};

		this.errorHandler = new DefaultHandler() {

			boolean res = false;

			boolean err = false;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

				if (qName.equalsIgnoreCase("result")) {
					this.res = true;
				}

				if (qName.equalsIgnoreCase("error-message")) {
					this.err = true;
				}
			}

			@Override
			public void characters(char ch[], int start, int length) throws SAXException {

				if (this.res) {
					CreateXMLInfo.this.result = new String(ch, start, length);
					this.res = false;
				}

				if (this.err) {
					CreateXMLInfo.this.errorMessage = new String(ch, start, length);
					this.err = false;
				}
			}
		};
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return this.transactionId;
	}

	/**
	 * @return the paymentSlipLink
	 */
	public String getPaymentSlipLink() {
		return this.paymentSlipLink;
	}

	/**
	 * @return the expirationNotice
	 */
	public String getExpirationNotice() {
		return this.expirationNotice;
	}

	/**
	 * @return the infotext1
	 */
	public String getInfotext1() {
		return this.infotext1;
	}

	/**
	 * @return the infotext2
	 */
	public String getInfotext2() {
		return this.infotext2;
	}

	/**
	 * @return the result
	 */
	public int getResult() {
		return Integer.valueOf(this.result);
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return this.hash;
	}

	/**
	 * @return the error
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @return the paramsAmountReceived
	 */
	public int getParamsAmountReceived() {
		return this.paramsAmountReceived;
	}
}
