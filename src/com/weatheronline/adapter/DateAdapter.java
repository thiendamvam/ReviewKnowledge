package com.weatheronline.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatheronline.R;

public class DateAdapter extends CursorAdapter {

	private LayoutInflater inflater;

	private static class DateViewHolder {
		public TextView tvDate;
		public TextView tvFocasMinValue, tvFocasMaxValue;
	}

	public DateAdapter(Context mContext, Cursor c) {
		super(mContext, c);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		DateViewHolder holder = new DateViewHolder();
		holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
		holder.tvFocasMinValue = (TextView) view.findViewById(R.id.tvFocasMinValue);
		holder.tvFocasMaxValue = (TextView) view.findViewById(R.id.tvFocasMaxValue);

		if (cursor != null) {
			String mDate = cursor.getString(1);
			String mMinTemp = cursor.getString(1);
			String mMaxTemp = cursor.getString(1);

			if (mDate != null) {
				holder.tvDate.setText(mDate);
			}

			if (mMinTemp != null) {
				holder.tvFocasMinValue.setText(mDate);
			}

			if (mMaxTemp != null) {
				holder.tvFocasMaxValue.setText(mDate);
			}
		}
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup contain) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.day_list_fragment, contain, false);
	}

}
