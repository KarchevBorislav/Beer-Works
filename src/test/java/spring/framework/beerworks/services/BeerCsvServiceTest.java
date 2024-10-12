package spring.framework.beerworks.services;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import spring.framework.beerworks.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerCsvServiceTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();


    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);

    }

}