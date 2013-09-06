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
import de.barzahlen.request.xml.CancelXMLInfo;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Implements the cancel webservice
 *
 * @author Jesus Javier Nuno Garcia
 */
public final class CancelRequest extends ServerRequest {

	/**
	 * Log file for the logger.
	 */
	private static final Logger requestLog = Logger.getLogger(CancelRequest.class.getName());

	/**
	 * The xml info retrieved from the server response.
	 */
	public static CancelXMLInfo XML_INFO;

	static {
		XML_INFO = new CancelXMLInfo();
	}

	/**
	 * Default constructor.
	 */
	public CancelRequest() {
		super();
		requestLog.addAppender(this.logAppender.getConsoleAppender());
		requestLog.addAppender(this.logAppender.getFileAppender());
	}

	/**
	 * Constructor with parameters for the "cancel" request
	 *
	 * @param _sandbox         True if wanted sandbox mode to be enabled
	 * @param _shopId          The shop identifier
	 * @param _paymentKey      The payment key
	 * @param _notificationKey The notification key
	 */
	public CancelRequest(boolean _sandbox, String _shopId, String _paymentKey, String _notificationKey) {
		super(_sandbox, _shopId, _paymentKey, _notificationKey);
		requestLog.addAppender(this.logAppender.getConsoleAppender());
		requestLog.addAppender(this.logAppender.getFileAppender());
	}

	/**
	 * Executes the cancel for a specific order.
	 *
	 * @param _parameters The parameters for the request. There are necessary
	 *                    "order_id", "transaction_id", "transaction_state" and
	 *                    "language" (ISO 639-1).
	 * @throws Exception
	 */
	public boolean cancel(HashMap<String, String> _parameters) throws Exception {
		return executeServerRequest(Barzahlen.BARZAHLEN_CANCEL_URL, assembleParameters(_parameters));
	}

	@Override
	protected String[] getParametersTemplate() {
		String parametersTemplate[] = new String[4];
		parametersTemplate[0] = "shop_id";
		parametersTemplate[1] = "transaction_id";
		parametersTemplate[2] = "language";
		parametersTemplate[3] = "payment_key";

		return parametersTemplate;
	}

	@Override
	protected boolean executeServerRequest(String _targetURL, String _urlParameters) throws Exception {
		BarzahlenApiRequest request = new BarzahlenApiRequest(_targetURL, CancelRequest.XML_INFO);
		boolean successful = request.doRequest(_urlParameters);

		if (Barzahlen.BARZAHLEN_DEBUGGING_MODE) {
			requestLog.debug("Response code: " + request.getResponseCode() + ". Response message: " + request.getResponseMessage()
					+ ". Parameters: " + ServerRequest.formatReadableParameters(_urlParameters));
			requestLog.debug(request.getResult());
			requestLog.debug(CancelRequest.XML_INFO.getTransactionId());
			requestLog.debug(String.valueOf(CancelRequest.XML_INFO.getResult()));
			requestLog.debug(CancelRequest.XML_INFO.getHash());
		}

		if (!successful) {
			return errorAction(_targetURL, _urlParameters, RequestErrorCode.XML_ERROR, requestLog,
					"Cancel request failed - retry", "Error received from the server. Response code: " + request.getResponseCode()
					+ ". Response message: " + request.getResponseMessage());
		} else if (!CancelRequest.XML_INFO.checkParameters()) {
			return errorAction(_targetURL, _urlParameters, RequestErrorCode.PARAMETERS_ERROR, requestLog,
					"Cancel request failed - retry", "There are errors with parameters received from the server.");
		} else if (!compareHashes()) {
			return errorAction(_targetURL, _urlParameters, RequestErrorCode.HASH_ERROR, requestLog,
					"Cancel request failed - retry", "Data received is not correct (hashes do not match)");
		} else {
			BARZAHLEN_REQUEST_ERROR_CODE = RequestErrorCode.SUCCESS;
			return true;
		}
	}

	@Override
	protected boolean compareHashes() {
		String data = CancelRequest.XML_INFO.getTransactionId() + ";" + CancelRequest.XML_INFO.getResult() + ";" + this.paymentKey;
		String hash = calculateHash(data);

		return hash.equals(CancelRequest.XML_INFO.getHash());
	}
}
