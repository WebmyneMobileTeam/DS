package com.wm.fragments;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.webmyne.daryabherbarium.R;
import com.wm.customviews.CustomTextview;
import com.wm.dh_activity.ProductMain;

public class Home {
	


	 public static class HomeFragment extends Fragment implements OnClickListener {

	
	
		private CustomTextview btnProducts,btnAboutUs,btnFavourites,btnContactUs;
		private LinearLayout linearParent;

		public static HomeFragment newInstance(int sectionNumber) {

			HomeFragment fragment = new HomeFragment();
			Bundle args = new Bundle();
			args.putInt("no", sectionNumber);
			fragment.setArguments(args);

			return fragment;
		}

		public HomeFragment() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);

		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub

			View v = inflater.inflate(R.layout.fragment_home, null);
			
			btnContactUs = (CustomTextview)v.findViewById(R.id.btn_contactus);
			btnContactUs.setOnClickListener(this);
			
			btnFavourites = (CustomTextview)v.findViewById(R.id.btn_favourites);
			btnFavourites.setOnClickListener(this);
			
			btnProducts=(CustomTextview)v.findViewById(R.id.btn_products);
			btnProducts.setOnClickListener(this);
			
			btnAboutUs=(CustomTextview)v.findViewById(R.id.btn_about_us);
			btnAboutUs.setOnClickListener(this);
			
			linearParent = (LinearLayout)v.findViewById(R.id.linearHomePArent);


			return v;
		}

		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}
		
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
			AnimationSet set = new AnimationSet(true);
			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(50);
			set.addAnimation(animation);

			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

			animation.setDuration(200);
			set.addAnimation(animation);

			LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
			
			linearParent.setLayoutAnimation(controller);
			linearParent.startLayoutAnimation();
			
		}
	

		
		public void onClick(View v) {
		    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		    
			switch(v.getId()) {
			
			case R.id.btn_about_us:
				
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				     // only for gingerbread and newer versions
					    ProductMain.SELECTED_OPTION = "ABOUT US";

				    Fragment fAboutUs = AboutUs.AboutUsFragment
					    .newInstance(1 + 1);

				    FragmentTransaction ftA = fragmentManager.beginTransaction();

				    ftA.replace(R.id.container, fAboutUs, "AboutUsFragment");

				    // ftFav.addToBackStack("Fav");

				    ftA.commit();
				    
				    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					
				}else{
					
					goWithAnimation(btnAboutUs);
					
				}
			
			    break;
			
			
			case R.id.btn_contactus:
				
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
					
					    ProductMain.SELECTED_OPTION = "CONTACT US";

				    Fragment fContact = ContactUs.ContactUsFragment
					    .newInstance(1 + 1);

				    FragmentTransaction ftC = fragmentManager.beginTransaction();

				    ftC.replace(R.id.container, fContact, "ContactFragment");

				    // ftFav.addToBackStack("Fav");

				    ftC.commit();
				    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					
				}else{
					  goWithAnimation(btnContactUs);
				}
			    
		
			    
			  
			    
			    break;
			
			
			case R.id.btn_favourites:
				
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
					
					    ProductMain.SELECTED_OPTION = "FAVOURITES";
				    
					 
				    Fragment fFav = Favourites.FavouriteFragment
					    .newInstance(1 + 1);

				    FragmentTransaction ftFav = fragmentManager.beginTransaction();

				    ftFav.replace(R.id.container, fFav, "FavouriteFragment");

				    // ftFav.addToBackStack("Fav");

				    ftFav.commit();

				    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
				   
					
				}else{
					   goWithAnimation(btnFavourites);
				}
			    
			
			 
			    
			    
			    break;
			
			
			
			case R.id.btn_products:
				
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
					
					   ProductMain.SELECTED_OPTION = "HOME";
				    
					Fragment fragmentCategoryHome =new CategoryHome();
				
					FragmentTransaction ft = fragmentManager.beginTransaction();

					ft.replace(R.id.container, fragmentCategoryHome, "CategoryHomeFragment");

					 ft.addToBackStack("Home");

					ft.commit();
					
					 ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					
					
				}else{
					 goWithAnimation(btnProducts);
				}
			
				
	
				
		 
					
				
				
				break;
		

			}

		}
		@SuppressLint("NewApi") public  void goWithAnimation(final CustomTextview view) {
			// TODO Auto-generated method stub
			ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "X",-25);
			
			float w = (float) (view.getWidth() * 1.2);
			ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "X",w);
			scaleDownX.setDuration(300);
			scaleDownY.setDuration(300);
			AnimatorSet scaleDown = new AnimatorSet();	
			scaleDown.playSequentially(scaleDownX,scaleDownY);
			scaleDown.start();
			
			scaleDown.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					// TODO Auto-generated method stub
					  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					switch (view.getId()) {
					
					case R.id.btn_about_us:
					    
					   ProductMain.SELECTED_OPTION = "ABOUT US";

					    Fragment fAboutUs = AboutUs.AboutUsFragment
						    .newInstance(1 + 1);

					    FragmentTransaction ftA = fragmentManager.beginTransaction();

					    ftA.replace(R.id.container, fAboutUs, "AboutUsFragment");

					    // ftFav.addToBackStack("Fav");

					    ftA.commit();
					    
					    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					
						
						
					
						
					    
					    break;
					
					
					case R.id.btn_contactus:
					    
					    ProductMain.SELECTED_OPTION = "CONTACT US";

					    Fragment fContact = ContactUs.ContactUsFragment
						    .newInstance(1 + 1);

					    FragmentTransaction ftC = fragmentManager.beginTransaction();

					    ftC.replace(R.id.container, fContact, "ContactFragment");

					    // ftFav.addToBackStack("Fav");

					    ftC.commit();
					    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					    
					    break;
					
					
					case R.id.btn_favourites:
					    
					    ProductMain.SELECTED_OPTION = "FAVOURITES";
					    
					 
					    Fragment fFav = Favourites.FavouriteFragment
						    .newInstance(1 + 1);

					    FragmentTransaction ftFav = fragmentManager.beginTransaction();

					    ftFav.replace(R.id.container, fFav, "FavouriteFragment");

					    // ftFav.addToBackStack("Fav");

					    ftFav.commit();

					    ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
					    
					    
					    break;
					
					
					
					case R.id.btn_products:
						
				/*	    ProductMain.SELECTED_OPTION = "HOME";
					    
						Fragment fragmentCategoryHome =new CHome();
					
						FragmentTransaction ft = fragmentManager.beginTransaction();

						ft.replace(R.id.container, fragmentCategoryHome, "CategoryHomeFragment");

						 ft.addToBackStack("Home");

						ft.commit();
						
						  ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);*/
						
					    ProductMain.SELECTED_OPTION = "HOME";
					    
								Fragment fragmentCategoryHome =new CategoryHome();
							
								FragmentTransaction ft = fragmentManager.beginTransaction();

								ft.replace(R.id.container, fragmentCategoryHome, "CategoryHomeFragment");

								 ft.addToBackStack("Home");

								ft.commit();
								
								  ((ProductMain)getActivity()).restoreActionBar( ProductMain.SELECTED_OPTION);
						
						
						break;
				

					}
					
					
					
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			
			
			
		}
		
	
 

	}


	

}
