package spring.framework.beerworks.services;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import spring.framework.beerworks.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(File scvFile) {

        try {
            List<BeerCSVRecord> records = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(scvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
            return records;


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }


    }
}
