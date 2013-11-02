package com.weatheronline.interfaces;

import com.weatheronline.service.Service;
import com.weatheronline.service.ServiceResponse;

public interface IServiceListener {
	/*
	 * Called when a request has been fisnished without canceled.
	 */
	public void onCompleted(Service service, ServiceResponse result);
}
