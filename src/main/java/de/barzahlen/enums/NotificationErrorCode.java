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
package de.barzahlen.enums;

/**
 * Enumeration for error codes in notifications.
 *
 * @author Jesus Javier Nuno Garcia
 */
public enum NotificationErrorCode {
	HASH_ERROR, SHOP_ID_ERROR, TRANSACTION_STATE_ERROR, BARZAHLEN_ORIGIN_TRANSACTION_ID_ERROR, BARZAHLEN_TRANSACTION_STATE_ERROR, BARZAHLEN_ORIGIN_ORDER_ID_ERROR, CUSTOMER_EMAIL_ERROR, CURRENCY_ERROR, AMOUNT_ERROR, SUCCESS
}
