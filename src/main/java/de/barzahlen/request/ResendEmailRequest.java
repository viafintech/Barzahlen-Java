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
import de.barzahlen.response.ResendEmailResponse;
import de.barzahlen.tools.HashTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements the resend email webservice
 *
 * @author Jesus Javier Nuno Garcia
 */
public final class ResendEmailRequest extends ServerRequest {

	/**
	 * Log file for the logger.
	 */
	private static final Logger resendEmailRequestLog = LoggerFactory.getLogger(ResendEmailRequest.class);

	/**
	 * The xml info retrieved from the server response.
	 */
	private ResendEmailResponse resendEmailResponse;
	private BarzahlenApiRequest request;
	private boolean successful;

	/**
	 * Constructor with parameters for the "resend email" request
	 */
	public ResendEmailRequest(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Executes the resend email for a specific order.
	 *
	 * @param parameters The parameters for the request. There are necessary
	 *                   "order_id", "transaction_id", "transaction_state" and
	 *                   "language" (ISO 639-1).
	 * @throws Exception
	 */
	public ResendEmailResponse resendEmail(Map<String, String> parameters) throws Exception {
		executeServerRequest(Barzahlen.BARZAHLEN_RESEND_EMAIL_URL, assembleParameters(parameters));

		return resendEmailResponse;
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
		request = new BarzahlenApiRequest(targetUrl, ResendEmailResponse.class);
		successful = request.doRequest(urlParameters);

		if (isSandboxMode()) {
			resendEmailRequestLog.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage()
					+ ". Parameters: " + ServerRequest.formatReadableParameters(urlParameters));
			resendEmailRequestLog.debug(request.getResult());

			if (isSuccessful()) {
				resendEmailResponse = (ResendEmailResponse) request.getResponse();

				resendEmailRequestLog.debug(resendEmailResponse.getTransactionId());
				resendEmailRequestLog.debug(String.valueOf(resendEmailResponse.getResult()));
				resendEmailRequestLog.debug(resendEmailResponse.getHash());
			}
		}

		if (!isSuccessful()) {
			return errorAction(targetUrl, urlParameters, RequestErrorCode.XML_ERROR, "Resend email request failed - retry", "Error received from the server. Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage());
		} else if (!compareHashes()) {
			return errorAction(targetUrl, urlParameters, RequestErrorCode.HASH_ERROR, "Resend email request failed - retry", "Data received is not correct (hashes do not match)");
		} else {
			BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;

			resendEmailResponse = (ResendEmailResponse) request.getResponse();

			return true;
		}
	}

	@Override
	protected boolean compareHashes() {
		String data = resendEmailResponse.getTransactionId() + ";" + resendEmailResponse.getResult() + ";" + getPaymentKey();
		String hash = HashTools.getHash(data);

		return hash.equals(resendEmailResponse.getHash());
	}

	public ResendEmailResponse getResendEmailResponse() {
		return resendEmailResponse;
	}

	public ErrorResponse getErrorResponse() {
		return (ErrorResponse) request.getResponse();
	}

	public boolean isSuccessful() {
		return successful;
	}
}
