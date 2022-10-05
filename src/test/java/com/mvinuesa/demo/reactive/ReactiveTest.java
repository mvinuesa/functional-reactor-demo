package com.mvinuesa.demo.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

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

    }



}
