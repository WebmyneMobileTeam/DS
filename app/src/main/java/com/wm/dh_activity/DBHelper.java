package com.wm.dh_activity;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wm.beans.BeanProduct;

public class DBHelper extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "daryab.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "favouritetable";
	private static String DB_PATH = "";
	private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( productId INTEGER PRIMARY KEY, productName TEXT, category TEXT, isPublished TEXT, Thumbnail TEXT, unitPrice TEXT)";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;


	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		 DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

	}
	public void onCreate(SQLiteDatabase db) {
		Log.e("onCreate","onCreate");
		db.execSQL(CREATE_TABLE);
	


	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("onUpgrade","onUpgrade");
		db.execSQL(DROP_TABLE);
		onCreate(db);
	
	}

	private static boolean isDataBaseExist(){
		File file =new File(DB_PATH + DATABASE_NAME);  
		return file.exists();
	}
	
	public DBHelper opendb() throws SQLException {
		Log.e("opendb","opendb");
		isDataBaseExist();
		if (isDataBaseExist() == false) {
			db=getWritableDatabase();
			if (db.isOpen())
				db.close();
		}
		db = getWritableDatabase();
		return this;
	}
	public void closedb() {
		db.close();
	}

	public void addMethod(BeanProduct selected_product_bean) {
		Log.e("addMethod","addMethod");
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put("productId",selected_product_bean.getmProductID());
			initialValues.put("productName",selected_product_bean.getmName());
			initialValues.put("category",selected_product_bean.getmCategory());
			initialValues.put("isPublished",selected_product_bean.getmIsPublished());
			initialValues.put("Thumbnail",selected_product_bean.getmThumbnail());
			initialValues.put("unitPrice",selected_product_bean.getmUnitPrice());
			Log.e("initialValues",initialValues+"");
			db.insert(TABLE_NAME, null, initialValues);
			Log.e("inserted","done");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Exception in inserting",e+"");
		}
	} 
	
	 public ArrayList<BeanProduct> viewMethod(){
		  ArrayList<BeanProduct> favoriteData=new ArrayList<BeanProduct>();
		  favoriteData.clear();
		  db=this.getWritableDatabase();
		  String[] columns={"productId", "productName","category","isPublished","Thumbnail","unitPrice"};
		  Cursor cursor=db.query(TABLE_NAME, columns, null, null, null, null, null);
		  while(cursor.moveToNext()){
		   
			  favoriteData.add(new BeanProduct(cursor.getString(cursor.getColumnIndex("category")), cursor.getString(cursor.getColumnIndex("isPublished")), 
					  cursor.getString(cursor.getColumnIndex("productName")), cursor.getString(cursor.getColumnIndex("productId")), cursor.getString(cursor.getColumnIndex("Thumbnail")), cursor.getString(cursor.getColumnIndex("unitPrice"))));
			  Log.e("favourite data",favoriteData+"");
		  }
		  cursor.close();
		  return favoriteData;

		 }

	public void removeMethod(String id){
		db.delete(TABLE_NAME, "productId="+id, null);
	}

}
