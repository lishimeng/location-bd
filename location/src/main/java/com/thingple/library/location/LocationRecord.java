package com.thingple.library.location;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;

import java.util.List;

/**
 * 位置信息
 * Created by lism on 2017/9/27.
 */
public class LocationRecord {

    private BDLocation location;

    private long updateTime;

    private boolean debugLocation = false;

    public void update(BDLocation location) {
        this.location = location;
        updateTime = System.currentTimeMillis();
        Log.d(getClass().getName() + "#update", "位置更新[" + location.getLongitude() + "," + location.getLatitude() + "]");
        if (debugLocation) {
            printLocation();
        }
    }

    public BDLocation getLocation() {
        return this.location;
    }

    public long lastUpdateTime() {
        return updateTime;
    }

    private void printLocation() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n打印位置详细信息:");

        //获取定位结果
        sb.append("\n定位时间:").append(location.getTime());
        sb.append("\n定位ID:").append(location.getLocationID());
        sb.append("\n定位类型:").append(location.getLocType());
        sb.append("\n纬度:").append(location.getLatitude());
        sb.append("\n经度:").append(location.getLongitude());
        sb.append("\n精准度:").append(location.getRadius());
        sb.append("\n地址:").append(location.getAddrStr());
        sb.append("\n国家:").append(location.getCountry());
        sb.append("\n国家码:").append(location.getCountryCode());
        sb.append("\n城市:").append(location.getCity());
        sb.append("\n城市码:").append(location.getCityCode());
        sb.append("\n区县:").append(location.getDistrict());
        sb.append("\n街道:").append(location.getStreet());
        sb.append("\n街道码:").append(location.getStreetNumber());
        sb.append("\n位置描述:").append(location.getLocationDescribe());
        List<Poi> pois = location.getPoiList();    //获取当前位置周边POI信息
        if (pois != null) {
            sb.append("\nPOI:");
            for (Poi poi : pois) {
                sb.append("\n");
                sb.append("\t").append(poi.getId());
                sb.append("\t").append(poi.getName());
            }
        }

        sb.append("\n室内精准定位信息:");
        sb.append("\n楼宇ID:").append(location.getBuildingID());
        sb.append("\n楼宇名称:").append(location.getBuildingName());
        sb.append("\n楼层:").append(location.getFloor());

        if (location.getLocType() == BDLocation.TypeGpsLocation){

            sb.append("\nGPS定位");
            sb.append("\n速度(公里每小时):").append(location.getSpeed());
            sb.append("\n卫星数:").append(location.getSatelliteNumber());
            sb.append("\n海拔(米):").append(location.getAltitude());
            sb.append("\n方向(度):").append(location.getDirection());

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

            sb.append("\n移动基站定位");
            sb.append("\n运营商:").append(location.getOperators());

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

            //当前为网络定位结果
            sb.append("\n离线定位");

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            //当前网络定位失败
            //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com
            sb.append("\n网络定位失败:SDK异常");

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            //当前网络不通
            sb.append("\n网络不稳定");

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            sb.append("\n定位失败:需要用户授权");
            //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
            //可进一步参考onLocDiagnosticMessage中的错误返回码

        }
        Log.d(getClass().getName() + "#printLocation", sb.toString());
    }
}
