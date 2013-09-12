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
package de.barzahlen.request;

import de.barzahlen.Barzahlen;
import de.barzahlen.BarzahlenApiRequest;
import de.barzahlen.configuration.Configuration;
import de.barzahlen.enums.RequestErrorCode;
import de.barzahlen.response.ErrorResponse;
import de.barzahlen.response.RefundResponse;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Implements the refund web service
 *
 * @author Jesus Javier Nuno Garcia
 */
public final class RefundRequest extends ServerRequest {

	/**
	 * Log file for the logger.
	 */
	private static final Logger refundRequestLog = LoggerFactory.getLogger(RefundRequest.class);

	/**
	 * The xml info retrieved from the server response.
	 */
	private RefundResponse refundResponse;
	private BarzahlenApiRequest request;
	private boolean successful = false;

	/**
	 * Constructor with parameters for the "refund" request
	 */
	public RefundRequest(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Executes the refund for a specific order.
	 *
	 * @param _parameters The parameters for the request. There are necessary
	 *                    "order_id", "amount","transaction_id", "currency" (ISO 4217)
	 *                    and "language" (ISO 639-1).
	 * @throws Exception
	 */
	public RefundResponse refund(HashMap<String, String> _parameters) throws Exception {
		executeServerRequest(Barzahlen.BARZAHLEN_REFUND_URL, assembleParameters(_parameters));

		return refundResponse;
	}

	@Override
	protected String[] getParametersTemplate() {
		String parametersTemplate[] = new String[6];
		parametersTemplate[0] = "shop_id";
		parametersTemplate[1] = "transaction_id";
		parametersTemplate[2] = "amount";
		parametersTemplate[3] = "currency";
		parametersTemplate[4] = "language";
		parametersTemplate[5] = "payment_key";

		return parametersTemplate;
	}

	@Override
	protected boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception {
		request = new BarzahlenApiRequest(_targetURL, RefundResponse.class);
		successful = request.doRequest(_urlParameters);

		if (isSandboxMode()) {
			refundRequestLog.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage() + ". Parameters: " + ServerRequest.formatReadableParameters(_urlParameters));
			refundRequestLog.debug(request.getResult());

			if (isSuccessful()) {
				refundResponse = (RefundResponse) request.getResponse();

				refundRequestLog.debug(refundResponse.getOriginTransactionId());
				refundRequestLog.debug(refundResponse.getRefundTransactionId());
				refundRequestLog.debug(String.valueOf(refundResponse.getResult()));
				refundRequestLog.debug(refundResponse.getHash());
			}
		}

		if (!isSuccessful()) {
			return errorAction(_targetURL, _urlParameters, RequestErrorCode.XML_ERROR, "Refund request failed - retry", "Error received from the server. Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage());
		} else if (!compareHashes()) {
			return errorAction(_targetURL, _urlParameters, RequestErrorCode.HASH_ERROR, "Refund request failed - retry", "Data received is not correct (hashes do not match)");
		} else {
			BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;

			refundResponse = (RefundResponse) request.getResponse();

			return true;
		}
	}

	@Override
	protected boolean compareHashes() {
		String data = refundResponse.getOriginTransactionId() + ";" + refundResponse.getRefundTransactionId() + ";"
				+ refundResponse.getResult() + ";" + getPaymentKey();
		String hash = HashTools.getHash(data);

		return hash.equals(refundResponse.getHash());
	}

	public RefundResponse getRefundResponse() {
		return refundResponse;
	}

	public ErrorResponse getErrorResponse() {
		return (ErrorResponse) request.getResponse();
	}

	public boolean isSuccessful() {
		return successful;
	}
}
