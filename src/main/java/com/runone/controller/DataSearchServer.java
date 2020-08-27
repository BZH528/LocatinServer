package com.runone.controller;

import com.runone.bean.LocationInfo;
import com.runone.service.LocationService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSearchServer {


    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/api/location/search", method = {RequestMethod.POST, RequestMethod.GET})
    public LocationInfo welcome(String code) {
        if (code != null) {
            return locationService.searchByCode(code);
        }
        return null;
    }

}
