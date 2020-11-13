package com.aukustomx.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Map<String, String>> all() {
        return countryRepository.findAll().stream()
                .map(CountryEntity::asMap)
                .collect(Collectors.toList());
    }

    @Override
    public Object findAll(Pageable pageable) {

        Page<CountryEntity> paginated = countryRepository.findAll(pageable);

        if (pageable.getPageNumber() > paginated.getTotalPages()) {
            String errorMsg = "The number of requested pages: " + pageable.getPageNumber() +
                    ", exceed the existing ones: " +  paginated.getTotalPages();

            throw new RuntimeException(errorMsg);
        }

        return paginated.map(CountryEntity::asMap);
    }
}
