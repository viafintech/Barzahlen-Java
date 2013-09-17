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
import de.barzahlen.response.CancelResponse;
import de.barzahlen.response.ErrorResponse;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements the cancel webservice
 *
 * @author Jesus Javier Nuno Garcia
 */
public final class CancelRequest extends ServerRequest {

	/**
	 * Log file for the logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CancelRequest.class);

	/**
	 * The xml info retrieved from the server response.
	 */
	private CancelResponse cancelResponse = null;

	private boolean successful = false;
	private BarzahlenApiRequest request = null;

	/**
	 * Constructor with parameters for the "cancel" request
	 */
	public CancelRequest(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Executes the cancel for a specific order.
	 *
	 * @param parameters The parameters for the request. There are necessary
	 *                    "order_id", "transaction_id", "transaction_state" and
	 *                    "language" (ISO 639-1).
	 * @throws Exception
	 */
	public CancelResponse cancel(Map<String, String> parameters) throws Exception {
		executeServerRequest(Barzahlen.BARZAHLEN_CANCEL_URL, assembleParameters(parameters));

		return cancelResponse;
	}

	@Override
	protected List<String> getParametersTemplate() {
		List<String> parametersTemplate = new ArrayList<String>(4);

		parametersTemplate.add("shop_id");
		parametersTemplate.add("transaction_id");
		parametersTemplate.add("language");
		parametersTemplate.add("payment_key");

		return parametersTemplate;
	}

	@Override
	protected boolean executeServerRequest(String targetUrl, String urlParameters) throws Exception {
		request = new BarzahlenApiRequest(targetUrl, CancelResponse.class);
		successful = request.doRequest(urlParameters);

		if (isSandboxMode()) {
			logger.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage()
					+ ". Parameters: " + ServerRequest.formatReadableParameters(urlParameters));
			logger.debug(request.getResult());

			if (isSuccessful()) {
				cancelResponse = (CancelResponse) request.getResponse();

				logger.debug(cancelResponse.getTransactionId());
				logger.debug(cancelResponse.getHash());
				logger.debug(String.valueOf(cancelResponse.getResult()));
			}
		}

		if (!isSuccessful()) {
			return errorAction(targetUrl, urlParameters, RequestErrorCode.XML_ERROR, "Cancel request failed - retry", "Error received from the server. Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage());
		} else if (!compareHashes()) {
			return errorAction(targetUrl, urlParameters, RequestErrorCode.HASH_ERROR, "Cancel request failed - retry", "Data received is not correct (hashes do not match)");
		} else {
			BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;

			cancelResponse = (CancelResponse) request.getResponse();

			return true;
		}
	}

	@Override
	protected boolean compareHashes() {
		String data = cancelResponse.getTransactionId() + ";" + cancelResponse.getResult() + ";" + getPaymentKey();
		String hash = HashTools.getHash(data);

		return hash.equals(cancelResponse.getHash());
	}

	public CancelResponse getCancelResponse() {
		return cancelResponse;
	}

	public ErrorResponse getErrorResponse() {
		return (ErrorResponse) request.getResponse();
	}

	public boolean isSuccessful() {
		return successful;
	}
}
