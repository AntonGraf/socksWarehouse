package ru.skypro.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.dto.SocksDto;
import ru.skypro.service.SocksService;

@RestController
@RequestMapping(path = "/api/socks")
@Tag(name ="Склад носков")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping
    public ResponseEntity<Integer> getSocks(
            @RequestParam(name = "color", defaultValue = "черные")  String color,
            @RequestParam(name = "operation", defaultValue = "equal") String operation,
            @RequestParam(name = "cottonPart", defaultValue = "100") short cottonPart
    ) {
        return ResponseEntity.ok(socksService.getQuantitySocksByColorAndCottonPart(color, operation, cottonPart));
    }

    @PostMapping("/income")
    public void income(@RequestBody SocksDto socksDto) {


    }

    @PostMapping("/outcome")
    public void outcome(@RequestBody SocksDto socksDto) {

    }
}
