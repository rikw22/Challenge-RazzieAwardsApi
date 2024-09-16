package com.example.demo.controller;

import com.example.demo.dto.ProducersStatsResponse;
import com.example.demo.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducersController {

    private final MovieService movieService;

    @Operation(summary = "Return the producer with the longest interval between two consecutive awards, and the one who obtained two awards the fastest.")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200",  description = "Return OK",  content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProducersStatsResponse.class))})
    })
    @GetMapping(value ="/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducersStatsResponse> stats(){

        ProducersStatsResponse response = movieService.stats();
        return ResponseEntity.ok(response);
    }

}
