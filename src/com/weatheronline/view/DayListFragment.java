package com.weatheronline.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.weatheronline.R;

public class DayListFragment extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.day_list_fragment, container, false);
		if (v != null) {
			Button btnDetail = (Button) v.findViewById(R.id.btnGotoDetailTest);
			btnDetail.setOnClickListener(this);
		}
		return v;

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

		Log.d("gotoWeatherDetail","gotoWeatherDetail");
		WeatherDetailFragment weatherDetailFrag = new WeatherDetailFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.page_in_left, R.anim.page_in_right);
		transaction.add(R.id.lnDayListContent, weatherDetailFrag);
		transaction.addToBackStack(null);
		transaction.commit();
		
	}

}
