package com.runone.service.impl;

import com.runone.bean.LocationInfo;
import com.runone.hbase.HbaseHelper;
import com.runone.service.LocationService;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("LocationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    private HbaseHelper hbaseHelper;

    public LocationInfo searchByCode(String code) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setCode(code);
        HTable htable = hbaseHelper.getHtable();
        Get get = new Get(Bytes.toBytes(code));
        try {
            Result result = htable.get(get);
            if (result.isEmpty()) {
                return null;
            }
            byte[] logitude = result.getValue(Bytes.toBytes("location"), Bytes.toBytes("logitude"));
            locationInfo.setLogitude(new String(logitude));
            byte[] latitude = result.getValue(Bytes.toBytes("location"), Bytes.toBytes("latitude"));
            locationInfo.setLatitude(new String(latitude));
            byte[] locatetime = result.getValue(Bytes.toBytes("location"), Bytes.toBytes("locatetime"));
            locationInfo.setLocatetime(new String(locatetime));
            return locationInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
