package ru.skypro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получить количество носков")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = Integer.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    @GetMapping
    public ResponseEntity<Integer> getSocks(
            @RequestParam(name = "color", defaultValue = "черные")  String color,
            @RequestParam(name = "operation", defaultValue = "equal") String operation,
            @RequestParam(name = "cottonPart", defaultValue = "100") short cottonPart
    ) {
        return ResponseEntity.ok(socksService.getQuantitySocksByColorAndCottonPart(color, operation, cottonPart));
    }

    @Operation(summary = "Добавление носков на склад")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    @PostMapping("/income")
    public void income(@RequestBody SocksDto socksDto) {
        socksService.income(socksDto);
    }

    @Operation(summary = "Выдача носков со склад")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    @PostMapping("/outcome")
    public void outcome(@RequestBody SocksDto socksDto) {
        socksService.outcome(socksDto);
    }
}
