package com.movjdzi.app.adapter.user;

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
import com.movjdzi.app.R;
import com.media.playerlib.model.AdConfigDto;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public class SelfAdSectionViewBinder extends ItemViewBinder<SelfAdSection, SelfAdSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_self_ad_section, parent, false);
        return new ViewHolder(root);
    }

    /**
     * 加载个人中心广告
     * @param holder
     * @param selfAdSection
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SelfAdSection selfAdSection) {
        ImageView imageView = new ImageView(holder.itemView.getContext());

        if (!TextUtils.isEmpty(GlobalDATA.AD_INFO)){
            AdConfigDto.DataBean dataBean=  new Gson().fromJson(GlobalDATA.AD_INFO,AdConfigDto.DataBean.class);
            if (dataBean!=null&&dataBean.getAd_user_center()!=null){
                Glide.with(holder.itemView.getContext()).load(dataBean.getAd_user_center().getImg()).into(imageView);
            }
            if (dataBean!=null&&dataBean.getAd_user_center()!=null&&!TextUtils.isEmpty(dataBean.getAd_user_center().getLink())){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(dataBean.getAd_splash().getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        imageView.getContext().startActivity(intent);
                    }
                });
            }
            imageView.setLayoutParams(holder.adContent.getLayoutParams());
            holder.adContent.addView(imageView);
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
