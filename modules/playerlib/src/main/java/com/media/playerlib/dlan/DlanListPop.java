package com.media.playerlib.dlan;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;
import com.media.playerlib.R;
import com.media.playerlib.dlan.adapter.DeviceListAdapter;
import com.qingfeng.clinglibrary.entity.ClingDevice;
import com.qingfeng.clinglibrary.entity.IDevice;
import com.qingfeng.clinglibrary.listener.BrowseRegistryListener;
import com.qingfeng.clinglibrary.listener.DeviceListChangedListener;
import com.qingfeng.clinglibrary.service.ClingUpnpService;
import com.qingfeng.clinglibrary.service.manager.ClingManager;
import com.qingfeng.clinglibrary.service.manager.DeviceManager;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019-10-05
 */
public class DlanListPop extends CenterPopupView {


    private RecyclerView list;
    private View cancle;
    private View help;
    private ServiceConnection mUpnpServiceConnection;
    private BrowseRegistryListener registryListener = new BrowseRegistryListener();
    private String url;
    private String title;
    private ClingUpnpService beyondUpnpService;
    private ClingManager clingUpnpServiceManager;

    public DlanListPop(@NonNull Context context, String url, String title) {
        super(context);
        this.url = url;
        this.title = title;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        list = findViewById(R.id.device_list);
        cancle = findViewById(R.id.dlan_to_cancel);
        help = findViewById(R.id.dlan_to_help);

        final DeviceListAdapter adapter = new DeviceListAdapter(getContext(), new ArrayList<ClingDevice>(), new DeviceListAdapter.OnDeviceItemClickListener() {
            @Override
            public void onDeviceItemClick(ClingDevice device, boolean isActived) {
                if (device != null && isActived) {
                    Intent intent = new Intent(getContext(), MediaPlayActivity.class);
                    intent.putExtra(DLandataInter.Key.PLAY_TITLE,title);
                    intent.putExtra(DLandataInter.Key.PLAYURL,url);
                    getContext().startActivity(intent);
                    dismiss();
                }else {
                    Toast.makeText(getContext(), "未连接到设备", Toast.LENGTH_SHORT).show();
                }
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mUpnpServiceConnection!=null){
                    getContext().unbindService(mUpnpServiceConnection);
                }
            }
        });
        help.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent airPlay = new Intent();
                if (getContext() != null) {
                    airPlay.setClassName(getContext(), "com.movtalent.app.view.CastDescriptionnActivity");
                    airPlay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(airPlay);
                }
            }
        });

        initAndRefresh();
        registryListener.setOnDeviceListChangedListener(new DeviceListChangedListener() {
            @Override
            public void onDeviceAdded(final IDevice device) {
                list.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addDevice((ClingDevice) device);
                    }
                });

            }

            @Override
            public void onDeviceRemoved(final IDevice device) {
                list.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeDevice((ClingDevice) device);
                    }
                });

            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dlan_ui_device_pop_layout;
    }


    public void initAndRefresh() {

        mUpnpServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {

                ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
                beyondUpnpService = binder.getService();

                clingUpnpServiceManager = ClingManager.getInstance();
                clingUpnpServiceManager.setUpnpService(beyondUpnpService);
                clingUpnpServiceManager.setDeviceManager(new DeviceManager());

                clingUpnpServiceManager.getRegistry().addListener(registryListener);
                //Search on service created.
                clingUpnpServiceManager.searchDevices();
            }

            @Override
            public void onServiceDisconnected(ComponentName className) {
                mUpnpServiceConnection = null;
            }
        };

        Intent upnpServiceIntent = new Intent(getContext(), ClingUpnpService.class);
        getContext().bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);


    }

    @Override
    public void dismiss() {
        super.dismiss();
       // getContext().unbindService(mUpnpServiceConnection);
    }
}
