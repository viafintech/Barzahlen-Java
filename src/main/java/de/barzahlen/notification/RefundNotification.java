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
 * @copyright   Copyright (c) 2012 Zerebro Internet GmbH (http://www.barzahlen.de/)
 * @author      Jesus Javier Nuno Garcia
 * @license     http://opensource.org/licenses/GPL-3.0  GNU General Public License, version 3 (GPL-3.0)
 */
package de.barzahlen.notification;

import de.barzahlen.Barzahlen;
import de.barzahlen.request.ServerRequest;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Class that implements the checking for the payment notification.
 * 
 * @author Jesus Javier Nuno Garcia
 */
public final class RefundNotification extends Notification {

    /**
     * Log file for the logger.
     */
    private static Logger refundNotificationLog = Logger.getLogger(RefundNotification.class.getName());

    /**
     * A print writer to show some output in the browser.
     */
    protected PrintWriter outStream;

    /**
     * The transaction state retrieved from the server request
     */
    protected String _state;

    /**
     * The refund transaction identifier retrieved from the server request
     */
    protected String _refundTransactionId;

    /**
     * The origin transaction identifier retrieved from the server request
     */
    protected String _originTransactionId;

    /**
     * The shop identifier retrieved from the server request
     */
    protected String _shopId;

    /**
     * The customer email retrieved from the server request
     */
    protected String _customerEmail;

    /**
     * The transaction amount retrieved from the server request
     */
    protected String _amount;

    /**
     * The currency retrieved from the server request
     */
    protected String _currency;

    /**
     * The origin order identifier from the server request
     */
    protected String _originOrderId;

    /**
     * The custom variable (0) retrieved from the server request
     */
    protected String _customVar0;

    /**
     * The custom variable (1) retrieved from the server request
     */
    protected String _customVar1;

    /**
     * The custom variable (2) retrieved from the server request
     */
    protected String _customVar2;

    /**
     * The hash retrieved from the server request
     */
    protected String _hash;

    /**
     * Constructor with parameters
     * 
     * @param _request
     *            The request received from the server
     * @param _response
     *            The response associated with the server
     * @throws IOException
     */
    public RefundNotification(HttpServletRequest _request, HttpServletResponse _response) throws IOException {
        super(_request, _response);
        refundNotificationLog.addAppender(this.logAppender.getConsoleAppender());
        refundNotificationLog.addAppender(this.logAppender.getFileAppender());
        this.outStream = this.response.getWriter();
        this._state = this.request.getParameter(STATE);
        this._refundTransactionId = this.request.getParameter(REFUND_TRANSACTION_ID);
        this._originTransactionId = this.request.getParameter(ORIGIN_TRANSACTION_ID);
        this._shopId = this.request.getParameter(SHOP_ID);
        this._customerEmail = this.request.getParameter(CUSTOMER_EMAIL);
        this._amount = this.request.getParameter(AMOUNT);
        this._currency = this.request.getParameter(CURRENCY);
        this._originOrderId = this.request.getParameter(ORIGIN_ORDER_ID);
        this._customVar0 = this.request.getParameter(CUSTOM_VAR_0);
        this._customVar1 = this.request.getParameter(CUSTOM_VAR_1);
        this._customVar2 = this.request.getParameter(CUSTOM_VAR_2);
        this._hash = this.request.getParameter(HASH);
    }

    @Override
    public boolean checkNotification(HashMap<String, String> _parameters) throws Exception {
        String message = this._state + ";" + this._refundTransactionId + ";" + this._originTransactionId + ";" + this._shopId + ";"
                + this._customerEmail + ";" + this._amount + ";" + this._currency + ";" + this._originOrderId + ";" + this._customVar0
                + ";" + this._customVar1 + ";" + this._customVar2 + ";" + this.notificationKey;

        this.response.setStatus(HttpServletResponse.SC_OK);

        // Check if everything is ok
        if (this.shopId.equals(this._shopId)) {

            if (this._state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_COMPLETED)
                    || this._state.equals(ServerRequest.BARZAHLEN_REFUND_ORDER_EXPIRED)) {

                if (_parameters.get("origin_transaction_id").equals(this._originTransactionId)) {

                    if (_parameters.get("origin_order_id").equals(this._originOrderId)) {

                        if (_parameters.get("refund_state").equals(ServerRequest.BARZAHLEN_REFUND_ORDER_PENDING)) {

                            if (_parameters.get("customer_email").equals(this._customerEmail)) {

                                if (_parameters.get("currency").equals(this._currency)) {
                                    String am = _parameters.get("amount");
                                    DecimalFormat df = new DecimalFormat("#.00");
                                    am = df.format(Double.valueOf(am)).replace(',', '.');
                                    String _am = df.format(Double.valueOf(this._amount)).replace(',', '.');

                                    if (am.equals(_am)) {

                                        if (Barzahlen.calculateHash(message).equals(this._hash)) {
                                            BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.SUCCESS;
                                            return true;
                                        }

                                        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                                            refundNotificationLog.debug("Data received in callback not correct: Hash not correct.");
                                        }

                                        this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                        BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.HASH_ERROR;
                                        this.outStream.println("Data received in callback not correct: Hash not correct.");
                                        throw new Exception("Data received in callback not correct: Hash not correct.");
                                    }

                                    if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                                        refundNotificationLog.debug("Data received in callback not correct: Amount not correct.");
                                    }

                                    this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.AMOUNT_ERROR;
                                    this.outStream.println("Data received in callback not correct: Amount not correct.");
                                    throw new Exception("Data received in callback not correct: Amount not correct.");

                                }

                                if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                                    refundNotificationLog.debug("Data received in callback not correct: Currency not correct.");
                                }

                                this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.CURRENCY_ERROR;
                                this.outStream.println("Data received in callback not correct: Currency not correct.");
                                throw new Exception("Data received in callback not correct: Currency not correct.");

                            }

                            if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                                refundNotificationLog.debug("Data received in callback not correct: Customer email not correct.");
                            }

                            this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.CUSTOMER_EMAIL_ERROR;
                            this.outStream.println("Data received in callback not correct: Customer email not correct.");
                            throw new Exception("Data received in callback not correct: Customer email not correct.");

                        }

                        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                            refundNotificationLog.debug("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
                        }

                        this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.BARZAHLEN_TRANSACTION_STATE_ERROR;
                        this.outStream.println("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");
                        throw new Exception("Database doesn't have the barzahlen transaction state set to \"refund_pending\".");

                    }

                    if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                        refundNotificationLog.debug("Data received in callback not correct: Origin order ID not correct.");
                    }

                    this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.BARZAHLEN_ORIGIN_ORDER_ID_ERROR;
                    this.outStream.println("Data received in callback not correct: Origin order ID not correct.");
                    throw new Exception("Data received in callback not correct: Origin order ID not correct.");

                }

                if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                    refundNotificationLog.debug("Data received in callback not correct: Origin transaction ID not correct.");
                }

                this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR;
                this.outStream.println("Data received in callback not correct: Origin transaction ID not correct.");
                throw new Exception("Data received in callback not correct: Origin transaction ID not correct.");

            }

            if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
                refundNotificationLog
                        .debug("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
            }

            this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.TRANSACTION_STATE_ERROR;
            this.outStream
                    .println("Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");
            throw new Exception(
                    "Data received in callback not correct: The transaction state is neither \"refund_completed\" nor \"refund_expired\".");

        }

        if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
            refundNotificationLog.debug("Data received is not correct (shop id is incorrect).");
        }

        this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        BARZAHLEN_NOTIFICATION_ERROR_CODE = NotificationErrorCode.SHOP_ID_ERROR;
        this.outStream.println("Data received is not correct (shop id is incorrect).");
        throw new Exception("Data received is not correct (shop id is incorrect).");

    }
}
