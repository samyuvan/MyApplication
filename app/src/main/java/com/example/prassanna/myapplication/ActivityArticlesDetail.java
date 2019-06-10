package com.example.prassanna.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityArticlesDetail extends AppCompatActivity {
	private int mPlayIndex = 0;
	private ArrayList<Articles> mArticlesList = new ArrayList<>();
	private TextView tvArticleTitle, tvArticleDes, tvArticleContent, tvArticlePublished;
	private ImageView articleBannerImage;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);
		
		initViews();
		prepareObjects();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private void initViews() {
		tvArticleTitle = findViewById(R.id.tv_article_title);
		tvArticleDes = findViewById(R.id.tv_article_des);
		tvArticleContent = findViewById(R.id.tv_article_content);
		tvArticlePublished = findViewById(R.id.tv_article_published);
		articleBannerImage = findViewById(R.id.article_banner_image);
	}
	
	private void prepareObjects() {
		Intent intent = getIntent();
		mPlayIndex = intent.getIntExtra("position", 0);
		mArticlesList = (ArrayList<Articles>) intent.getSerializableExtra("articleData");
		
		setDetailsForArticle();
	}
	
	private void setDetailsForArticle() {
		tvArticleTitle.setText(mArticlesList.get(mPlayIndex).getTitle());
		tvArticleDes.setText(mArticlesList.get(mPlayIndex).getDescription());
		tvArticleContent.setText(mArticlesList.get(mPlayIndex).getContent());
		tvArticlePublished.setText(mArticlesList.get(mPlayIndex).getPublishedAt());
		
		Picasso.get()
		       .load(mArticlesList.get(mPlayIndex).getThumb())
		       .error(R.drawable.img_photo)
		       .into(articleBannerImage);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
}