package com.weatheronline.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.weatheronline.Config;
import com.weatheronline.interfaces.IServiceListener;
import com.weatheronline.util.Utils;

public class Service implements Runnable {

	private HttpURLConnection _connection;
	private ServiceAction _action;
	private ArrayList<IServiceListener> _listener;
	private boolean _connecting;
	private Thread _thread;
	private String _actionURI;
	private Map<String, String> _params;
	private boolean _includeHost;
	private boolean _isGet;
	private Service _service;
	private boolean _isBitmap;
	private boolean _isPostDirect;
	private HttpClient httpclient;

	public void getProductList() {
		_action = ServiceAction.ActionGetProductList;
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceType", "kindle-fire");
		request("/v1/shop/purchase", params, true, false);
	}
	String token;

	

	
	
	public Service() {
		this(null);
	}

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			for (IServiceListener listener : _listener) {
				if (listener != null)
					listener.onCompleted(_service, (ServiceResponse) msg.obj);
			}

		}
	};

	public Service(IServiceListener... listeners) {
		_action = ServiceAction.ActionNone;
		_listener = new ArrayList<IServiceListener>();
		if (listeners != null)
			for (IServiceListener listener : listeners) {
				_listener.add(listener);
			}
		_connecting = false;
		_includeHost = true;
		_service = this;
		_isBitmap = false;
	}

	public void addListener(IServiceListener listener) {
		_listener.add(listener);
	}

	public boolean isConnecting() {
		return _connecting;
	}

	private void cleanUp() {
		if (_connection != null) {
			try {
				_connection.disconnect();
			} catch (Exception ex) {
				// do nothing
			}
			_connection = null;
		}
		_action = ServiceAction.ActionNone;
		_connecting = false;
	}

	public void stop() {
		// cleanUp();
		if (httpclient != null)
			httpclient.getConnectionManager().shutdown();
		_action = ServiceAction.ActionNone;
		_connecting = false;
	}

	private boolean request(String uri, Map<String, String> params,
			boolean includeHost, boolean isGet) {
		_isGet = isGet;
		request(uri, params, includeHost);
		return true;
	}

	private boolean request(String uri, Map<String, String> params,
			boolean includeHost) {
		if (_connecting)
			return false;
		_connecting = true;
		_actionURI = uri;
		_params = params;
		_includeHost = includeHost;
		_thread = new Thread(this);
		_thread.start();
		return true;
	}

	private String getParamsString(Map<String, String> params) {
		if (params == null)
			return null;
		String ret = "";
		for (String key : params.keySet()) {
			String value = params.get(key);
			ret += key + "=" + URLEncoder.encode(value) + "&";
		}
		return ret;
	}

	private void processError(ResultCode errorCode) {
		// if (_listener == null || _action == ServiceAction.ActionNone
		// || !_connecting)
		// return;
		Message msg = handler.obtainMessage(0, new ServiceResponse(_action,
				null, errorCode));
		handler.sendMessage(msg);
	}

	private void dispatchResult(String result) {
		if (_listener == null || _action == ServiceAction.ActionNone
				|| !_connecting)
			return;

		ServiceAction act = _action;
		Object resObj = null;
		ServiceResponse response = null;
		DataParser parser = new DataParser();
		// Log.v("Response", "1");
		// Log.v("Response", result);
		boolean isSuccess = false;
		if(act == ServiceAction.ActionGetTwitterTimeline){
			isSuccess = parser.parse(result, false);
		}else{
			isSuccess = parser.parse(result, true);	
		}
		
		if (isSuccess) {
			switch (act) {
			// TODO
			case ActionNone:
				break;

			}
		}
		if (resObj == null)
			response = new ServiceResponse(act, null, ResultCode.Failed);
		else
			response = new ServiceResponse(act, resObj);
		stop();
		Message msg = handler.obtainMessage(0, response);
		handler.sendMessage(msg);
	}

	private void dispatchResult(Bitmap result) {
		if (_listener == null || _action == ServiceAction.ActionNone
				|| !_connecting)
			return;
		ServiceAction act = _action;
		ServiceResponse response = null;
		if (result == null)
			response = new ServiceResponse(act, null, ResultCode.Failed);
		else
			response = new ServiceResponse(act, result);
		stop();
		Message msg = handler.obtainMessage(0, response);
		handler.sendMessage(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Log.d(_action.toString(), "=========== run ===========");
		httpclient = new DefaultHttpClient();
		// AndroidHttpClient httpclient = AndroidHttpClient
		// .newInstance(_actionURI);
		HttpParams httpParameters = httpclient.getParams();
		// HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 60000);
		HttpConnectionParams.setTcpNoDelay(httpParameters, true);
		try {
			String urlString = _actionURI;
			HttpUriRequest request = null;
			if (_isPostDirect) {
				String data = getParamsString(_params);
				if (_includeHost)
					urlString = Config.API_HOST + urlString;
				urlString = urlString + "?" + data;
				request = (_isGet) ? new HttpGet(urlString) : new HttpPost(
						urlString);

			} else {
				if (_includeHost)
					urlString = Config.API_HOST + urlString;
				request = (_isGet) ? new HttpGet(urlString) : new HttpPost(
						urlString);

			}

			// if user already login ==> has token ==> add token to header
			token = "";
			if (token != null ){
//				request.addHeader("Authorization", ""+token);
				request.setHeader("Content-Type", "x-zip");
				request.setHeader("Authorization", "OAuth " + token);
			}
			String data = getParamsString(_params);
			if (data != null)
				if (!data.equals("")) {
					if (_isGet)
						urlString = urlString + "?" + data;
					else {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for (String key : _params.keySet()) {
							params.add(new BasicNameValuePair(key, _params
									.get(key)));
						}
						UrlEncodedFormEntity entity = null;
						try {
							entity = new UrlEncodedFormEntity(params,
									HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
						}

						((HttpPost) request).setEntity(entity);
					}
				}
			Log.d(_action.toString(), urlString);
			// Set default headers
			HttpResponse response = httpclient.execute(request);

			/*
			 * InputStream in1 = new BufferedInputStream(response.getEntity()
			 * .getContent()); Log.v("Hehe", "2"); String temp1 =
			 * CommonUtil.convertStreamToString(in1);
			 * 
			 * Log.v("Hehe", "3"); Log.v("Hehe", temp1);
			 */

			InputStream in = null;

			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				Header[] header = response.getHeaders("Content-Encoding");
				if (header != null && header.length != 0) {
					for (Header h : header) {
						if (h.getName().trim().equalsIgnoreCase("gzip"))
							in = new GZIPInputStream(response.getEntity()
									.getContent());
					}
				}

				if (in == null)
					in = new BufferedInputStream(response.getEntity()
							.getContent());

				if (_isBitmap) {
					Bitmap bm = BitmapFactory.decodeStream(in);
					dispatchResult(bm);
				} else {
					String temp = Utils.convertStreamToString(in);// text.toString();
					Log.d(_action.toString(), temp);
					// Log.v("Service", "temp = " + temp);
					// System.out.print(temp);
					in.close();
					dispatchResult(temp);
				}

			} else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND)
				processError(ResultCode.Failed);
			else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_SERVER_ERROR)
				processError(ResultCode.ServerError);
			else
				processError(ResultCode.NetworkError);

		} catch (Exception e) {
			e.printStackTrace();
			processError(ResultCode.NetworkError);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public int getConnectionTimeout() {
		return _connection.getConnectTimeout();
	}


}