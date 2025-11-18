package com.dat.backend_version_2.controller;

import com.dat.backend_version_2.dto.ScoreDTO;
import com.dat.backend_version_2.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scores")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping("/quarter-summary")
    public ScoreDTO.SummaryScore getSummaryByQuarter(
            @RequestParam int year,
            @RequestParam int quarter,
            @RequestParam String idAccount) {
        return scoreService.getSummaryByQuarter(year, quarter, idAccount);
    }
}
