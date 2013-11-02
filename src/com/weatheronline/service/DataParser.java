package com.weatheronline.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.weatheronline.model.DateInfo;

public class DataParser {

	private JSONObject _root;
	private JSONArray jsonContentArray;
	private JSONObject jsonContent;
	private JSONObject jsonStory;
	private JSONObject imageJsonContent;
	private JSONArray imageJsonList;
	private JSONObject imageJsonItem;
	private JSONObject imageJsonRelatedContent;
	private JSONArray imageJsonRelatedList;
	private JSONObject imageJsonRelatedItem;
	private JSONObject jsonStories;
	private Context context;
	public static String issueIntro = "";
	private String issueString;
	private JSONObject videoJsonRelatedItem;
	private JSONArray videoJsonRelatedList;

	// private StoreIssueDataControler issueControler;
	public DataParser() {
		// issueController.resetStorySetData();
	}

	public boolean parse(String input, boolean isJson) {
		try {
			issueString = input;
			if (isJson)
				_root = new JSONObject(input);
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<DateInfo> parseAdsJson() {
		// TODO Auto-generated method stub
		ArrayList<DateInfo> datas = new ArrayList<DateInfo>();
		try {
			JSONArray array = _root.optJSONArray("weather");
			if (array != null) {
				int length = array.length();
				try {
					for (int i = 0; i < length; i++) {
						JSONObject object = array.optJSONObject(i);
						if (object != null) {
							DateInfo item = new DateInfo();
							item.setDate(object.optString("date"));
							item.setMinTemp(object.optString("tempMinC"));
							item.setMaxTemp(object.optString("temMaxC"));
							item.setStatus(object.optString("weatherCode"));

							JSONArray weatherDescArray = object
									.optJSONArray("weatherDesc");
							for (int j = 0; j < weatherDescArray.length(); j++) {
								JSONObject weaherDescObj = weatherDescArray
										.optJSONObject(j);

								if (weaherDescObj != null) {
									item.setStatus(weaherDescObj
											.optString("value"));
									break;
								}
							}

							JSONArray weatherIconUrlArray = object
									.optJSONArray("weatherIconUrl");

							for (int j = 0; j < weatherIconUrlArray.length(); j++) {
								JSONObject weaherIconUrlObj = weatherIconUrlArray
										.optJSONObject(j);

								if (weaherIconUrlObj != null) {
									item.setStatus(weaherIconUrlObj
											.optString("value"));
									break;
								}
							}

							datas.add(item);
						}

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return datas;
	}

	public String parseTwitterTimelineJson() {
		// TODO Auto-generated method stub
		return issueString;
	}
}
