package com.store.products.infraestructure.adapaters.in.controller;

import com.store.products.domain.models.Returns;
import com.store.products.domain.ports.in.ReturnsUsesCases;
import com.store.products.infraestructure.adapaters.in.dto.ReturnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/returns")
@RequiredArgsConstructor
public class ReturnsController {

    private final ReturnsUsesCases usesCases;

    @PostMapping
    public Mono<Returns> createReturns(@RequestBody Returns returns){
        return usesCases.createReturn(returns);
    }
    @PostMapping("/accept/{returnId}")
    public Mono<ReturnsResponse> acceptReturn(@PathVariable("returnId")UUID returnId){
        usesCases.acceptReturn(returnId);
        String message ="The Return Was Successfully Accepted ";
        ReturnsResponse response=new ReturnsResponse(message);
        return Mono.just(response);
    }
    @PostMapping("/reject/{returnId}")
    public Mono<ReturnsResponse> rejectReturn(@PathVariable("returnId")UUID returnId){
        usesCases.rejectReturn(returnId);
        String message ="The Return Was Successfully rejected ";
        ReturnsResponse response=new ReturnsResponse(message);
        return Mono.just(response);
    }

}
