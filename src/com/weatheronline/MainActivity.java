package com.weatheronline;

import com.weatheronline.view.DayListFragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {

	private ActionBar actionbar;
	private FrameLayout mFragmentContainer;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		actionbar = getActionBar();
		// setActionbarTitle(actionbar.getTitle().toString());
		mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

		/***
		 * Check Main view in landscape mode or portraint mode. If landscape
		 * mode the mFragmentContainer is not null
		 */
		if (mFragmentContainer != null) {
			if (arg0 != null) {
				return;
			} else {

				try {
					// Create a new instance for DaylistFragment
					DayListFragment dayListFragment = new DayListFragment();

					// Set arguments if have an started intent
					dayListFragment.setArguments(getIntent().getExtras());

					// Start add fragment
					getSupportFragmentManager().beginTransaction()
							.add(R.id.fragment_container, dayListFragment)
							.commit();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} else {

		}
	}

	/***
	 * Create menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/***
	 * Menu item selected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_refresh:
			refreshData();
			return true;
		case R.id.action_menu:
			showMenuView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Set Actionbar title
	 */

	private void setActionbarTitle(String mTitle) {
		// TODO Auto-generated method stub
		actionbar.setTitle(mTitle);
	}

	/***
	 * Refresh data when user touch on the refresh button on the actionbar
	 */
	private void refreshData() {
		// TODO Auto-generated method stub
	}

	/***
	 * Show the menu listview when user touch on the Menu Button on the
	 * actionbar
	 */

	private void showMenuView() {
		// TODO Auto-generated method stub

	}
}
