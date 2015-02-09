package com.wm.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.dh_activity.DisplayGridProducts;
import com.wm.dh_activity.DisplayGridProducts.ViewHolder;
import com.wm.loader.ImageLoader;
import com.wm.model.AppConstants;
import com.wm.model.MyApplication;
import com.wm.model.SingleTon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryHome extends Fragment {

	ListView listCategory;
    private SingleTon singleTon = SingleTon.getInstance();
    ImageView imgCategoryHomeLogo;
    public static int SELECTED_CAT = 0;
    private TextView txtNoData;


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		callBackgroundTaskWithVolly();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_categoryhome_new, null);
		
		txtNoData = (TextView)v.findViewById(R.id.txtNoData);
		txtNoData.setVisibility(View.GONE);
		listCategory = (ListView)v.findViewById(R.id.grid_category);
		imgCategoryHomeLogo = (ImageView)v.findViewById(R.id.imgCategoryHome);
		
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		int w = getActivity().getResources().getDisplayMetrics().widthPixels /2;
		int h = getActivity().getResources().getDisplayMetrics().widthPixels /3;
		
		imgCategoryHomeLogo.getLayoutParams().width = w;
		imgCategoryHomeLogo.getLayoutParams().height = h;
		imgCategoryHomeLogo.requestLayout();
		
		
		listCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SELECTED_CAT = position;
				Intent i = new Intent(getActivity(), DisplayGridProducts.class);
				startActivity(i);
				
				
				
			}
		});
		
		
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void callBackgroundTaskWithVolly() {

		final Dialog dialog;
		dialog = new Dialog(getActivity(),
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.loading);
		dialog.show();

        Log.e("GetAllProduct ",AppConstants.SERVER_URL
                + "getAllProducts");

		JsonArrayRequest req = new JsonArrayRequest(AppConstants.SERVER_URL
				+ "getAllProducts", new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				dialog.dismiss();
				// TODO Auto-generated method stub
				System.out.println("Response : "+response);
				String res = response.toString();
				handledataafterresponseVolly(res);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
				System.out.println("Error : "+error.getMessage());
				dialog.dismiss();

				// Toast.makeText(getActivity(),""+
				// error.getMessage(),Toast.LENGTH_LONG).show();

				jsonRetrive();

			}
		});

		MyApplication.getInstance().addToRequestQueue(req);

	}

	public void handledataafterresponseVolly(final String response) {
		// TODO Auto-generated method stub

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				Type listType = new TypeToken<List<BeanProduct>>() {

				}.getType();

				singleTon.productData = new GsonBuilder().create().fromJson(
						response, listType);
			

				for (int i = 0; i < singleTon.productData.size(); i++) {

					BeanProduct bean = singleTon.productData.get(i);

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

				if (response.length() != 0) {

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
					
					jsonRetrive();

				}

			

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
		txtNoData.setVisibility(View.VISIBLE);
		listCategory.setVisibility(View.GONE);
	    }
	} catch (Exception e) {
	    Log.e("Error : ", e + "");
	    e.printStackTrace();

		txtNoData.setVisibility(View.VISIBLE);
		listCategory.setVisibility(View.GONE);
	} 
    }

	
    private void displayProductsOnline() {
	// TODO Auto-generated method stub

	LinkedHashMap<String, String> map = new LinkedHashMap<String,String>();

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


	// The main method that adds categories to our singleton class and display one by one products in our home page.
	for (Map.Entry<String, String> entry : map.entrySet()) {

	     System.out.println("-- value : " + entry.getValue());
	     singleTon.categories.add(entry.getValue());

	  //  displayIndividualCat(entry.getValue());
	     

	}
	
	ArrayList<String> arrCategory = singleTon.categories;
	
	
	if(arrCategory.size()>0){
		txtNoData.setVisibility(View.GONE);
		listCategory.setVisibility(View.VISIBLE);
		listCategory.setAdapter(new SampleAdapter(getActivity(), arrCategory));
	}else{
		
		txtNoData.setVisibility(View.VISIBLE);
		listCategory.setVisibility(View.GONE);
		
	}
	
	AnimationSet set = new AnimationSet(true);
	Animation animation = new AlphaAnimation(0.0f, 1.0f);
	animation.setDuration(50);
	set.addAnimation(animation);

	animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
			Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

	animation.setDuration(200);
	set.addAnimation(animation);

	LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	

	listCategory.setLayoutAnimation(controller);
	listCategory.startLayoutAnimation();

    }
    
    public class SampleAdapter extends BaseAdapter {

    	Context context;
    	ArrayList<String> sampleData;
    	LayoutInflater inflater;
    	public ImageLoader imageLoader;

    	public SampleAdapter(Context context, ArrayList<String> sampleData) {
    	    this.context = context;
    	    this.sampleData = sampleData;
    	 
    	 
    	}

    	public int getCount() {

    	    return sampleData.size();
    	  
    	}

    	public Object getItem(int position) {
    	    return sampleData.get(position);
    	}

    	public long getItemId(int position) {
    	    return sampleData.indexOf(getItem(position));
    	}

    	public View getView(final int position, View convertView,
    		ViewGroup parent) {

    	    final ViewHolder holder;
    	    LayoutInflater mInflater = (LayoutInflater) context
    		    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    	    if (convertView == null) {

    		convertView = mInflater.inflate(R.layout.grid_item_another, parent,false);

    		holder = new ViewHolder();
    		holder.txtTitle = (TextView) convertView
    			.findViewById(R.id.txtGrid);

    	//	holder.image = (ImageView) convertView
    	//		.findViewById(R.id.imgItemGrid);

    		holder.txtGridPrice = (TextView) convertView
    			.findViewById(R.id.txtGridPrice);
    		
    		holder.txtGridPrice.setVisibility(View.INVISIBLE);

    		convertView.setTag(holder);
    	    } else {
    		holder = (ViewHolder) convertView.getTag();
    	    }

    	    holder.txtTitle.setText(sampleData.get(position));
    	    return convertView;
    	    
    	}

        }
    public class ViewHolder {

    	TextView txtGridPrice;
    	TextView txtTitle;
    	ImageView image;
    	

        }
    

}
