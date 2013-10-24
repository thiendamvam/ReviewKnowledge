package com.weatheronline.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.weatheronline.R;
import com.weatheronline.adapter.DateAdapter;

public class DayListFragment extends ListFragment implements OnClickListener {

	private ListView mLvDateList;
	private Context mContext;
	private Cursor mDateData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.day_list_fragment, container, false);
		mLvDateList = (ListView) v.findViewById(R.id.lvDateList);
		if (v != null) {
			Button btnDetail = (Button) v.findViewById(R.id.btnGotoDetailTest);
			btnDetail.setOnClickListener(this);
		}

		exeBindDateData();
		return v;

	}

	private void exeBindDateData() {
		// TODO Auto-generated method stub
		mDateData = getDateDate();

		if (mDateData != null) {
			// Create data adapter to bind in to date listview
			DateAdapter mDateAdapter = new DateAdapter(mContext, mDateData);
			mLvDateList.setAdapter(mDateAdapter);
		}

	}

	private Cursor getDateDate() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnGotoDetailTest:
			gotoWeatherDetail();

			break;

		default:
			break;
		}
	}

	public void gotoWeatherDetail() {

		Log.d("gotoWeatherDetail", "gotoWeatherDetail");

		WeatherDetailFragment weatherDetailFrag = new WeatherDetailFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.page_in_left,
				R.anim.page_in_right);
		transaction.add(R.id.lnDayListContent, weatherDetailFrag);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
}
