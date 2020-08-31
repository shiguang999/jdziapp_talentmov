package com.movtalent.app.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class DetailAdSectionViewBinder extends ItemViewBinder<DetailAdSection, DetailAdSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_detail_ad_entity, parent, false);
        return new ViewHolder(root);
    }

    /**
     * 加载 播放页广告
     * @param holder
     * @param detailAdEntity
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DetailAdSection detailAdEntity) {
        if (TextUtils.isEmpty(GlobalDATA.AD_INFO)) {
            return;
        }
        ImageView imageView = new ImageView(holder.itemView.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(holder.viewGroup.getLayoutParams());
        AdConfigDto.DataBean dataBean = new Gson().fromJson(GlobalDATA.AD_INFO, AdConfigDto.DataBean.class);
        if (dataBean!=null&&dataBean.getAd_detail()!=null){
            Glide.with(imageView.getContext()).load(dataBean.getAd_detail().getImg()).into(imageView);

            if (!TextUtils.isEmpty(dataBean.getAd_detail().getLink())){
                holder.viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(dataBean.getAd_home_1().getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        imageView.getContext().startActivity(intent);
                    }
                });
            }

            holder.viewGroup.addView(imageView);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup viewGroup;

        ViewHolder(View itemView) {
            super(itemView);
            viewGroup = itemView.findViewById(R.id.webview);
        }
    }
}
