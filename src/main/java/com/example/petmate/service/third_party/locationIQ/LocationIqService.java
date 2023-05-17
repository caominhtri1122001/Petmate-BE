package com.example.petmate.service.third_party.locationIQ;

import com.example.petmate.model.response.LocationResponse;

public interface LocationIqService {
	LocationResponse getTheLocation(String address);
}
