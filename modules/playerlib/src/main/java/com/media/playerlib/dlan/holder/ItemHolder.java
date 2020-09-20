package com.media.playerlib.dlan.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.media.playerlib.R;


/**
 * @author huangyong
 * createTime 2019-10-05
 */
public class ItemHolder extends RecyclerView.ViewHolder {

    public TextView dlanTitle;
    public TextView dlanStatu;

    public ItemHolder(View itemView) {
        super(itemView);

        dlanTitle = itemView.findViewById(R.id.dlan_device_name);
        dlanStatu = itemView.findViewById(R.id.dlan_statu);

    }
}
