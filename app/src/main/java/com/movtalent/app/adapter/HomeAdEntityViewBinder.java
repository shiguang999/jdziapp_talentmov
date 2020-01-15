package com.movtalent.app.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.media.playerlib.widget.GlobalDATA;
import com.movtalent.app.R;
import com.media.playerlib.model.AdConfigDto;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class HomeAdEntityViewBinder extends ItemViewBinder<HomeAdEntity, HomeAdEntityViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_home_ad_entity, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeAdEntity homeAdEntity) {
        if (TextUtils.isEmpty(GlobalDATA.AD_INFO)) {
            return;
        }
        ImageView imageView = new ImageView(holder.itemView.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(holder.adContent.getLayoutParams());
        AdConfigDto.DataBean dataBean = new Gson().fromJson(GlobalDATA.AD_INFO, AdConfigDto.DataBean.class);
        if (dataBean != null) {
            switch (homeAdEntity.getIndex()) {
                case 0:
                    Glide.with(imageView.getContext()).load(dataBean.getAd_home_1().getImg()).into(imageView);
                    if (dataBean.getAd_home_1()==null||TextUtils.isEmpty(dataBean.getAd_home_1().getLink())){
                        return;
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(dataBean.getAd_home_1().getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            imageView.getContext().startActivity(intent);
                        }
                    });
                    imageView.setLayoutParams(holder.adContent.getLayoutParams());
                    holder.adContent.addView(imageView);
                    break;
                case 1:
                    Glide.with(imageView.getContext()).load(dataBean.getAd_home_2().getImg()).into(imageView);
                    if (dataBean.getAd_home_2()==null||TextUtils.isEmpty(dataBean.getAd_home_2().getLink())){
                        return;
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(dataBean.getAd_home_2().getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            imageView.getContext().startActivity(intent);
                        }
                    });
                    imageView.setLayoutParams(holder.adContent.getLayoutParams());
                    holder.adContent.addView(imageView);
                    break;
                case 2:
                    Glide.with(imageView.getContext()).load(dataBean.getAd_home_3().getImg()).into(imageView);
                    if (dataBean.getAd_home_3()==null||TextUtils.isEmpty(dataBean.getAd_home_3().getLink())){
                        return;
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(dataBean.getAd_home_3().getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            imageView.getContext().startActivity(intent);
                        }
                    });
                    imageView.setLayoutParams(holder.adContent.getLayoutParams());
                    holder.adContent.addView(imageView);
                    break;
                case 3:
                    Glide.with(imageView.getContext()).load(dataBean.getAd_home_4().getImg()).into(imageView);
                    if (dataBean.getAd_home_4()==null||TextUtils.isEmpty(dataBean.getAd_home_4().getLink())){
                        return;
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(dataBean.getAd_home_4().getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            imageView.getContext().startActivity(intent);
                        }
                    });
                    imageView.setLayoutParams(holder.adContent.getLayoutParams());
                    holder.adContent.addView(imageView);
                    break;
                default:
                    break;
            }

        }


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout adContent;

        ViewHolder(View itemView) {
            super(itemView);
            adContent = itemView.findViewById(R.id.ad_content);
        }
    }
}
