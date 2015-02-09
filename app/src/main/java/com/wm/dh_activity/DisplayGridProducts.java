package com.wm.dh_activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.fragments.CategoryHome;
import com.wm.loader.ImageLoader;
import com.wm.model.AppColors;
import com.wm.model.CategoryAdapter;
import com.wm.model.SingleTon;

public class DisplayGridProducts extends ActionBarActivity {
    
    private SingleTon singleTon = SingleTon.getInstance();
    DecimalFormat df = new DecimalFormat("#.00");
	public int lastposition = 0;
	public TranslateAnimation animation;
	public AnimationSet set = new AnimationSet(true); 
	public boolean mIsScrollingUp = false;
	public int mLastFirstVisibleItem = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_products_grid);
	setupActionBar();
	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	getMenuInflater().inflate(R.menu.product_main, menu);

	return super.onCreateOptionsMenu(menu);
    }

    private void setupActionBar() {

	ActionBar actionBar = getSupportActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setBackgroundDrawable(new ColorDrawable(
		AppColors.APP_THEME_COLOR));
	actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));

	final SpinnerAdapter mSpinnerAdapter = new CategoryAdapter(
			
		DisplayGridProducts.this, android.R.layout.simple_spinner_item,
		singleTon.categories);

	ActionBar ab = getSupportActionBar();
	ab.setTitle("");
	ab.setDisplayHomeAsUpEnabled(true);

	ab.setListNavigationCallbacks(mSpinnerAdapter,
		new OnNavigationListener() {
		    @Override
		    public boolean onNavigationItemSelected(int itemPosition,
			    long itemId) {

			processDisplay(mSpinnerAdapter.getItem(itemPosition)
				.toString());

			return false;
		    }
		});
	
	ab.setSelectedNavigationItem(CategoryHome.SELECTED_CAT);

    }

    protected void processDisplay(String cat) {
	// TODO Auto-generated method stub1345

	final ArrayList<BeanProduct> temp = new ArrayList<BeanProduct>();

	try {
	    temp.clear();
	} catch (Exception e) {

	}

	for (BeanProduct b : singleTon.productData) {

	    if (b.getmCategory().equalsIgnoreCase(cat)
		    && b.getmIsPublished().equalsIgnoreCase("true")) {

		temp.add(b);

	
	
		
	
	    }

	}

	final ListView gv = (ListView) findViewById(R.id.grid_products);

	gv.setAdapter(null);
	gv.setAdapter(new SampleAdapter(DisplayGridProducts.this, temp));
	
	AnimationSet set = new AnimationSet(true);
	Animation animation = new AlphaAnimation(0.0f, 1.0f);
	animation.setDuration(50);
	set.addAnimation(animation);

	animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
			Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

	animation.setDuration(200);
	set.addAnimation(animation);

	LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	gv.setLayoutAnimation(controller);
	gv.startLayoutAnimation();
	
	gv.invalidate();
	gv.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		// TODO Auto-generated method stub

		singleTon.selected_product_bean = temp.get(position);
		Intent intent = new Intent(DisplayGridProducts.this,ProductDetails.class);
		startActivity(intent);

	    }
	});
	
	
	gv.setOnScrollListener(new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
			

			// TODO Auto-generated method stub
			
			 if (view.getId() == gv.getId()) {
			        final int currentFirstVisibleItem = gv.getFirstVisiblePosition();
			         if (currentFirstVisibleItem > mLastFirstVisibleItem) {
			            mIsScrollingUp = false;
			         
			        } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
			            mIsScrollingUp = true;
			         
			        }

			        mLastFirstVisibleItem  = currentFirstVisibleItem;
			 }
			
		
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
		}
	});
	
	
	
	

    }

 public class SampleAdapter extends BaseAdapter {

	Context context;
	List<BeanProduct> sampleData;
	LayoutInflater inflater;
	public ImageLoader imageLoader;

	public SampleAdapter(Context context, List<BeanProduct> sampleData) {
		
	    this.context = context;
	    this.sampleData = sampleData;
	    imageLoader = new ImageLoader(DisplayGridProducts.this);
	    
	 
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
	
	


	public View getView( final int position, View convertView,
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
		

		convertView.setTag(holder);
		
	    } else {
	    	
	    	
		holder = (ViewHolder) convertView.getTag();
		 if(mIsScrollingUp == false){
			    
			    Animation anim = AnimationUtils.loadAnimation(DisplayGridProducts.this, R.anim.slide_up);
		        convertView.setAnimation(anim);
		        anim.start();
			    }
	
		
		
	    }
	  
	    if (sampleData.get(position).getmUnitPrice().equalsIgnoreCase("0") || sampleData.get(position).getmUnitPrice() == null
	    		|| sampleData.get(position).getmUnitPrice().equalsIgnoreCase("")) {
	    	
	    	holder.txtGridPrice.setVisibility(View.INVISIBLE);
	    	
	    }else{
	    	
	    	double dValue = Double.parseDouble(sampleData.get(position).getmUnitPrice());
	 	    String priceToPrint = df.format(dValue).toString();	
	 	    
	 	    holder.txtGridPrice.setText(getResources().getString(R.string.euro)+" "+priceToPrint.replace(".",",").toString());
	    	
	    }
	 
	    holder.txtTitle.setText(sampleData.get(position).getmName());
	    lastposition = position;
		
	    
	   
	    

	    // imageLoader.DisplayImage(sampleData.get(position).getUrl(),holder.image);

	  //  String imgLink1 = AppConstants.IMAGE_LINK_PREFIX + sampleData.get(position).getmThumbnail().replace("~", "").trim();

	 //   imageLoader.DisplayImage(imgLink1, holder.image);

	    // holder.image.setImageResource(R.drawable.temp_item);

	    return convertView;
	    
	}

    }

    public class ViewHolder {

	TextView txtGridPrice;
	TextView txtTitle;
	ImageView image;
	

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub

	switch (item.getItemId()) {

	case R.id.action_example:

		
	    Intent i = new Intent(DisplayGridProducts.this,SearchActivity.class);
	    
	    startActivity(i);

	    break;

	case android.R.id.home:

	    finish();

	    break;

	default:
	    break;
	}

	return super.onOptionsItemSelected(item);
    }

}
