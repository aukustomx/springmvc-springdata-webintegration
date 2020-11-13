package com.aukustomx.country;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CountryService {

    List<Map<String, String>> all();

    Object findAll(Pageable pageable);
}
