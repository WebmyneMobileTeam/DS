package com.wm.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class API {

	
	private static String SERVER_URL_GET= AppConstants.SERVER_URL;

	public static Reader callWebserviceGet(String url) {

		Reader reader = null;
		try {
			if(AppConstants.SHOW_LOGS==true){
		//	Log.e("SERVER_URL",SERVER_URL+"");
			}
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(SERVER_URL_GET+url);
			HttpResponse response = client.execute(get);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				reader = new InputStreamReader(content);
			} else {
				Log.e("Error", +statusLine.getStatusCode()+"");
			}
		}catch (JsonSyntaxException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (JsonIOException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		}
		return reader;
	}

	public static Reader callWebservicePost(String SERVER_URL,String jsonString) {
		
		Reader reader = null;
		InputStream is=null;
		try {
			if(AppConstants.SHOW_LOGS==true){
			Log.e("SERVER_URL",SERVER_URL+"");
			}
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SERVER_URL);

			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(new StringEntity(jsonString));
			HttpResponse response = client.execute(post);
            
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				reader = new InputStreamReader(is,"UTF-8");
			} else {
				Log.e("Error", +statusLine.getStatusCode()+"");
			}
		}catch (JsonSyntaxException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (JsonIOException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("Error: ",e+"");
			e.printStackTrace();
		} 
		return reader;
	}
}
