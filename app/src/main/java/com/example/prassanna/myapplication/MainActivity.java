package com.example.prassanna.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "LOG";
	RecyclerView mRecyclerView;
	private ProgressDialog pDialog;
	String url;
	AdapterArticleList mAdapterArticleList;
	ArrayList<Articles> mArticleDataList = new ArrayList<>();
	private Context mContext = this;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		
		// URL to get article JSON
		url = "https://newsapi.org/v2/everything?q=bitcoin&from=2019-05-08&sortBy=publishedAt&apiKey=d714d4f39e444dbd8e272bb7f5926c73";
		
		mRecyclerView = findViewById(R.id.mRecyclerView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
		
		new getArticleListData().execute();
		
	}
	
	private class getArticleListData extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			HttpHandler sh = new HttpHandler();
			
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url);
			
			Log.e(TAG, "Response from url: " + jsonStr);
			
			if (jsonStr != null) {
				try {
					JSONObject mRootJsonObject = new JSONObject(jsonStr);
					mArticleDataList = new GsonBuilder().create().
						fromJson(
							String.valueOf(mRootJsonObject.getJSONArray("articles")),
							new TypeToken<ArrayList<Articles>>() {
							}.getType());
					
				} catch (final JSONException e) {
					Log.e(TAG, "Json parsing error: " + e.getMessage());
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
								getApplicationContext(), "Json parsing error: " + e.getMessage(),
								Toast.LENGTH_LONG)
							     .show();
						}
					});
					
				}
			} else {
				Log.e("issues", "Couldn't get json from server.");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
							getApplicationContext(),
							"Couldn't get json from server. Check LogCat for possible errors!",
							Toast.LENGTH_LONG)
						     .show();
					}
				});
				
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}
			/**
			 * Updating parsed JSON data into Recylerview
			 * */
			
			mAdapterArticleList = new AdapterArticleList(mContext, mArticleDataList);
			mRecyclerView.setAdapter(mAdapterArticleList);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}