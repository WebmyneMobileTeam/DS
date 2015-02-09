package com.wm.dh_activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.webmyne.daryabherbarium.R;
import com.wm.customviews.CustomTextview;
import com.wm.fragments.AboutUs;
import com.wm.fragments.CategoryHome;
import com.wm.fragments.ContactUs;
import com.wm.fragments.Favourites;
import com.wm.fragments.Home;
import com.wm.model.AppColors;
import com.wm.model.SingleTon;

public class ProductMain extends ActionBarActivity implements
	NavigationDrawerFragment.NavigationDrawerCallbacks,
	SearchView.OnQueryTextListener {

    public static String SELECTED_OPTION = "HOME";
    public static boolean isShowSearch = false;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_product_main);

	SingleTon.getInstance().categories.add("Category 1");
	SingleTon.getInstance().categories.add("Category 2");
	SingleTon.getInstance().categories.add("Category 3");
	SingleTon.getInstance().categories.add("Category 4");

	mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
		.findFragmentById(R.id.navigation_drawer);

	// Set up the drawer.
	mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
		(DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawers();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

	/*
	 * // update the main content by replacing fragments FragmentManager
	 * fragmentManager = getSupportFragmentManager(); fragmentManager
	 * .beginTransaction() .replace(R.id.container,
	 * PlaceholderFragment.newInstance(position + 1)).commit();
	 */
	FragmentManager fragmentManager = getSupportFragmentManager();

	switch (position) {

	// Home

	case 0:

	    SELECTED_OPTION = "HOME";

	    Fragment fHome = Home.HomeFragment.newInstance(position + 1);

	    FragmentTransaction ft = fragmentManager.beginTransaction();

	    ft.replace(R.id.container, fHome, "HomeFragment");

	    // ft.addToBackStack("Home");

	    ft.commit();

	    break;

	    
	case 1:

	    SELECTED_OPTION = "PRODUCTS";

	    Fragment fragmentCategoryHome =new CategoryHome();

	    FragmentTransaction ftProduct = fragmentManager.beginTransaction();

	    ftProduct.replace(R.id.container, fragmentCategoryHome, "ProductFragment");

	    // ftFav.addToBackStack("Fav");

	    ftProduct.commit();

	    break;
	    
	    
	case 2:

	    SELECTED_OPTION = "FAVOURITES";

	    Fragment fFav = Favourites.FavouriteFragment
		    .newInstance(position + 1);

	    FragmentTransaction ftFav = fragmentManager.beginTransaction();

	    ftFav.replace(R.id.container, fFav, "FavouriteFragment");

	    // ftFav.addToBackStack("Fav");

	    ftFav.commit();

	    break;

	case 4:

	    SELECTED_OPTION = "CONTACT US";

	    Fragment fContact = ContactUs.ContactUsFragment
		    .newInstance(position + 1);

	    FragmentTransaction ftC = fragmentManager.beginTransaction();

	    ftC.replace(R.id.container, fContact, "ContactFragment");

	    // ftFav.addToBackStack("Fav");

	    ftC.commit();

	    break;

	case 3:

	    SELECTED_OPTION = "ABOUT US";

	    Fragment fAboutUs = AboutUs.AboutUsFragment
		    .newInstance(position + 1);

	    FragmentTransaction ftA = fragmentManager.beginTransaction();

	    ftA.replace(R.id.container, fAboutUs, "AboutUsFragment");

	    // ftFav.addToBackStack("Fav");

	    ftA.commit();

	    break;

	}

    }

    public void onSectionAttached(int number) {
	switch (number) {
	case 1:

	    break;
	case 2:

	    break;
	case 3:

	    break;
	}
    }

    public void restoreActionBar(String title) {

	ActionBar actionBar = getSupportActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setBackgroundDrawable(new ColorDrawable(
		AppColors.APP_THEME_COLOR));
	actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));

	SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER
		| Gravity.CENTER_VERTICAL;
	actionBar.setDisplayShowCustomEnabled(true);
	CustomTextview tv = new CustomTextview(ProductMain.this, null);

	tv.setText(title);
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	actionBar.setCustomView(tv, SingleTon.getInstance().acBarParams);

	/*
	 * final SpinnerAdapter mSpinnerAdapter = new
	 * CategoryAdapter(ProductMain
	 * .this,android.R.layout.simple_spinner_item,
	 * SingleTon.getInstance().categories); ActionBar ab =
	 * getSupportActionBar(); ab.setTitle("");
	 * ab.setDisplayHomeAsUpEnabled(true);
	 * 
	 * 
	 * ab.setListNavigationCallbacks(mSpinnerAdapter, new
	 * OnNavigationListener() {
	 * 
	 * @Override public boolean onNavigationItemSelected(int itemPosition,
	 * long itemId) {
	 * 
	 * // strSpinnerCity = mSpinnerAdapter.getItem(itemPosition).toString();
	 * //new
	 * ShowCustomToast(FindMainActivity.this,getResources().getStringArray
	 * (R.array.city_list)[itemPosition].toString());
	 * 
	 * return false; } });
	 */

    }

    public void restoreActionBar() {

	ActionBar actionBar = getSupportActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setBackgroundDrawable(new ColorDrawable(
		AppColors.APP_THEME_COLOR));
	actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));

	SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER
		| Gravity.CENTER_VERTICAL;
	actionBar.setDisplayShowCustomEnabled(true);
	CustomTextview tv = new CustomTextview(ProductMain.this, null);

	tv.setText("HOME");
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	actionBar.setCustomView(tv, SingleTon.getInstance().acBarParams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	if (!mNavigationDrawerFragment.isDrawerOpen()) {

	    // Only show items in the action bar relevant to this screen
	    // if the drawer is not showing. Otherwise, let the drawer
	    // decide what to show in the action bar.

	    getMenuInflater().inflate(R.menu.product_main, menu);

	    restoreActionBar(SELECTED_OPTION);

	    return true;
	}
	return super.onCreateOptionsMenu(menu);
    }

    protected boolean isAlwaysExpanded() {
	return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.

	int id = item.getItemId();
	if (id == R.id.action_settings) {
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    /*    *//**
     * A placeholder fragment containing a simple view.
     */
    /*
     * public static class PlaceholderFragment extends Fragment {
     *//**
     * The fragment argument representing the section number for this
     * fragment.
     */
    /*
     * 
     * private static final String ARG_SECTION_NUMBER = "section_number";
     *//**
     * Returns a new instance of this fragment for the given section number.
     */
    /*
     * 
     * public static PlaceholderFragment newInstance(int sectionNumber) {
     * PlaceholderFragment fragment = new PlaceholderFragment(); Bundle args =
     * new Bundle(); args.putInt(ARG_SECTION_NUMBER, sectionNumber);
     * fragment.setArguments(args); return fragment; }
     * 
     * public PlaceholderFragment() { }
     * 
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
     * container, Bundle savedInstanceState) { View rootView =
     * inflater.inflate(R.layout.fragment_product_main, container, false);
     * TextView textView = (TextView) rootView
     * .findViewById(R.id.section_label);
     * textView.setText(Integer.toString(getArguments().getInt(
     * ARG_SECTION_NUMBER))); return rootView; }
     * 
     * @Override public void onAttach(Activity activity) {
     * super.onAttach(activity); ((ProductMain)
     * activity).onSectionAttached(getArguments().getInt( ARG_SECTION_NUMBER));
     * } }
     */

    @Override
    public boolean onQueryTextChange(String arg0) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
	// TODO Auto-generated method stub
	return false;
    }

}
