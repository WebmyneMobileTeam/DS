package com.wm.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.beans.BeanProductImages;
import com.wm.customviews.ViewPagerCustomDuration;
import com.wm.dh_activity.DisplayGridProducts;
import com.wm.dh_activity.ProductDetails;
import com.wm.loader.ImageLoader;
import com.wm.model.AppConstants;
import com.wm.model.MyApplication;
import com.wm.model.SingleTon;

public class CHome extends Fragment {

    private LinearLayout linear;
    private ImageLoader loader;
    private PagerAdapter sampleAdapter;
    private ViewPagerCustomDuration viewPager = null;
    private PageIndicator mIndicator = null;
    private TextView txtPriceStrip;
    public static int SELECTED_CAT = 0;
    Timer timer = new Timer();
    public static boolean nowSlide = false;
    Animation fadeIn = new AlphaAnimation(0, 1);
    Animation fadeOut = new AlphaAnimation(1, 0);
    private SingleTon singleTon = SingleTon.getInstance();

    @Override
    public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	loader = new ImageLoader(getActivity());

	fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
	fadeIn.setDuration(2500);

	callBackgroundTaskWithVolly();
	
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {


	View v = inflater.inflate(R.layout.fragment_category_home, null);

	linear = (LinearLayout) v.findViewById(R.id.linear_category_home);
	viewPager = (ViewPagerCustomDuration) v.findViewById(R.id.pagerHome);
	viewPager.setScrollDurationFactor(2);
	mIndicator = (CirclePageIndicator) v.findViewById(R.id.indicatorHome);
	txtPriceStrip = (TextView) v.findViewById(R.id.txtPriceStripHome);

	final AnimationSet animation = new AnimationSet(false); // change to//
		
	txtPriceStrip.setText(getResources().getString(R.string.euro)+" 100");
	// false
	txtPriceStrip.setAnimation(animation);
	mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

	    @Override
	    public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	    	
		animation.addAnimation(fadeIn);
		// animation.addAnimation(fadeOut);

		txtPriceStrip.startAnimation(animation);

	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	    }
	});

	return v;
    }


    @Override
    public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();

	try {
	    timer.cancel();
	} catch (Exception e) {

	}
    }

 

    public class SampleAdapter extends PagerAdapter {

	Context context;
	List<BeanProductImages> sampleData;
	LayoutInflater inflater;
	ImageLoader imageLoader;

	public SampleAdapter(Context context, List<BeanProductImages> sampleData) {

	    this.context = context;
	    this.sampleData = sampleData;
	    imageLoader = new ImageLoader(getActivity());
	}

	@Override
	public int getCount() {
	    // return mImages.length;
	    return sampleData.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
	    return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
	    Context context = getActivity();

	    ImageView imageView = new ImageView(context);
	    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    imageView.setImageResource(R.drawable.temp_a);
	    // imageLoader.DisplayImage(sampleData.get(position).getmImagePath(),
	    // imageView);

	    ((ViewPager) container).addView(imageView, 0);

	    return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	    ((ViewPager) container).removeView((ImageView) object);
	}
    }
    
    
    private void callBackgroundTaskWithVolly(){
    	
    	 final Dialog dialog;
    	  dialog = new Dialog(getActivity(),
    			    android.R.style.Theme_Translucent_NoTitleBar);
    		    dialog.setContentView(R.layout.loading);
    		    dialog.show();
    	
    	
    	JsonArrayRequest req = new JsonArrayRequest(AppConstants.SERVER_URL+"getAllProducts", new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				  dialog.dismiss();
				// TODO Auto-generated method stub
				String res = response.toString();
				handledataafterresponseVolly(res);
				
				
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				  dialog.dismiss();
				  
				 // Toast.makeText(getActivity(),""+ error.getMessage(),Toast.LENGTH_LONG).show();
				  displaySliderImages();
				  jsonRetrive();
				
				
			}
		});
    	
    	MyApplication.getInstance().addToRequestQueue(req);
    	
    	
    }
    
    
    private void displayProductsOnline() {
	// TODO Auto-generated method stub

	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	
	
	for (BeanProduct bean :singleTon.productData) {

		//  The reason for adding category to map is that in map there is no duplicate key. 
		//  Thats way here we get all categories
		
	    map.put(bean.getmCategory(), bean.getmCategory());

	}
	

	// make sure that there is no data before displaying products
	try {
	   singleTon.categories.clear();
	} catch (Exception e) {

	}

	// clear all layouts and make them empty
	try {
	    linear.removeAllViews();
	    linear.invalidate();
	} catch (Exception e) {

	}

	// The main method that adds categories to our singleton class and display one by one products in our home page.
	for (Map.Entry<String, String> entry : map.entrySet()) {

	    System.out.println("-- value : " + entry.getValue());

	     singleTon.categories.add(entry.getValue());

	    displayIndividualCat(entry.getValue());

	}

    }
    
    
    public void handledataafterresponseVolly(final String response) {
	// TODO Auto-generated method stub

	getActivity().runOnUiThread(new Runnable() {

	    @Override
	    public void run() {

	
		Type listType = new TypeToken<List<BeanProduct>>() {
			
		}.getType();

		singleTon.productData = new GsonBuilder()
			.create().fromJson(response, listType);
	
	
		for (int i = 0; i < singleTon.productData.size(); i++) {

		    BeanProduct bean = singleTon.productData
			    .get(i);

		    Log.i("Category", bean.getmCategory() + "");
		    Log.i("CompanyName", bean.getmCompanyName() + "");
		    Log.i("CreationDate", bean.getmCreationDate() + "");
		    Log.i("ExpiryDate", bean.getmExpiryDate() + "");
		    Log.i("IsPublished", bean.getmIsPublished() + "");
		    Log.i("MfgDate", bean.getmMfgDate() + "");
		    Log.i("Name", bean.getmName() + "");
		    Log.i("ProductID", bean.getmProductID() + "");
		    Log.i("Thumbnail", bean.getmThumbnail() + "");
		    Log.i("UnitPrice", bean.getmUnitPrice() + "" + "\n\n");

		}

		if (!response.isEmpty()) {

		    Gson gson = new Gson();
		    String jsonStore = gson.toJson(singleTon.productData);
		    SharedPreferences spStore = getActivity()
			    .getSharedPreferences("ALL_PRODUCTS", 0);
		    SharedPreferences.Editor spe = spStore.edit();
		    spe.putString("all_products_json", jsonStore);
		    spe.commit();
		}

		try {

		    // For Get

		    displaySliderImages();
		    displayProductsOnline();

		} catch (JsonSyntaxException e) {
		    Log.e("Syntax Error: ", e + "");
		    e.printStackTrace();
		} catch (JsonIOException e) {
		    Log.e("JsonIO Error: ", e + "");
		    e.printStackTrace();
		} catch (Exception e) {
		    Log.e("Error: ", e + "");
		    e.printStackTrace();
		    displaySliderImages();
		    jsonRetrive();

		}

		startAutomaticSLider();

	    }
	});

    }

 

    public void startAutomaticSLider() {
	// TODO Auto-generated method stub

	final int delay = 5000; // delay for 5 sec.
	final int period = 5000; // repeat every sec.

	timer = new Timer();

	timer.scheduleAtFixedRate(new TimerTask() {
	    public void run() {
		// your code

		getActivity().runOnUiThread(new Runnable() {

		    @Override
		    public void run() {
			// TODO Auto-generated method stub

			new Handler().post(new Runnable() {

			    @Override
			    public void run() {
				// TODO Auto-generated method stub
				if (nowSlide == false) {

				} else {

				    int x = viewPager.getCurrentItem();

				    if (x == viewPager.getChildCount()) {

					x = 0;

				    } else {

					x = x + 1;

				    }

				    viewPager.setCurrentItem(x,true);

				}
			    }
			});

		    }
		});

	    }
	}, delay, period);

    }

    public void displaySliderImages() {
	// TODO Auto-generated method stub
	// Display slider images
	ArrayList<BeanProductImages> arr = new ArrayList<BeanProductImages>();
	arr.add(new BeanProductImages("", "", ""));
	arr.add(new BeanProductImages("", "", ""));
	arr.add(new BeanProductImages("", "", ""));

	sampleAdapter = new SampleAdapter(getActivity(), arr);
	// sampleAdapter= new SampleAdapter();
	viewPager.setAdapter(sampleAdapter);
	mIndicator.setViewPager(viewPager);

	viewPager.getLayoutParams().height = (int) (getResources()
		.getDisplayMetrics().widthPixels / 1.75);
	viewPager.requestLayout();

	nowSlide = true;

	txtPriceStrip.setVisibility(View.VISIBLE);

    }

    private void displayIndividualCat(String value) {
	// TODO Auto-generated method stub

	final View itemHome = getActivity().getLayoutInflater().inflate(
		R.layout.item_homepage, null);

	linear.addView(itemHome, LayoutParams.MATCH_PARENT,
		LayoutParams.WRAP_CONTENT);
	linear.invalidate();

	// LinearLayout child1 =
	// (LinearLayout)itemHome.findViewById(R.id.parentItemOne);
	ImageView img1 = (ImageView) itemHome.findViewById(R.id.imgItemOne);
	ImageView img2 = (ImageView) itemHome.findViewById(R.id.imgItemTwo);

	TextView txt1 = (TextView) itemHome
		.findViewById(R.id.txt_itemhomepage_productname1);
	TextView txt2 = (TextView) itemHome
		.findViewById(R.id.txt_itemhomepage_productname2);

	TextView txtTitle = (TextView) itemHome
		.findViewById(R.id.txt_itemhomepage_title);
	TextView txtSeeAll = (TextView) itemHome
		.findViewById(R.id.txt_itemhomepage_seeall);

	txtSeeAll.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		SELECTED_CAT = linear.indexOfChild(itemHome);
		Intent i = new Intent(getActivity(), DisplayGridProducts.class);
		startActivity(i);

	    }
	});

	// here we got all products those have category 'value'
	final ArrayList<BeanProduct> temp = new ArrayList<BeanProduct>();

	try {
	    temp.clear();
	} catch (Exception e) {

	}

	for (BeanProduct b : singleTon.productData) {

	    if (b.getmCategory().equalsIgnoreCase(value)
		    && b.getmIsPublished().equalsIgnoreCase("true")) {

		temp.add(b);

	    }

	}
	// ...........................................................

	txtTitle.setText(" " + temp.get(0).getmCategory());

	if (temp.size() <= 2) {

	    // txtSeeAll.setVisibility(View.INVISIBLE);

	    if (temp.size() == 1) {

		((FrameLayout) itemHome.findViewById(R.id.parentItemTwo)).setVisibility(View.GONE);

	    }

	}

	// set particular values..

	txt1.setText(temp.get(0).getmName());

	String imgLink1 = AppConstants.IMAGE_LINK_PREFIX
		+ temp.get(0).getmThumbnail().replace("~", "").trim();

	loader.DisplayImage(imgLink1, img1);

	if (temp.size() >= 2) {

	    txt2.setText(temp.get(1).getmName());

	    String imgLink2 = AppConstants.IMAGE_LINK_PREFIX
		    + temp.get(1).getmThumbnail().replace("~", "").trim();
	    loader.DisplayImage(imgLink2, img2);
	}
	// Toast.makeText(getActivity(), temp.get(0).getmThumbnail(),
	// Toast.LENGTH_LONG).show();

	// img1.setImageResource(R.drawable.temp_item);
	// img2.setImageResource(R.drawable.temp_item);

	// click of particular item

	FrameLayout li1 = (FrameLayout) itemHome
		.findViewById(R.id.parentItemOne);
	FrameLayout li2 = (FrameLayout) itemHome
		.findViewById(R.id.parentItemTwo);

	li1.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels / 3;
	li1.requestLayout();

	li2.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels / 3;
	li2.requestLayout();
	


	((FrameLayout) itemHome.findViewById(R.id.parentItemTwo))
		.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub

			singleTon.selected_product_bean = temp
				.get(1);
			Intent intent = new Intent(getActivity(),
				ProductDetails.class);
			startActivity(intent);

		    }
		});

	((FrameLayout) itemHome.findViewById(R.id.parentItemOne))
		.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub

			singleTon.selected_product_bean = temp
				.get(0);
			Intent intent = new Intent(getActivity(),
				ProductDetails.class);
			startActivity(intent);

		    }
		});

    }

    private void jsonRetrive() {
	try {
	    SharedPreferences spREtrive = getActivity().getSharedPreferences(
		    "ALL_PRODUCTS", 0);
	    String jsonRetrive = spREtrive.getString("all_products_json", null);
	    if (jsonRetrive != null) {
		Type listType = new TypeToken<List<BeanProduct>>() {
		}.getType();

		singleTon.productData = new GsonBuilder()
			.create().fromJson(jsonRetrive, listType);

		displayProductsOnline();

		/*
		 * for(int i=0;i<productData.size();i++) {
		 * Log.e("Category local",productData.get(i).getmCategory()+"");
		 * Log
		 * .e("CompanyName local",productData.get(i).getmCompanyName()
		 * +"");
		 * Log.e("CreationDate local",productData.get(i).getmCreationDate
		 * ()+"");
		 * Log.e("ExpiryDate local",productData.get(i).getmExpiryDate
		 * ()+"");
		 * Log.e("IsPublished local",productData.get(i).getmIsPublished
		 * ()+"");
		 * Log.e("MfgDate local",productData.get(i).getmMfgDate()+"");
		 * Log.e("Name local",productData.get(i).getmName()+"");
		 * Log.e("ProductID local"
		 * ,productData.get(i).getmProductID()+"");
		 * Log.e("Thumbnail local"
		 * ,productData.get(i).getmThumbnail()+"");
		 * Log.e("UnitPrice local"
		 * ,productData.get(i).getmUnitPrice()+""+"\n\n"); }
		 */
		

	    } else {
		Log.e("JSON OUTPUT FOR GET: ", "json string value is "
			+ jsonRetrive + "  ");
	    }
	} catch (JsonSyntaxException e) {
	    Log.e("Error : ", e + "");
	    e.printStackTrace();
	} catch (Exception e) {
	    Log.e("Error in jsonRetrive: ", e + "");
	    e.printStackTrace();
	}
    }

}
