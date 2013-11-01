package com.weatheronline.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatheronline.R;
import com.weatheronline.model.DateInfo;

public class WeatherDetailFragment extends Fragment {
	private final static String ARG_POSITION = "position";
	private DateInfo mDateInfo;
	private TextView mTvMaxTemp;
	private TextView mTvMinTemp;
	private TextView mTvStatus;
	private ImageView mImgIcon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.weather_detail_fragment,
				container, false);
		/***
		 * Init all field in the Screen
		 */
		initUI(view);

		return view;
	}

	private void initUI(View view) {
		// TODO Auto-generated method stub
		mImgIcon = (ImageView) view.findViewById(R.id.imgIcon);
		mTvStatus = (TextView) view.findViewById(R.id.tvStatus);
		mTvMinTemp = (TextView) view.findViewById(R.id.tvFocasMinValue);
		mTvMaxTemp = (TextView) view.findViewById(R.id.tvFocasMaxValue);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Bundle bundle = getArguments();
		if (bundle != null) {
			mDateInfo = (DateInfo) bundle.getSerializable("date_info");
			if (mDateInfo != null)
				exeDisplayDataToUI(mDateInfo);
		}

	}

	private void exeDisplayDataToUI(DateInfo mDateInfo) {
		try {
			// TODO Auto-generated method stub
			if (mDateInfo.getStatus() != null) {
				mTvStatus.setText("" + mDateInfo.getStatus());
			}
			if (mDateInfo.getMinTemp() != null) {
				mTvMinTemp.setText("" + mDateInfo.getMinTemp());
			}
			if (mDateInfo.getMaxTemp() != null) {
				mTvMaxTemp.setText(mDateInfo.getMaxTemp());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
