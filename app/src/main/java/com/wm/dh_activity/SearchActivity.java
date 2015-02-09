package com.wm.dh_activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.loader.ImageLoader;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.SingleTon;

public class SearchActivity extends ActionBarActivity {
	
	//TODO changes
	private SampleAdapter sampleAdapter;
	private ListView gv;
	public String receivedString = new String();
	//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);
		setupActionBar();
		//TODO changes
		gv = (ListView)findViewById(R.id.search_grid_products);

		gv.setAdapter(null);
		sampleAdapter=new SampleAdapter(SearchActivity.this, SingleTon.getInstance().productData);
		

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SingleTon.getInstance().selected_product_bean = SingleTon.getInstance().productData.get(position);
				Intent intent=new Intent(SearchActivity.this,ProductDetails.class);
				startActivity(intent);

			}
		});
		//
	}

	//TODO Changes
	public class SampleAdapter extends BaseAdapter {

		Context context;
		List<BeanProduct> sampleData;
		ArrayList<BeanProduct> arraylist;
		LayoutInflater inflater;
		public ImageLoader imageLoader;

		public SampleAdapter(Context context, List<BeanProduct> sampleData) {
			this.context = context;
			this.sampleData = sampleData;
			this.arraylist = new ArrayList<BeanProduct>();
			this.arraylist.addAll(sampleData);
			imageLoader = new ImageLoader(SearchActivity.this);
		}



		public int getCount() {

			return sampleData.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			final ViewHolder holder;
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
	

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.grid_item_another,parent,false);
				holder = new ViewHolder();
				
				
				holder.txtTitle = (TextView) convertView.findViewById(R.id.txtGrid);
				
				
				
				//holder.image = (ImageView) convertView.findViewById(R.id.imgItemGrid);
				holder.txtGridPrice = (TextView) convertView
					.findViewById(R.id.txtGridPrice);


				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
		        holder.txtTitle.setText(sampleData.get(position).getmName());
			    holder.txtGridPrice.setText(getResources().getString(R.string.euro)+" "+sampleData.get(position).getmUnitPrice());
			    
			    Spannable spannable = (Spannable)holder.txtTitle.getText();
			    
			   // StyleSpan boldSpan = new StyleSpan(Typeface.BOLD );
			    ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#a0a0a0"));
			
			 
			  int start = sampleData.get(position).getmName().toLowerCase().indexOf(receivedString);
			 
			    spannable.setSpan( span,start,start + receivedString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
		

			//    String imgLink1 = AppConstants.IMAGE_LINK_PREFIX
			//	    + sampleData.get(position).getmThumbnail().replace("~", "")
			//		    .trim();

			 //   imageLoader.DisplayImage(imgLink1, holder.image);
			
			return convertView;
		}

		// Filter Class
		public void filter(String charText) {
			
			
			receivedString = charText;
			
			charText = charText.toLowerCase(Locale.getDefault());
			sampleData.clear();
			if (charText.length() == 0) {
				sampleData.addAll(arraylist);

			} else {
				for (BeanProduct st : arraylist) {
					if (st.getmName().toLowerCase(Locale.getDefault()).contains(charText)) {
						sampleData.add(st);
					}
				}
			}
			notifyDataSetChanged();
		}

	}

	public class ViewHolder {
		
		TextView txtTitle;
		ImageView image;
		TextView txtGridPrice;

	}
	//

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

	private void setupActionBar() {


		ActionBar ab = getSupportActionBar();
		ActionBar.LayoutParams acBarParams2 = new ActionBar.LayoutParams((int) (getResources().getDisplayMetrics().widthPixels / 1.2),ActionBar.LayoutParams.WRAP_CONTENT);
		acBarParams2.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;

		ab.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		View v = getLayoutInflater().inflate(R.layout.item_action_search, null);
		ab.setCustomView(v, acBarParams2);
		ab.setLogo(new ColorDrawable(Color.TRANSPARENT));

		ab.setTitle(""); 

		final EditText ed = (EditText) v.findViewById(R.id.ededSearch);

		ImageView iv = (ImageView) v.findViewById(R.id.imgCancelSearch);

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ed.setText("");

			}
		});


		//
		//		ed.setOnEditorActionListener(new OnEditorActionListener() {
		//
		//			@Override
		//			public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
		//
		//				Toast.makeText(SearchActivity.this, "Now Go",Toast.LENGTH_SHORT).show();
		//
		//				return false;
		//			}
		//		});
		//TODO changes
		ed.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				String text = ed.getText().toString().toLowerCase(Locale.getDefault()); 
				receivedString = text;
				sampleAdapter.filter(text);
				if(text.length() == 0) {
					gv.setAdapter(null);
					gv.invalidate();
				} else {
					gv.setAdapter(sampleAdapter);
					gv.invalidate();
				}
				
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}	
		});
		//

	}









}
