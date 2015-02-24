package com.wm.fragments;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webmyne.daryabherbarium.R;
import com.wm.customviews.CustomButton;
import com.wm.customviews.CustomEdittext;
import com.wm.customviews.CustomTextview;
import com.wm.fileattachment.Constants;
import com.wm.fileattachment.FileChooserActivity;

public class ContactUs {

	public static class ContactUsFragment extends Fragment implements OnClickListener {
		private String fileSelected;
		final int FILE_CHOOSER=1; 
		private CustomEdittext etCompany, etFirstName, etFamilyName, etAddress,
		etZip, etLocation, etCountry, etEmail, etMessage;
		private CustomButton btnAddFile;
		private CustomTextview txtReset, txtSend;

		public static ContactUsFragment newInstance(int sectionNumber) {

			ContactUsFragment fragment = new ContactUsFragment();
			Bundle args = new Bundle();
			args.putInt("no", sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
            
			View v = inflater.inflate(R.layout.fragment_contactus, null);
			etCompany=(CustomEdittext) v.findViewById(R.id.etCompany);
			etFirstName=(CustomEdittext) v.findViewById(R.id.etFirstName);
			etFamilyName=(CustomEdittext) v.findViewById(R.id.etFamilyName);
			etAddress=(CustomEdittext) v.findViewById(R.id.etAddress);
			etZip=(CustomEdittext) v.findViewById(R.id.etZip);
			etLocation=(CustomEdittext) v.findViewById(R.id.etLocation);
			etCountry=(CustomEdittext) v.findViewById(R.id.etCountry);
			etEmail=(CustomEdittext) v.findViewById(R.id.etEmail);
			etMessage=(CustomEdittext) v.findViewById(R.id.etMessage);
			txtReset=(CustomTextview) v.findViewById(R.id.txtReset);
			txtSend=(CustomTextview) v.findViewById(R.id.txtSend);
			btnAddFile=(CustomButton) v.findViewById(R.id.btnAttachment);
			btnAddFile.setOnClickListener(this);
			txtReset.setOnClickListener(this);
			txtSend.setOnClickListener(this);
            
            
			return v;
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
            
			if ((requestCode == FILE_CHOOSER) && (resultCode == getActivity().RESULT_OK)) {
				fileSelected = data.getStringExtra(Constants.KEY_FILE_SELECTED);
				Toast.makeText(getActivity(), "file selected "+fileSelected, Toast.LENGTH_SHORT).show();
				btnAddFile.setText(new File(fileSelected).getName());
			}   
            
		}

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnAttachment:
				Intent intent = new Intent(getActivity(), FileChooserActivity.class);
				startActivityForResult(intent, FILE_CHOOSER);
				break;

			case R.id.txtReset:
				fileSelected="";
				btnAddFile.setText("Add File");
				etCompany.setText("");
				etFirstName.setText("");
				etFamilyName.setText("");
				etAddress.setText("");
				etZip.setText("");
				etLocation.setText("");
				etCountry.setText("");
				etEmail.setText("");
				etMessage.setText("");
				break;

			case R.id.txtSend:
				try {
					final Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { "info@daryabsofe.com" });
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"Daryabsofe Inquiry");
					if (fileSelected != null) {
						emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(fileSelected)));
					}
					String html = "<!DOCTYPE html><html><body>"

			    + "<h4>Hello daryabsofe,</h4>"
			    + "<p></p>"
			    + "<p>"
			    + "Company: "+etCompany.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "First Name: "+etFirstName.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "Family Name: "+etFamilyName.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "Address: "+etAddress.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "ZIP: "+etZip.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "Location: "+etLocation.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "Country: "+etCountry.getText().toString().trim()
			    + "</p>"
			    + "<p>"
			    + "Email: "+etEmail.getText().toString().trim()
			    + "</p>"
			     + "<p>"
			    + "Message: "+etMessage.getText().toString().trim()
			    + "</p>"

			    + "</body></html>";
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,Html.fromHtml(html));
					this.startActivity(Intent.createChooser(emailIntent,
							"Sending email..."));
				} catch (Throwable t) {
					Toast.makeText(getActivity(),
							"Request failed try again: " + t.toString(),
							Toast.LENGTH_LONG).show();
				}
				break;
			}
		}

	}

}
