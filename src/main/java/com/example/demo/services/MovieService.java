package com.example.demo.services;

import com.example.demo.dto.ProducersStats;
import com.example.demo.dto.ProducersStatsResponse;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Producer;
import com.example.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public ProducersStatsResponse stats() {
        List<Movie> movieList = movieRepository.findMovieByWinnerIsTrueOrderByYear();
        Map<Producer, List<Integer>> producerAwardsByYear = getProducerAwardsByYear(movieList);
        Set<Map. Entry<Producer, List<Integer>>> atLeastTwoAwardsProducers = getProducerWithAtLeastTwoAwards(producerAwardsByYear);

        // Get periods of awards
        List<ProducersStats> producersStats = getProducerStats(atLeastTwoAwardsProducers);

        // Get min and max intervals
         List<ProducersStats> allMinInterval = getMinIntervals(producersStats);
        List<ProducersStats> allMaxInterval = getMaxIntervals(producersStats);

        return ProducersStatsResponse.builder()
                .min(allMinInterval)
                .max(allMaxInterval)
                .build();
    }

    Map<Producer, List<Integer>> getProducerAwardsByYear(List<Movie> movieList){
        Map<Producer, List<Integer>> result = new HashMap<>();

        movieList.forEach((movie -> {
            var producers = movie.getProducers();

            producers.forEach((producer -> {
                result.putIfAbsent(producer, new ArrayList<>());
                result.get(producer).add(movie.getYear());
            }));
        }));
        return result;
    }

    Set<Map. Entry<Producer, List<Integer>>> getProducerWithAtLeastTwoAwards(Map<Producer, List<Integer>> producerAwardsByYear)  {
        return producerAwardsByYear.entrySet()
                .stream()
                .filter((producerSetEntry) -> producerSetEntry.getValue().size()>1)
                .collect(Collectors.toSet());
    }

    List<ProducersStats> getProducerStats(Set<Map. Entry<Producer, List<Integer>>> atLeastTwoAwardsProducers){
        List<ProducersStats> producersStats = new ArrayList<>();
        atLeastTwoAwardsProducers.forEach((item) -> {

            for(int i =0; i<item.getValue().size(); i++){
                Integer actualYear = item.getValue().get(i);
                Integer nextYear = i+1 <item.getValue().size() ? item.getValue().get(i+1) : null;

                if(nextYear != null){
                    Integer interval = nextYear - actualYear;
                    ProducersStats stats = ProducersStats.builder()
                            .producer(item.getKey().getName())
                            .interval(interval)
                            .previousWin(actualYear)
                            .followingWin(nextYear)
                            .build();
                    producersStats.add(stats);
                }
            }
        });
        producersStats.sort(Comparator.comparingInt(ProducersStats::interval));
        return producersStats;
    }


    List<ProducersStats> getMinIntervals(List<ProducersStats> producersStats){
        var minInterval = producersStats.getFirst().interval();
        return producersStats.stream()
                .filter((item) -> item.interval().equals(minInterval))
                .toList();
    }

    List<ProducersStats> getMaxIntervals(List<ProducersStats> producersStats){
        var maxInterval = producersStats.getLast().interval();
        return producersStats.stream()
                .filter((item) -> item.interval().equals(maxInterval))
                .toList();
    }

    public Movie saveMovie(Movie item){
        return movieRepository.save(item);
    }
}
