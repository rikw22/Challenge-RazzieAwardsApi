package com.example.demo.infra;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Producer;
import com.example.demo.entity.Studio;
import com.example.demo.services.MovieService;
import com.example.demo.services.ProducerService;
import com.example.demo.services.StudioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class CSVMoviesLoader {
    private static final String CSV_DELIMITER = ";";
    private static final String CSV_FILE = "movielist.csv";
    private static final boolean CSV_SKIP_FIRST_LINE = true;

    private final MovieService movieService;
    private final ProducerService producerService;
    private final StudioService studioService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadDatabase() throws IOException {
        log.info("Loading CSV file into database");
        var csvRecords= getCsvRecords();

        csvRecords.stream()
                .map(this::mapper)
                .peek((item) -> log.info("Importing {}", item))
                .forEach(movieService::saveMovie);

        log.info("Successfully imported! Ready to receive requests!");
    }

    public List<List<String>> getCsvRecords() throws IOException {
        List<List<String>> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader( new InputStreamReader( new ClassPathResource(CSV_FILE).getInputStream() ))){
            String line;
            int i =-1;
            while((line = br.readLine()) != null){
                i++;
                if(i==0 && CSV_SKIP_FIRST_LINE) continue;

                String[] values = line.split(CSV_DELIMITER);
                lines.add(Arrays.asList(values));
            }
        }
        return lines;
    }

    public Movie mapper(List<String> record){
        Set<Studio> studios = processStudio(record.get(2));
        Set<Producer> producers = processProducers(record.get(3));

        return Movie.builder()
                .year(Integer.valueOf(record.get(0)))
                .title(record.get(1))
                .studios(studios)
                .producers(producers)
                .winner(record.size() >4 && record.get(4).equals("yes"))
                .build();
    }

    private Set<Studio> processStudio(String studioName) {
        String[] studios = studioName.replace(" and ", ",").split(",");
        return Stream.of(studios)
                .map(String::trim)
                .filter((sname) -> !sname.isBlank())
                .map(studioService::findOrSaveStudio)
                .collect(Collectors.toSet());
    }

    private Set<Producer> processProducers(String producersName) {
        String[] producers = producersName.replace(" and ", ",").split(",");
        return Stream.of(producers)
                .map(String::trim)
                .filter((pname) -> !pname.isBlank())
                .map(producerService::findOrSaveProducer)
                .collect(Collectors.toSet());
    }

}
