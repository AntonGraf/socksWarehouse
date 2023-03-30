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
            @RequestParam(name = "color")  String color,
            @RequestParam(name = "operation") String operation,
            @RequestParam(name = "cottonPart") int cottonPart
    ) {
        return ResponseEntity.ok(socksService.getSocks(color, operation, cottonPart));
    }

    @PostMapping("/income")
    public void income(@RequestBody SocksDto socksDto) {


    }

    @PostMapping("/outcome")
    public void outcome(@RequestBody SocksDto socksDto) {

    }
}
