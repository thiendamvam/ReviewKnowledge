package com.weatheronline;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.WindowManager;

public class WeatherOnlineApplication extends Application {

	private static WeatherOnlineApplication _instance;

	public WeatherOnlineApplication() {
		super();
		_instance = this;

	}

	public static WeatherOnlineApplication Instance() {
		return _instance;
	}

	public boolean isLandscape() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public int getDeviceWidth() {
		Display display = ((WindowManager) WeatherOnlineApplication.Instance()
				.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		return width;
	}

	public int getDeviceHeight() {
		Display display = ((WindowManager) WeatherOnlineApplication.Instance()
				.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int height = display.getHeight();
		return height;
	}

	public boolean isDefaultLanscapeDevice() {
		// Log.d("isDefaultLanscapeDevice "+isLandscape(),deviceWidth()+" is widh and height is "+
		// deviceHeight());
		if (getDeviceWidth() > getDeviceHeight())
			return true;
		else
			return false;
	}
}
