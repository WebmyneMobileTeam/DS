package com.wm.fragments;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.dh_activity.DBHelper;
import com.wm.dh_activity.ProductDetails;
import com.wm.loader.ImageLoader;
import com.wm.model.AppConstants;
import com.wm.model.SingleTon;

public class Favourites {

    public static class FavouriteFragment extends Fragment {

	private DBHelper db;
	private SingleTon singleTon;
	public static FavouriteFragment newInstance(int sectionNumber) {

	    FavouriteFragment fragment = new FavouriteFragment();
	    Bundle args = new Bundle();
	    args.putInt("no", sectionNumber);
	    fragment.setArguments(args);

	    return fragment;
	}

	public FavouriteFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	}


	@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			db=new DBHelper(getActivity());
			singleTon = SingleTon.getInstance();
		    singleTon.favProductData.clear();
		    
		    try {
				db.opendb();
		
				singleTon.favProductData=db.viewMethod();
				Log.e("size.............",singleTon.favProductData.size()+"");
				db.closedb();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

	    View v = inflater.inflate(R.layout.fragment_favourite, null);
	    // TODO changes
	 
	
	    

	    /*
	     * for(int i=0;i<SingleTon.getInstance().favProductData.size();i++){
	     * Log
	     * .e("product Ids: ",SingleTon.getInstance().favProductData.get(i
	     * ).getmProductID()+""); }
	     */

	    GridView gv = (GridView) v
		    .findViewById(R.id.favourite_grid_products);

	    if (singleTon.favProductData.isEmpty()
		    || singleTon.favProductData == null) {

		Toast.makeText(getActivity(), "No Favourite products added",
			Toast.LENGTH_LONG).show();

	    } else {
		gv.setAdapter(null);
		gv.setAdapter(new SampleAdapter(getActivity(), singleTon.favProductData));
		gv.invalidate();

	    }

	    gv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {

		    // TODO Auto-generated method stub

			singleTon.selected_product_bean = singleTon.favProductData.get(position);
		    Intent intent = new Intent(getActivity(),
			    ProductDetails.class);
		    startActivity(intent);

		}
	    });
	    //
	    return v;
	}

	@Override
	public void onStop() {
	    super.onStop();

	}

	// TODO changes
	public class SampleAdapter extends BaseAdapter {

	    Context context;
	    List<BeanProduct> sampleData;
	    LayoutInflater inflater;
	    public ImageLoader imageLoader;

	    public SampleAdapter(Context context, List<BeanProduct> sampleData) {
		this.context = context;
		this.sampleData = sampleData;
		imageLoader = new ImageLoader(getActivity());
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
		    convertView = mInflater.inflate(R.layout.grid_item,parent,false);
		    holder = new ViewHolder();
		    holder.txtTitle = (TextView) convertView
			    .findViewById(R.id.txtGrid);
		    holder.image = (ImageView) convertView
			    .findViewById(R.id.imgItemGrid);
		    holder.txtGridPrice = (TextView) convertView
				    .findViewById(R.id.txtGridPrice);

		    convertView.setTag(holder);
		} else {
		    holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(sampleData.get(position).getmName());
		
		holder.txtGridPrice.setText(getResources().getString(R.string.euro)+" "+sampleData.get(position).getmUnitPrice());
		// imageLoader.DisplayImage(sampleData.get(position).getUrl(),holder.image);
//
		//String imgLink = "http://healthandsuppliments.com/wp-content/uploads/2009/07/Aloe-Vera-Amazing-Herbal-Product.jpg";

		imageLoader.DisplayImage(AppConstants.IMAGE_LINK_PREFIX+sampleData.get(position).getmThumbnail().replace("~","").trim(), holder.image);

		return convertView;
	    }

	}

	public class ViewHolder {
	    TextView txtTitle;
	    ImageView image;
	    TextView txtGridPrice;

	}

	//
    }

}
