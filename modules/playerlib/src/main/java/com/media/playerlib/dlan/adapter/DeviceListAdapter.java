package com.media.playerlib.dlan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.playerlib.R;
import com.media.playerlib.dlan.holder.ItemHolder;
import com.qingfeng.clinglibrary.entity.ClingDevice;
import com.qingfeng.clinglibrary.service.manager.ClingManager;
import com.qingfeng.clinglibrary.util.Utils;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-10-05
 */
public class DeviceListAdapter extends RecyclerView.Adapter<ItemHolder> {
    private OnDeviceItemClickListener onDeviceItemClickListener;
    private ArrayList<ClingDevice> devices;
    private Context context;

    public DeviceListAdapter(Context context, ArrayList<ClingDevice> devices, OnDeviceItemClickListener onDeviceItemClickListener) {
        this.devices = devices;
        this.context = context;
        this.onDeviceItemClickListener = onDeviceItemClickListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {

        if (devices.get(position) != null && !TextUtils.isEmpty(devices.get(position).getDevice().getDetails().getFriendlyName())) {
            holder.dlanTitle.setText(devices.get(position).getDevice().getDetails().getFriendlyName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeviceItemClickListener != null) {
                        // 选择连接设备
                        ClingDevice item = devices.get(position);
                        if (Utils.isNull(item)) {
                            onDeviceItemClickListener.onDeviceItemClick(null, false);
                            return;
                        }
                        Device device = item.getDevice();
                        if (Utils.isNull(device)) {
                            onDeviceItemClickListener.onDeviceItemClick(null, false);
                            return;
                        }
                        ClingManager.getInstance().setSelectedDevice(item);
                        onDeviceItemClickListener.onDeviceItemClick(devices.get(position), true);
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
    }

    public void addDevice(ClingDevice device) {
        devices.add(device);
        notifyDataSetChanged();
    }

    public void removeDevice(ClingDevice device) {
        if (devices.contains(device)) {
            devices.remove(device);
        }
        notifyDataSetChanged();
    }


    public interface OnDeviceItemClickListener {
        void onDeviceItemClick(ClingDevice context, boolean isActived);
    }
}
