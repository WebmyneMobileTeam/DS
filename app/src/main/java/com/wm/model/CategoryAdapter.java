package com.wm.model;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webmyne.daryabherbarium.R;

public class CategoryAdapter extends ArrayAdapter<String>{
	
	
	Context context; 
	List<String> arr;
	int dim = 0;

	public CategoryAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr = objects;
		
		dim = (int)context.getResources().getDimension(R.dimen.activity_vertical_margin);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView tv = new TextView(context);
		tv.setTextColor(Color.WHITE);
		tv.setPadding(10,10,10,10);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f);
		tv.setText(arr.get(position).toString());
		
		
		return tv;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setTextColor(Color.WHITE);
		tv.setPadding(dim,dim, dim,dim);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f);
		tv.setText(arr.get(position).toString());
		
		return tv;
	}
	
	

}
