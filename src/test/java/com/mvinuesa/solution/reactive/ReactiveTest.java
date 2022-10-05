package com.mvinuesa.solution.reactive;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class ReactiveTest {


    @Test
    @DisplayName("test reactive without assert")
    void testReactiveWithoutAssert() {

        Flux.range(0, 8)
            .map(integer -> integer + 1)
            .filter(integer -> integer % 2 == 0)
            .buffer(2)
            .subscribe(
                integers -> LOGGER.info("Block of integers: {}", integers),
                throwable -> LOGGER.info("ERROR: ", throwable),
                () -> LOGGER.info("End"));
    }


    @Test
    @DisplayName("test reactive with assert")
    void testReactiveWithAssert() {

        var flux = Flux.range(0, 8)
            .map(integer -> integer + 1)
            .filter(integer -> integer % 2 == 0)
            .buffer(2);

        StepVerifier.create(flux)
            .expectNext(List.of(2, 4), List.of(6, 8))
            .verifyComplete();

    }



}
