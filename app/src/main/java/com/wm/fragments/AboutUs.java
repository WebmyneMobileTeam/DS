package com.wm.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webmyne.daryabherbarium.R;

public class AboutUs {
	
	public static class AboutUsFragment extends Fragment{
		
		
		public static AboutUsFragment newInstance(int sectionNumber) {
			
		    AboutUsFragment fragment = new AboutUsFragment();
		    Bundle args = new Bundle();
		    args.putInt("no", sectionNumber);
		    fragment.setArguments(args);
		    
		    return fragment;
		}
		
		public AboutUsFragment() {
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
		
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View v = inflater.inflate(R.layout.fragment_aboutus,null);
		
			
			return v;
		}
		
		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}
	
		
		
	}
	


}
