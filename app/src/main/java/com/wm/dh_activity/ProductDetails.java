package com.wm.dh_activity;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProductDetails;
import com.wm.beans.BeanProductImages;
import com.wm.customviews.CustomTextview;
import com.wm.loader.ImageLoader;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.MyApplication;
import com.wm.model.SingleTon;
import com.wm.model.TouchImageView;

public class ProductDetails extends ActionBarActivity {
	
	private DBHelper db;
    private PagerAdapter sampleAdapter;
    private ViewPager viewPager = null;
    private PageIndicator mIndicator = null;
    private ImageView imgExpandCollapse;
    private View hiddenPanel;
    private boolean isShown = false;
    Animation an;
    public int HEIGHT = 0;
    public boolean fake = false;
    private ScrollView hiddenLinear;
    private TextView btnBuy;
    private boolean isFavourite = false;
    String myTemp = new String();
    private boolean isPriceAvailable = true;
    private TextView txtPrice;
    private TextView tvDescription;

    TextView txtNoImages;
    
    private SingleTon singleTon = SingleTon.getInstance();
    DecimalFormat df = new DecimalFormat("#.00"); 
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_product_details);
	db=new DBHelper(ProductDetails.this);
	singleTon.favProductData.clear();
	    
	    try {
			db.opendb();
			singleTon.favProductData=db.viewMethod();
			db.closedb();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	setupActionBar();
	
	txtNoImages = (TextView) findViewById(R.id.txtNoImages);
	txtNoImages.setVisibility(View.INVISIBLE);

	viewPager = (ViewPager) findViewById(R.id.pager);
	mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
	hiddenPanel = findViewById(R.id.hidden_panel);
	hiddenLinear = (ScrollView) findViewById(R.id.linearDetailHidden);
	txtPrice = (TextView) findViewById(R.id.txtDetailPrice);

	btnBuy = (TextView) findViewById(R.id.btnBuy);

	String mPrice = singleTon.selected_product_bean.getmUnitPrice();

	if (mPrice.equalsIgnoreCase("0") || mPrice == null
		|| mPrice.equalsIgnoreCase("")) {

	    isPriceAvailable = false;

	} else {

	    isPriceAvailable = true;
	}

	if (isPriceAvailable == true) {

	    btnBuy.setText("Buy");
	    
	    double dValue = Double.parseDouble(mPrice);
	    String priceToPrint = df.format(dValue).toString();	
	    
	    
	    txtPrice.setText(getResources().getString(R.string.euro)+" "+priceToPrint.replace(".",","));

	} else {

	    btnBuy.setText("Inquiry");
	    txtPrice.setVisibility(View.GONE);
	    btnBuy.getLayoutParams().width = android.widget.FrameLayout.LayoutParams.MATCH_PARENT;
	    btnBuy.requestLayout();

	}

	btnBuy.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		if (isPriceAvailable == true) {

		    // process buy

		    Intent i = new Intent(ProductDetails.this,
			    BuyRegistration.class);
		    startActivity(i);

		} else {

		    // process inquiry

		    registerForContextMenu(btnBuy);
		    openContextMenu(btnBuy);

		}

	    }
	});

	final ViewTreeObserver vto = hiddenPanel.getViewTreeObserver();
	vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

	    @Override
	    public void onGlobalLayout() {

		if (fake == false) {

		    HEIGHT = hiddenPanel.getMeasuredHeight();
		  //  hiddenPanel.setMinimumHeight(HEIGHT);
		    
		    fake = true;

		}

	    }
	});

	imgExpandCollapse = (ImageView) findViewById(R.id.imgExpandCollapse);

	imgExpandCollapse.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		setupDrawwer(isShown);

	    }
	});

	((RelativeLayout) findViewById(R.id.rel))
		.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {

			setupDrawwer(isShown);
		    }
		});


	CallTaskForDescription();
	CallTaskForImages();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {

	getMenuInflater().inflate(R.menu.inquiry, menu);

	super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

	switch (item.getItemId()) {
	
	case R.id.action_callus:
	    
	    final String number = "+983112203610";
	    
	    final AlertDialog dialog;

	    AlertDialog.Builder alert = new Builder(ProductDetails.this);
	    alert.setCancelable(true);
	    alert.setTitle("Call us");

	    String msg = "dial "+number+" ?";
	    
	    alert.setMessage(msg);
	    
	    alert.setPositiveButton("Ok",
	    		new DialogInterface.OnClickListener() {

	    		    @Override
	    		    public void onClick(DialogInterface dialog,
	    			    int which) {
	    			
	    			dialog.dismiss();
	    			
	    		    Intent callIntent = new Intent(Intent.ACTION_CALL);  
	    		    callIntent.setData(Uri.parse("tel:"+number));  
	    		   //startActivity(callIntent); 
	    		    startActivityForResult(callIntent, 1);
	    			
	    			
	    			
	    		
	    		    }
	    		});

	    dialog = alert.create();
	    dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        dialog.show();
		
	 
	    break;

	case R.id.action_emailus:

	    new Handler().post(new Runnable() {

		@Override
		public void run() {

		    Intent i = new Intent(ProductDetails.this, Inquiry.class);
		    startActivity(i);

		}
	    });

	    break;

	default:
	    break;
	}

	return super.onContextItemSelected(item);
    }


    private void setupActionBar() {
    	
	ActionBar actionBar = getSupportActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setBackgroundDrawable(new ColorDrawable(
		AppColors.APP_THEME_COLOR));
	actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));

	singleTon.acBarParams.gravity = Gravity.CENTER
		| Gravity.CENTER_VERTICAL;
	actionBar.setDisplayShowCustomEnabled(true);

	CustomTextview tv = new CustomTextview(ProductDetails.this, null);
	tv.setText(singleTon.selected_product_bean.getmName());
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

	final CheckBox tv2 = new CheckBox(ProductDetails.this);
	// tv2.setBackgroundResource(R.drawable.check);

	//TODO
	if(singleTon.favProductData.isEmpty()==false) {
	Log.e("inside actionbar:","method.......");
	for (int i = 0; i < singleTon.favProductData.size(); i++) {
	
		Log.e("favorite products data:",singleTon.favProductData.get(i).getmProductID()+" y ");
		Log.e("selected products data:",singleTon.selected_product_bean.getmProductID()+" try ");
		if (singleTon.favProductData.get(i).getmProductID()
				.contains(singleTon.selected_product_bean.getmProductID())) {
			tv2.setChecked(true);
	
		}
	}
	}
	//
	tv2.setButtonDrawable(R.drawable.check);
	tv2.setText("");

	RelativeLayout acParent = new RelativeLayout(ProductDetails.this);
	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	params.addRule(RelativeLayout.CENTER_IN_PARENT);

	acParent.addView(tv, params);

	RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	params2.addRule(RelativeLayout.CENTER_VERTICAL);
	params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	params2.rightMargin = (int) (getResources()
		.getDimension(R.dimen.activity_horizontal_margin));

	acParent.addView(tv2, params2);

	actionBar.setCustomView(acParent, singleTon.acBarParams);

	actionBar.setDisplayHomeAsUpEnabled(true);

	// TODO changes
	tv2.setOnCheckedChangeListener(new OnCheckedChangeListener() {


	    @Override
	    public void onCheckedChanged(CompoundButton buttonView,
		    boolean isChecked) {

		if (isChecked) {

			try {
				db.opendb();
				db.addMethod(singleTon.selected_product_bean);
				db.closedb();
				
				Toast.makeText(ProductDetails.this, "Added to Favourites",Toast.LENGTH_LONG).show();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    

		} else {

			try {
				db.opendb();
				db.removeMethod(singleTon.selected_product_bean.getmProductID());
				db.closedb();
				
				Toast.makeText(ProductDetails.this, "Added to Favourites",Toast.LENGTH_LONG).show();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    Toast.makeText(ProductDetails.this, "Removed From Favourites",Toast.LENGTH_LONG).show();

		}

	    }
	});
	//

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	switch (item.getItemId()) {

	case android.R.id.home:

	    finish();

	    break;

	default:
	    break;
	}

	return super.onOptionsItemSelected(item);
    }

    protected void setupDrawwer(boolean isShown2) {

	if (isShown2 == true) {

	    an = new com.wm.model.DropDownAnim(hiddenPanel, getResources()
		    .getDisplayMetrics().widthPixels, false, HEIGHT);
	    an.setAnimationListener(new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
		    imgExpandCollapse
			    .setImageResource(R.drawable.ic_action_collapse);
		    hiddenLinear.setVisibility(View.GONE);

		}
	    });

	    an.setDuration(500);

	    hiddenPanel.startAnimation(an);

	    isShown = false;

	} else {

	    an = new com.wm.model.DropDownAnim(hiddenPanel, getResources()
		    .getDisplayMetrics().widthPixels, true, HEIGHT);

	    an.setAnimationListener(new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
		    imgExpandCollapse
			    .setImageResource(R.drawable.ic_action_expand);
		    hiddenLinear.setVisibility(View.VISIBLE);

		}
	    });

	    an.setDuration(500);

	    hiddenPanel.startAnimation(an);

	    isShown = true;

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
	    imageLoader = new ImageLoader(ProductDetails.this);
	}

	@Override
	public int getCount() {
	    // return mImages.length;
	    return sampleData.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
	    return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
	    Context context = ProductDetails.this;

	    RelativeLayout rel = new RelativeLayout(context);
	    rel.setBackgroundColor(Color.LTGRAY);

	   // ImageView imageView = new ImageView(context);
	    TouchImageView imageView = new TouchImageView(context);

	    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

	    imageView.setImageResource(R.drawable.temp_product);

	    String imgLink1 = AppConstants.IMAGE_LINK_PREFIX
		    + sampleData.get(position).getmImagePath().replace("~", "")
			    .trim();

        Log.e("Image Link : ",imgLink1);

	    imageLoader.DisplayImage(imgLink1, imageView);

	    rel.addView(imageView, RelativeLayout.LayoutParams.MATCH_PARENT,
		    RelativeLayout.LayoutParams.MATCH_PARENT);

	    ((ViewPager) container).addView(rel, 0);

	    imageView.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {

		    if (isShown == true) {
			isShown = true;
			setupDrawwer(isShown);
		    }

		}
	    });

	    return rel;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	    ((ViewPager) container).removeView((RelativeLayout) object);
	}
    }
    
    private void CallTaskForDescription(){
    	
    	
   	 final Dialog dialog;
   	    dialog = new Dialog(ProductDetails.this,
   			    android.R.style.Theme_Translucent_NoTitleBar);
   		    dialog.setContentView(R.layout.loading);
   		    dialog.show();
   		    
   
   			JsonArrayRequest req = new JsonArrayRequest(AppConstants.SERVER_URL+"getProductDetail/"+singleTon.selected_product_bean.getmProductID(), new Listener<JSONArray>() {

   				@Override
   				public void onResponse(JSONArray response) {
   					  dialog.dismiss();
   					// TODO Auto-generated method stub
   					String res = response.toString();
   					
   					Type listType = new TypeToken<List<BeanProductDetails>>() {
   					}.getType();
   					singleTon.productDetails = new GsonBuilder()
   						.create().fromJson(res, listType);

   					for (int i = 0; i < singleTon.productDetails.size(); i++) {
   					    BeanProductDetails bean = singleTon.productDetails
   						    .get(i);
   					    Log.i("DescriptionID", bean.getmDescriptionID() + "");
   					    Log.i("DescriptionLine", bean.getmDescriptionLine() + "");
   					    Log.i("ProductID", bean.getmProductID() + "" + "\n\n");

   					}

   					isShown = false;
   					setupDrawwer(isShown);
   					addDescriptionToSlideContent();
   					
   					
   					
   				}
   			}, new ErrorListener() {

   				@Override
   				public void onErrorResponse(VolleyError error) {
   					// TODO Auto-generated method stub
   					  dialog.dismiss();
   					  
   					  Toast.makeText(ProductDetails.this,""+ error.getMessage(),Toast.LENGTH_LONG).show();
   					
   					
   				}
   			});
   	    	
   	    	MyApplication.getInstance().addToRequestQueue(req);
   
   	
   	
   	
   }
   
   
   private void CallTaskForImages(){
   	
   	
  	 final Dialog dialog;
  	    dialog = new Dialog(ProductDetails.this,
  			    android.R.style.Theme_Translucent_NoTitleBar);
  		    dialog.setContentView(R.layout.loading);
  		    dialog.show();
  		    
  
  			JsonArrayRequest req = new JsonArrayRequest(AppConstants.SERVER_URL+"getProductImages/"
  					+ singleTon.selected_product_bean
  					.getmProductID(), new Listener<JSONArray>() {

  				@Override
  				public void onResponse(JSONArray response) {
  					  dialog.dismiss();
  					// TODO Auto-generated method stub
  					String res = response.toString();
  				// For Get
  					Type listType = new TypeToken<List<BeanProductImages>>() {
  					}.getType();

  					singleTon.productImages = new GsonBuilder()
  						.create().fromJson(res, listType);

  					for (int i = 0; i < singleTon.productImages
  						.size(); i++) {

  					    BeanProductImages bean = singleTon.productImages
  						    .get(i);
  					    Log.i("ImagePath", bean.getmImagePath() + "");
  					    Log.i("ImagesTrsID", bean.getmImagesTrsID() + "");
  					    Log.i("ProductID", bean.getmProductID() + "" + "\n\n");

  					}

  					
  					if (singleTon.productImages == null
  						|| singleTon.productImages.isEmpty()) {

  					    txtNoImages.setVisibility(View.VISIBLE);

  					} else {

  					    txtNoImages.setVisibility(View.INVISIBLE);
  					    sampleAdapter = new SampleAdapter(ProductDetails.this,
  						    singleTon.productImages);
  					    // sampleAdapter= new SampleAdapter();
  					    viewPager.setAdapter(sampleAdapter);
  					    mIndicator.setViewPager(viewPager);

  					}

  					
  					
  					
  				}
  			}, new ErrorListener() {

  				@Override
  				public void onErrorResponse(VolleyError error) {
  					// TODO Auto-generated method stub
  					  dialog.dismiss();
  					  
  					  Toast.makeText(ProductDetails.this,""+ error.getMessage(),Toast.LENGTH_LONG).show();
  					
  					
  				}
  			});
  	    	
  	    	MyApplication.getInstance().addToRequestQueue(req);
  
  	
  	
  	
  }



  
    public void addDescriptionToSlideContent() {


	tvDescription = new TextView(ProductDetails.this);

	Typeface typeface = Typeface.createFromAsset(getAssets(), "FRABK.TTF");

	tvDescription.setTypeface(typeface);

	String strDescription = new String();

	for (BeanProductDetails bean : singleTon.productDetails) {

	    strDescription = strDescription.concat(bean.getmDescriptionLine());

	}

	tvDescription.setText(strDescription);

	tvDescription.setTextColor(Color.WHITE);
	tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

	LinearLayout slideContent = (LinearLayout) findViewById(R.id.slideContent);
	slideContent.addView(tvDescription,
		android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
		android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
	slideContent.invalidate();

    }
}
