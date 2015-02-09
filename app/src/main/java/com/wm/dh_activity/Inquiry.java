package com.wm.dh_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanProduct;
import com.wm.customviews.CustomEdittext;
import com.wm.customviews.CustomTextview;
import com.wm.model.AppColors;
import com.wm.model.SingleTon;

public class Inquiry extends ActionBarActivity {

    LinkedHashMap<String, String> map_product = new LinkedHashMap<String,String>();
    ArrayList<LinkedHashMap<String, String>> products_to_displayed = new ArrayList<LinkedHashMap<String, String>>();
    ListView listInquiryProducts;
    SimpleAdapter simpleAdapter;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    LinkedHashMap<String, String> ProductsAndNotes = new LinkedHashMap<String,String>();
    Button btnAddProduct;
    ArrayList<String> arrAllProductName = new ArrayList<String>();
    Button btnEmail;

    EditText edFirstName;
    EditText edLastName;
    EditText edCountry;
    EditText edState;
    EditText edMobile;
    
    private ArrayList<BeanProduct> arrProductData;
    private BeanProduct selectedProduct;
    private SingleTon s = SingleTon.getInstance();
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
   
    }
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) throws IllegalStateException{

	super.onCreate(savedInstanceState);
	setContentView(R.layout.inquiry_page);

	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
	
        arrProductData = new ArrayList<BeanProduct>();
	    selectedProduct = null;
	    
	    arrProductData = s.productData;
	    selectedProduct = s.selected_product_bean;
	
	

	try {
	    setUpActionBar();
	    /*
	     * map_product.put(
	     * SingleTon.getInstance().selected_product_bean.getmName(),
	     * "click to add note"); products_to_displayed.add(map_product);
	     */

	    arrAllProductName = new ArrayList<String>();
	    
	    new Handler().post(new Runnable() {

	        @Override
	        public void run() {
	    	// TODO Auto-generated method stub

	    	for (BeanProduct bean : arrProductData) {

	    	    arrAllProductName.add(bean.getmName());

	    	}
	    	removefromall(selectedProduct.getmName());

	        }
	    });

	    ProductsAndNotes = new LinkedHashMap<String, String>();

	    try {
	        ProductsAndNotes.put( selectedProduct.getmName(), "");
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    /*
	     * for (int i = 0; i < ProductsAndNotes.length; i++) {
	     * 
	     * 
	     * }
	     */

	    addToList();

	    simpleAdapter = new SimpleAdapter(this, list,
	    	R.layout.card_play_another, new String[] { "line1", "line2" },
	    	new int[] { R.id.line_a, R.id.line_b });

	    listInquiryProducts = (ListView) findViewById(R.id.listInquiryProducts);
	    listInquiryProducts.setAdapter(simpleAdapter);

	    listInquiryProducts
	    	.setOnItemLongClickListener(new OnItemLongClickListener() {

	    	    @Override
	    	    public boolean onItemLongClick(AdapterView<?> parent,
	    		    View view, final int position, long id) {
	    		// TODO Auto-generated method stub

	    		final AlertDialog dialog;

	    		AlertDialog.Builder alert = new Builder(Inquiry.this);

	    		alert.setTitle("Alert");
	    		String msg = "Delete this item?";
	    		alert.setMessage(msg);

	    		alert.setPositiveButton("delete",new DialogInterface.OnClickListener() {

	    			    @Override
	    			    public void onClick(DialogInterface dialog,
	    				    int which) {

	    				final HashMap<String, String> willChangeMap = list
	    					.get(position);

	    				new Handler().post(new Runnable() {

	    				    @Override
	    				    public void run() {
	    					// stub

	    					int index = list
	    						.indexOf(willChangeMap);

	    					list.remove(index);

	    					simpleAdapter
	    						.notifyDataSetChanged();

	    					arrAllProductName.add(0,
	    						willChangeMap
	    							.get("line1"));

	    				    }
	    				});

	    				dialog.dismiss();

	    			    }
	    			});

	    		alert.setNegativeButton("cancel",
	    			new DialogInterface.OnClickListener() {

	    			    @Override
	    			    public void onClick(DialogInterface dialog,
	    				    int which) {

	    				dialog.dismiss();

	    			    }
	    			});

	    		dialog = alert.create();
	    		dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

	    		// alert.show();
	    		dialog.show();

	    		return false;
	    	    }

	    	});

	    listInquiryProducts.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {

	    	final HashMap<String, String> willChangeMap = list
	    		.get(position);

	    	final AlertDialog dialog;

	    	AlertDialog.Builder alert = new Builder(Inquiry.this);

	    	alert.setTitle("Add Note");
	    	String msg = "";
	    	alert.setMessage(msg);

	    	final CustomEdittext ed = new CustomEdittext(Inquiry.this, null);
	    	ed.setMaxLines(3);
	    	ed.setText(willChangeMap.get("line2"));
	    	ed.setGravity(Gravity.LEFT | Gravity.TOP);

	    	alert.setView(ed);
	    	alert.setPositiveButton("Ok",
	    		new DialogInterface.OnClickListener() {

	    		    @Override
	    		    public void onClick(DialogInterface dialog,
	    			    int which) {
	    			

	    			willChangeMap.put("line2", ed.getText()
	    				.toString());

	    			new Handler().post(new Runnable() {

	    			    @Override
	    			    public void run() {
	    				

	    				int index = list.indexOf(willChangeMap);

	    				list.remove(index);
	    				list.add(index, willChangeMap);
	    				simpleAdapter.notifyDataSetChanged();

	    			    }
	    			});

	    			dialog.dismiss();

	    		    }
	    		});

	    	dialog = alert.create();
	    	dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

	    	// alert.show();
	    	dialog.show();

	        }

	    });
	} catch (Exception e) {
	  
	}

	fetchIds();

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub

	super.onActivityResult(arg0, arg1, arg2);

	 finish();
	
/*	Intent i = new Intent(Inquiry.this,ProductMain.class);
	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(i);*/
	

    }

    private void setUpActionBar() {
	// TODO Auto-generated method stub

	ActionBar actionBar = getSupportActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setDisplayHomeAsUpEnabled(true);
	actionBar.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
	actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));

	
	 ActionBar.LayoutParams acBarParams = new ActionBar.LayoutParams(
		    ActionBar.LayoutParams.WRAP_CONTENT,
		    ActionBar.LayoutParams.WRAP_CONTENT);
	
	acBarParams.gravity = Gravity.CENTER| Gravity.CENTER_VERTICAL;
	
	
	
	actionBar.setDisplayShowCustomEnabled(true);
	CustomTextview tv = new CustomTextview(Inquiry.this, null);

	tv.setText("Inquiry Form");
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	actionBar.setCustomView(tv, s.acBarParams);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub

	switch (item.getItemId()) {

	case android.R.id.home:

	    finish();

	    break;

	default:
	    break;
	}

	return super.onOptionsItemSelected(item);
    }

    private void removefromall(String string) {
	// TODO Auto-generated method stub

	arrAllProductName.remove(string);

    }

    private void addToList() {
	// TODO Auto-generated method stub

	try {
	    list.clear();
	} catch (Exception e) {
	}

	HashMap<String, String> item;

	for (Map.Entry<String, String> entry : ProductsAndNotes.entrySet()) {

	    item = new HashMap<String, String>();

	    item.put("line1", entry.getKey());
	    item.put("line2", entry.getValue());

	    list.add(0, item);
	    

	}

    }

    private void fetchIds() {
	// TODO Auto-generated method stub

	edCountry = (EditText) findViewById(R.id.edCountryInquiry);
	edFirstName = (EditText) findViewById(R.id.edFirstNameInquiry);
	edLastName = (EditText) findViewById(R.id.edLastNameInquiry);
	edMobile = (EditText) findViewById(R.id.edMobileInquiry);
	edState = (EditText) findViewById(R.id.edStateInquiry);

	btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
	btnAddProduct.setOnClickListener(btnClick);

	btnEmail = (Button) findViewById(R.id.btnEmail);

	btnEmail.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
	
		try {
		    
		    getCacheDir().delete();
		    
		}catch(Exception e) {
		    e.printStackTrace();
		}
		
		

		if (checkMandatoryFields() == true) {

		    String productString = new String();

		    for (HashMap<String, String> map : list) {

			System.out.println("---------------------------");

			productString = productString + "<p>" + "* "
				+ map.get("line1") + " : " + map.get("line2")
				+ "</p>";

			System.out.println("Product " + map.get("line1"));
			System.out.println("Description " + map.get("line2"));

		    }

		    String html = "<!DOCTYPE html><html><body>"

			    + "<h4>Hello daryabsofe,</h4>"
			    + "<p></p>"
			    + "<p>This is "
			    + edFirstName.getText().toString()
			    + " "
			    + edLastName.getText().toString()
			    + " from "
			    + edState.getText().toString()
			    + ", "
			    + edCountry.getText().toString()
			    + "</p>"

			    + "<p> I want to purchase this following products from your org.</p><hr>"
			    + "<p></p>" + "<p></p>"

			    + productString + "<p></p>" + "<p></p>"

			    + "<p> my contact no : "
			    + edMobile.getText().toString() + "</p>"

			    + "</body></html>";

		    // bodyContent.concat("Hello daryabsofe");

		    /*
		     * bodyContent.concat("This is " +
		     * edFirstName.getText().toString() + " " +
		     * edLastName.getText().toString() + " from " +
		     * edState.getText().toString() + ", " +
		     * edCountry.getText().toString() + "\n\n"); bodyContent
		     * .concat(
		     * 
		     * "I want to do an inquiry for these particular products as mentioned below."
		     * );
		     */

		    final Intent emailIntent = new Intent(
			    android.content.Intent.ACTION_SEND);

		    /* Fill it with Data */
		    emailIntent.setType("plain/text");
		    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
			    new String[] { "info@daryabsofe.com" });
		    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
			    "Product Inquiry");

		    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
			    Html.fromHtml(html));

		    /* Send it off to the Activity-Chooser */

		    startActivityForResult(
			    Intent.createChooser(emailIntent, "Send mail..."),
			    10);

		    /*
		     * startActivity(Intent.createChooser(emailIntent,
		     * "Send mail..."));
		     */

		}

	    }
	});

    }

    protected boolean checkMandatoryFields() {
	// TODO Auto-generated method stub

	boolean check = false;

	ArrayList<EditText> listEd = new ArrayList<EditText>();

	try {
	    listEd.clear();
	} catch (Exception e) {

	}

	listEd.add(edFirstName);
	listEd.add(edCountry);
	listEd.add(edState);
	listEd.add(edMobile);

	for (EditText ed : listEd) {

	    if (ed.getText().toString().equalsIgnoreCase("")) {

		check = false;

		ed.requestFocus();
		ed.setError("please enter " + ed.getHint());

		break;

	    } else {

		check = true;

	    }

	}

	return check;
    }

    View.OnClickListener btnClick = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub

	    final AlertDialog dialog;

	    AlertDialog.Builder alert = new Builder(Inquiry.this);

	    alert.setTitle("Select Product");
	    String msg = "";
	    alert.setMessage(msg);

	    // final EditText input = new EditText(Inquiry.this);
	    // input.setHint("reply+ tochemist");

	    final ListView lvAllProducts = new ListView(Inquiry.this);

	    lvAllProducts.setAdapter(new ArrayAdapter<String>(Inquiry.this,
		    android.R.layout.simple_list_item_1, arrAllProductName));

	    lvAllProducts.setDivider(new ColorDrawable(Color.LTGRAY));

	    alert.setView(lvAllProducts);

	    dialog = alert.create();
	    dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

	    // alert.show();
	    dialog.show();

	    lvAllProducts.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub

		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("line1", arrAllProductName.get(position));
		    map.put("line2", "");

		    list.add(0, map);

		    // ProductsAndNotes.put(arrAllProductName.get(position),
		    // "");
		    // addToList();

		    simpleAdapter.notifyDataSetChanged();
		    dialog.dismiss();

		    listInquiryProducts.setSelection(0);

		    removefromall(arrAllProductName.get(position));

		    /*
		     * View v = listInquiryProducts.getAdapter().getView(0,
		     * null, listInquiryProducts);
		     * 
		     * TextView tv = (TextView) v.findViewById(R.id.line_a);
		     * Toast.makeText(Inquiry.this, tv.getText().toString(),
		     * Toast.LENGTH_LONG).show();
		     */
		    
		}
	    });

	}
    };

}
