package com.careerdevs.stockinfoapi.repositories;


import com.careerdevs.stockinfoapi.models.Overview;
import org.springframework.data.repository.CrudRepository;

public interface OverviewRepository extends CrudRepository<Overview, Long> {

    public Overview findBySymbol(String symbol);
}
