package com.runone.service;

import com.runone.bean.LocationInfo;
import org.springframework.stereotype.Service;

public interface LocationService {
    LocationInfo searchByCode(String code);
}
