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
import de.barzahlen.response.UpdateResponse;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements the update order id webservice
 *
 * @author Jesus Javier Nuno Garcia
 */
public class UpdateRequest extends ServerRequest {

	/**
	 * Log file for the logger.
	 */
	private static final Logger updateRequestLog = LoggerFactory.getLogger(UpdateRequest.class);

	/**
	 * The xml info retrieved from the server response.
	 */
	private UpdateResponse updateResponse;
	private BarzahlenApiRequest request;
	private boolean successful;

	/**
	 * Constructor with parameters for the "update" request
	 */
	public UpdateRequest(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Executes the update order identifier for a specific order.
	 *
	 * @param parameters The parameters for the request. There are necessary "shop_id",
	 *                   "transaction_id" and "order_id"
	 * @throws Exception
	 */
	public UpdateResponse updateOrder(Map<String, String> parameters) throws Exception {
		executeServerRequest(Barzahlen.BARZAHLEN_UPDATE_URL, assembleParameters(parameters));

		return updateResponse;
	}

	@Override
	protected List<String> getParametersTemplate() {
		List<String> parametersTemplate = new ArrayList<String>(4);

		parametersTemplate.add("shop_id");
		parametersTemplate.add("transaction_id");
		parametersTemplate.add("order_id");
		parametersTemplate.add("payment_key");

		return parametersTemplate;
	}

	@Override
	protected String assembleParameters(Map<String, String> parameters) {
		String hashMessage = getShopId() + ";" + parameters.get("transaction_id") + ";" + parameters.get("order_id") + ";"
				+ getPaymentKey();

		String hash = HashTools.getHash(hashMessage);

		String params = "shop_id=" + getShopId() + "&transaction_id=" + parameters.get("transaction_id") + "&order_id="
				+ parameters.get("order_id") + "&hash=" + hash;

		return params;
	}

	@Override
	protected boolean executeServerRequest(String targetUrl, String urlParameters) throws Exception {
		request = new BarzahlenApiRequest(targetUrl, UpdateResponse.class);
		successful = request.doRequest(urlParameters);

		if (isSandboxMode()) {
			updateRequestLog.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage()
					+ ". Parameters: " + ServerRequest.formatReadableParameters(urlParameters));
			updateRequestLog.debug(request.getResult());

			if (isSuccessful()) {
				updateResponse = (UpdateResponse) request.getResponse();

				updateRequestLog.debug(updateResponse.getTransactionId());
				updateRequestLog.debug(String.valueOf(updateResponse.getResult()));
				updateRequestLog.debug(updateResponse.getHash());
			}
		}

		if (!isSuccessful()) {
			return errorAction(
					targetUrl,
					urlParameters,
					RequestErrorCode.XML_ERROR,
					"Update request failed - retry",
					"Error received from the server. Response code: " + request.getResponseCode() + ". Response message: "
							+ request.getResponseMessage());
		} else if (!compareHashes()) {
			return errorAction(targetUrl, urlParameters, RequestErrorCode.HASH_ERROR, "Update request failed - retry",
					"Data received is not correct (hashes do not match)");
		} else {
			BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;

			updateResponse = (UpdateResponse) request.getResponse();

			return true;
		}
	}

	@Override
	protected boolean compareHashes() {
		String data = updateResponse.getTransactionId() + ";" + updateResponse.getResult() + ";" + getPaymentKey();
		String hash = HashTools.getHash(data);

		return hash.equals(updateResponse.getHash());
	}

	public UpdateResponse getUpdateResponse() {
		return updateResponse;
	}

	public ErrorResponse getErrorResponse() {
		return (ErrorResponse) request.getResponse();
	}

	public boolean isSuccessful() {
		return successful;
	}
}
