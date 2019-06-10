package com.example.prassanna.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.ArrayList;

class AdapterArticleList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private ArrayList<Articles> mDataList;
	private Context mContext;
	
	public AdapterArticleList(Context con1, ArrayList<Articles> mDataList) {
		mContext = con1;
		this.mDataList = mDataList;
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
			R.layout.adapter_articles_list_items, parent, false);
		return new ItemViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
		Articles mData = mDataList.get(position);
		
		if (holder instanceof ItemViewHolder) {
			ItemViewHolder vItem = (ItemViewHolder) holder;
			
			Picasso.get()
			       .load(mData.getThumb())
			       .placeholder(R.drawable.img_photo)
			       .error(R.drawable.img_photo)
			       .into(vItem.articleBanner);
			
			vItem.adapterTitle.setText(URLDecoder.decode(mData.getTitle()));
			
			vItem.adapterSubTitle.setText(mData.getDescription());
			
			vItem.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
					Intent intent = new Intent(mContext, ActivityArticlesDetail.class);
					intent.putExtra("position", position);
					intent.putExtra("articleData", mDataList);
					mContext.startActivity(intent);
				}
			});
			
		}
	}
	
	
	@Override
	public int getItemCount() {
		return mDataList.size();
	}
	
	public class ItemViewHolder extends RecyclerView.ViewHolder {
		ImageView articleBanner;
		TextView adapterTitle, adapterSubTitle, adapterLabelTitle;
		
		public ItemViewHolder(View view) {
			super(view);
			articleBanner = view.findViewById(R.id.adapter_article_banner_image);
			adapterTitle = view.findViewById(R.id.adapter_article_title);
			adapterSubTitle = view.findViewById(R.id.adapter_sub_title);
			adapterLabelTitle = view.findViewById(R.id.adapter_label_title);
		}
	}
}
