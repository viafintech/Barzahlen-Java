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
package barzahlen.request.xml;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses the XML data retrieved from the server for the "refund" request
 * 
 * @author Jesus Javier Nuno Garcia
 */
public class RefundXMLInfo extends XMLInfo {

    /**
     * The origin transaction id
     */
    protected String originTransactionId;

    /**
     * The refund transaction id
     */
    protected String refundTransactionId;

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
    public RefundXMLInfo() {
        super();
        this.paramsAmountExpected = 4;
        this.originTransactionId = new String("");
        this.refundTransactionId = new String("");
        this.result = new String("0");
        this.hash = new String("");
        this.errorMessage = new String("");
    }

    @Override
    protected void initHandlers() {

        this.normalHandler = new DefaultHandler() {

            String currentStartingTag = "";

            String currentEndingTag = "";

            String currentLine = "";

            boolean setCurrentLine = false;

            boolean otid = false;

            boolean rtid = false;

            boolean res = false;

            boolean hsh = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                if (qName.equalsIgnoreCase("origin-transaction-id")) {
                    this.currentStartingTag = "<" + qName + ">";
                    this.otid = true;
                    this.setCurrentLine = true;
                }

                if (qName.equalsIgnoreCase("refund-transaction-id")) {
                    this.currentStartingTag = "<" + qName + ">";
                    this.rtid = true;
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
                    if (this.otid) {
                        int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
                        RefundXMLInfo.this.originTransactionId = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
                                currentEndingTagPosition));
                        this.otid = false;
                        RefundXMLInfo.this.paramsAmountReceived++;
                    }

                    if (this.rtid) {
                        int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
                        RefundXMLInfo.this.refundTransactionId = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0,
                                currentEndingTagPosition));
                        this.rtid = false;
                        RefundXMLInfo.this.paramsAmountReceived++;
                    }

                    if (this.res) {
                        int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
                        RefundXMLInfo.this.result = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0, currentEndingTagPosition));
                        this.res = false;
                        RefundXMLInfo.this.paramsAmountReceived++;
                    }

                    if (this.hsh) {
                        int currentEndingTagPosition = this.currentLine.indexOf("</" + qName + ">");
                        RefundXMLInfo.this.hash = StringEscapeUtils.unescapeHtml(this.currentLine.substring(0, currentEndingTagPosition));
                        this.hsh = false;
                        RefundXMLInfo.this.paramsAmountReceived++;
                    }
                }
            }

            @Override
            public void characters(char ch[], int start, int length) throws SAXException {

                if (this.setCurrentLine) {
                    if (this.otid) {
                        this.currentLine = new String(ch);
                        this.currentLine = this.currentLine.substring(this.currentLine.indexOf(this.currentStartingTag)
                                + this.currentStartingTag.length());
                        this.setCurrentLine = false;
                    }

                    if (this.rtid) {
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
                    RefundXMLInfo.this.result = new String(ch, start, length);
                    this.res = false;
                }

                if (this.err) {
                    RefundXMLInfo.this.errorMessage = new String(ch, start, length);
                    this.err = false;
                }
            }
        };
    }

    /**
     * @return the originTransactionId
     */
    public String getOriginTransactionId() {
        return this.originTransactionId;
    }

    /**
     * @return the refundTransactionId
     */
    public String getRefundTransactionId() {
        return this.refundTransactionId;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return Integer.valueOf(this.result).intValue();
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
