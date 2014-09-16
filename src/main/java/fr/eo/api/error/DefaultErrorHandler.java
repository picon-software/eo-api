/*
 * Copyright (C) 2014 Picon software
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package fr.eo.api.error;

import java.net.SocketTimeoutException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class DefaultErrorHandler implements ErrorHandler {

	private NetworkErrorHandler handler;

	public DefaultErrorHandler(NetworkErrorHandler handler) {
		this.handler = handler;
	}

	@Override
	public Throwable handleError(RetrofitError cause) {
		if(cause.isNetworkError()) {
			if(cause.getCause() instanceof SocketTimeoutException) {
				handler.onTimeOutError(cause);
			} else {
				handler.onNoInternetError(cause);
			}
		}
		return cause;
	}


}
