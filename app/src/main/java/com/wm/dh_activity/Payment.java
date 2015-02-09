package com.wm.dh_activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanConfirmPayment;
import com.wm.customviews.CustomTextview;
import com.wm.loader.ImageLoader;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.MyApplication;
import com.wm.model.SingleTon;

public class Payment extends ActionBarActivity {
    
	
    private Button btnPaypal;
    
    private SingleTon singleTon = SingleTon.getInstance();
    private ImageView img;
    private TextView txtProductName;
    private TextView txtPriceName;
    private ImageLoader imgLoader;
    private static String PRICE = new String();
    private EditText edEnterQUantity;
    private String transactionId;
    private static final String TAG = "paymentExample";
	 
    /**
     * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
     * 
     * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * 
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    // note that these credentials will differ between live & sandbox environments.


 //private static final String CONFIG_CLIENT_ID = "AZiXLBDF98Ev-I3iBtENcT1Gd11jroGWS9lrCFf3UV088mCdqhfaLibGfeui";//sandbox

    private static final String CONFIG_CLIENT_ID = "ATA4yRAjt6gHxbo68Dzk_oh6eHI3741dyMasSB1Y6QOcOOEblN0qVWLrmIrj";
    
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Daryabsofe")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    
    
    DecimalFormat df = new DecimalFormat("#.00");
    TextView txtPaymentOverView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		setupActionBar();
		imgLoader = new ImageLoader(Payment.this);

		Intent intent = new Intent(this, PayPalService.class);
		      intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		      startService(intent);
		
		txtPriceName = (TextView)findViewById(R.id.txtPaymentPrice);
		txtProductName = (TextView)findViewById(R.id.txtPaymentTitle);
		img = (ImageView)findViewById(R.id.imgPaymentThumbnail);
		edEnterQUantity = (EditText)findViewById(R.id.edEnterQUantity);
		txtPaymentOverView = (TextView)findViewById(R.id.txtPaymentOverview);
		
		
		new Handler().post(new Runnable() {
		    
		    @Override
		    public void run() {
			// TODO Auto-generated method stub
		    	double dValue = Double.parseDouble(singleTon.selected_product_bean.getmUnitPrice());
		   	    String priceToPrint = df.format(dValue).toString();	
		   	    

			txtPriceName.setText(getResources().getString(R.string.euro)+" "+priceToPrint);
			txtProductName.setText(singleTon.selected_product_bean.getmName());
			imgLoader.DisplayImage(AppConstants.IMAGE_LINK_PREFIX+singleTon.selected_product_bean.getmThumbnail().replace("~","").trim().toString(),img);
			
			String overview = "Qty "+BuyRegistrationFormAddresses.quantityOfProduct;
			
			double dv = Double.parseDouble(BuyRegistrationFormAddresses.PRICEUNIT);
	   	    String p = df.format(dv).toString();	
			
			
			String totalP = "Total : "+getResources().getString(R.string.euro)+" "+p;
			
			txtPaymentOverView.setText(overview+"\n"+totalP);
			
		    }
		});
		
		
		
		
		btnPaypal = (Button)findViewById(R.id.btnPaypal);
		btnPaypal.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			
			if(edEnterQUantity.getText().toString().equalsIgnoreCase("")) {
			    
			    Toast.makeText(Payment.this, "please mention quantity of this product", Toast.LENGTH_LONG).show();
			    
			}else {
			    
			    //double one_product_price = Double.parseDouble(singleTon.selected_product_bean.getmUnitPrice());
			    //	int quantityOfProduct = Integer.parseInt(edEnterQUantity.getText().toString());
			    //	double finalPrice = one_product_price * quantityOfProduct;
			    //	PRICE = ""+finalPrice;
			   
				PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
				Intent intent = new Intent(Payment.this,PaymentActivity.class);
				intent.putExtra(PaymentActivity.EXTRA_PAYMENT,thingToBuy);
				startActivityForResult(intent, REQUEST_CODE_PAYMENT);			    
			}
	
		    }
		});
	
	}
	
	private PayPalPayment getThingToBuy(String environment) {
		
	        return new PayPalPayment(new BigDecimal(BuyRegistrationFormAddresses.PRICEUNIT), "EUR","payment in eur",environment);
	   
	    }
		
	    @Override
	    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == REQUEST_CODE_PAYMENT) {
	            
	            if (resultCode == Activity.RESULT_OK) {
	                PaymentConfirmation confirm =
	                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	                if (confirm != null) {
	                    try {
	                    	Log.i("json", confirm.toJSONObject().getJSONObject("response").getString("id"));
	                    	transactionId=confirm.toJSONObject().getJSONObject("response").getString("id").toString().trim();
	                        
	                        Log.i("payment", confirm.getPayment().toJSONObject().toString(4));
	                       
	                        /**
	                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
	                         * or consent completion.
	                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	                         * for more details.
	                         *
	                         * For sample mobile backend interactions, see
	                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
	                         */
	                        
	                       // Toast.makeText(getApplicationContext(),
	                       // "Payment Confirmation info received from PayPal", Toast.LENGTH_LONG).show();
	                     
	                        
	                       // new BackgroundTaskForConfirmPayment().execute(); 
	                       CallTaskForConfirmPayment();
	                       showCompletionDialog();
	                    
	                    } catch (JSONException e) {
	                	
	                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
	                        
	                    }
	                }
	            } else if (resultCode == Activity.RESULT_CANCELED) {
	        	
	                Log.i(TAG, "The user canceled.");
	            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
	        	
	                Log.i(
	                        TAG,
	                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
	            }
	        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
	            if (resultCode == Activity.RESULT_OK) {
	                PayPalAuthorization auth =
	                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
	                if (auth != null) {
	                    try {
	                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

	                        String authorization_code = auth.getAuthorizationCode();
	                        Log.i("FuturePaymentExample", authorization_code);

	                        sendAuthorizationToServer(auth);
	                        Toast.makeText(
	                                getApplicationContext(),
	                                "Future Payment code received from PayPal", Toast.LENGTH_LONG)
	                                .show();

	                    } catch (JSONException e) {
	                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
	                    }
	                }
	            } else if (resultCode == Activity.RESULT_CANCELED) {
	                Log.i("FuturePaymentExample", "The user canceled.");
	            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
	                Log.i(
	                        "FuturePaymentExample",
	                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
	            }
	        }
	    }
	
	
		private void showCompletionDialog() {
			// TODO Auto-generated method stub
		    final AlertDialog dialog;

		    AlertDialog.Builder alert = new Builder(Payment.this);
		    alert.setCancelable(false);
		    alert.setTitle("Payment Successful");
		    String msg = "Your order is received for "+singleTon.selected_product_bean.getmName()+"\n"+"Your transactionId is :"+transactionId+"\n"+"Your orderId is :"+BuyRegistrationFormAddresses.orderId;
		    
		    alert.setMessage(msg);
		    
		    alert.setPositiveButton("Ok",
		    		new DialogInterface.OnClickListener() {
		    		    @Override
		    		    public void onClick(DialogInterface dialog,
		    			    int which) {

		    			dialog.dismiss();
		    			
		    			Intent i = new Intent(Payment.this,ProductMain.class);
		    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    			startActivity(i);
		    			
		    			
		    		    }
		    		});

		    dialog = alert.create();
		    dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
		 
			   dialog.show();
			
		    }


	private void setupActionBar() {

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
		actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
	
		SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
		actionBar.setDisplayShowCustomEnabled(true);
		CustomTextview tv = new CustomTextview(Payment.this, null);
		
		tv.setText("PAYMENT");
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		actionBar.setCustomView(tv, SingleTon.getInstance().acBarParams);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
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
	
	 private void sendAuthorizationToServer(PayPalAuthorization authorization) {

	        /**
	         * TODO: Send the authorization response to your server, where it can
	         * exchange the authorization code for OAuth access and refresh tokens.
	         * 
	         * Your server must then store these tokens, so that your server code
	         * can execute payments for this user in the future.
	         * 
	         * A more complete example that includes the required app-server to
	         * PayPal-server integration is available from
	         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
	         */

	    }
	 
	 private void CallTaskForConfirmPayment(){
	
		 try {
				
				
				final Dialog dialog;
				dialog = new Dialog(Payment.this,android.R.style.Theme_Translucent_NoTitleBar);
				dialog.setContentView(R.layout.loading_bottom);
				dialog.show();
				
				Map<String, String> confirmPayment = new HashMap<String, String>();
				confirmPayment.put("IsPaymentComplete", "true");
				confirmPayment.put("OrderID", BuyRegistrationFormAddresses.orderId);
				confirmPayment.put("PaymentTransactionID", transactionId);
			
				String confirmPaymentInformation = new GsonBuilder().create().toJson(confirmPayment, Map.class);
				
				JSONObject jj = new JSONObject(confirmPaymentInformation);
			
				
				JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,AppConstants.URL_CONFIRMPAYMENT,jj, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jobj) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String res = jobj.toString();
						
						
						BeanConfirmPayment beanConfirmPaymentInfo =new GsonBuilder().create().fromJson(res, BeanConfirmPayment.class);	
						
						SingleTon.getInstance().beanCustomerAddressForConfirmPayment=beanConfirmPaymentInfo.getmCustomerAddress();
						SingleTon.getInstance().beanUSerInfoForConfirmPayment=beanConfirmPaymentInfo.getmCustomerInfo();
						SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment=beanConfirmPaymentInfo.getmOrderedProductsList();
						
						Log.e("Address ID",beanConfirmPaymentInfo.getmAddressID()+"");
						//customer address object
						Log.e("Address1",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmAddress1()+"");
						Log.e("Address2",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmAddress2()+"");
						Log.e("AddressID",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmAddressID()+"");
						Log.e("City",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmCity()+"");
						Log.e("Country",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmCountry()+"");
						Log.e("CustomerID",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmCustomerID()+"");
						Log.e("NearestLandMark",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmNearestLandMark()+"");
						Log.e("PinCode",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmPinCode()+"");
						Log.e("State",SingleTon.getInstance().beanCustomerAddressForConfirmPayment.getmState()+"");
						
						// customer info
						Log.e("City",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmCity()+"");
						Log.e("CustomerID",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmCustomerID()+"");
						Log.e("EmailID",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmEmailID()+"");
						Log.e("FirstName",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmFirstName()+"");
						Log.e("LastName",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmLastName()+"");
						Log.e("PhoneNumber",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmPhoneNumber()+"");
						Log.e("State",SingleTon.getInstance().beanUSerInfoForConfirmPayment.getmState()+"");
						
						Log.e("IsPaymentComplete",beanConfirmPaymentInfo.getmIsPaymentComplete()+"");
						Log.e("OrderDate",beanConfirmPaymentInfo.getmOrderDate()+"");
						Log.e("OrderID",beanConfirmPaymentInfo.getmOrderID()+"");
						Log.e("PaymentTransactionID",beanConfirmPaymentInfo.getmPaymentTransactionID()+"");
						
						for(int i=0;i<SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.size();i++) {
							Log.e("OrderID",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmOrderID()+"");
							Log.e("OrderproductID",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmOrderproductID()+"");
							Log.e("ProductID",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmProductID()+"");
							Log.e("Quantity",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmQuantity()+"");
							Log.e("TotalPrice",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmTotalPrice()+"");
							Log.e("UnitPrice",SingleTon.getInstance().beanOrderedProdutsListForConfirmPayment.get(i).getmUnitPrice()+"");
							
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

						dialog.dismiss();
					}
				});
				
				
				MyApplication.getInstance().addToRequestQueue(req);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		 
	 }
	 
	 

}
