package com.thingple.location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 位置
 * Created by lism on 2017/9/27.
 */
public class LocationFactory {

    private static LocationFactory ins = null;

    private Context context;

    private LocationClient locationClient;

    private LocationRecord latestLocation;

    public static void init(Context context) {
        ins = new LocationFactory(context);
    }

    public static LocationFactory shareInstance() {
        return ins;
    }

    private LocationFactory(Context context) {
        this.context = context;
        this.latestLocation = new LocationRecord();
        locationClient = new LocationClient(this.context);
        locationClient.setLocOption(initLocation());
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                latestLocation.update(location);
            }
        });
    }

    public void start() {
        if (locationClient != null && !locationClient.isStarted()) {
            this.locationClient.start();
        }
    }

    public LocationRecord getLocation() {
        return this.latestLocation;
    }

    public void stop() {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
        }
    }

    private LocationClientOption initLocation() {
        LocationClientOption options = new LocationClientOption();
        options.setCoorType("bd09ll");
        options.setScanSpan(5000);
        options.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        options.setIsNeedAddress(false);
        options.setOpenGps(true);
        options.setLocationNotify(true);
        return options;
    }
}
