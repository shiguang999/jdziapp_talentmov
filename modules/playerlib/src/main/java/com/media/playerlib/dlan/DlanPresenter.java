package com.media.playerlib.dlan;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.qingfeng.clinglibrary.listener.BrowseRegistryListener;
import com.qingfeng.clinglibrary.service.ClingUpnpService;
import com.qingfeng.clinglibrary.service.manager.ClingManager;
import com.qingfeng.clinglibrary.service.manager.DeviceManager;

/**
 * @author huangyong
 * createTime 2019-10-11
 */
public class DlanPresenter {
    private ServiceConnection mUpnpServiceConnection;

    public DlanPresenter(Context context) {
        initDlan(context);
    }

    public BrowseRegistryListener getRegistryListener() {
        return registryListener;
    }

    private BrowseRegistryListener registryListener = new BrowseRegistryListener();


    private void initDlan(Context context){
        mUpnpServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {

                ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
                ClingUpnpService  beyondUpnpService = binder.getService();

                ClingManager  clingUpnpServiceManager = ClingManager.getInstance();
                clingUpnpServiceManager.setUpnpService(beyondUpnpService);
                clingUpnpServiceManager.setDeviceManager(new DeviceManager());

                clingUpnpServiceManager.getRegistry().addListener(registryListener);
                //Search on service created.
                clingUpnpServiceManager.searchDevices();

                Log.e("getdevices","is");
            }

            @Override
            public void onServiceDisconnected(ComponentName className) {
                mUpnpServiceConnection = null;
            }
        };

        Intent upnpServiceIntent = new Intent(context, ClingUpnpService.class);
        context.bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);

    }

    public void destroy(Context activity) {
        activity.unbindService(mUpnpServiceConnection);
    }

}
