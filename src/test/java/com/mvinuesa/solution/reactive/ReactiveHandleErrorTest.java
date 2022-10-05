package com.mvinuesa.solution.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class ReactiveHandleErrorTest {


    @Test
    @DisplayName("test reactive error")
    void testReactiveError() {

        Flux.range(1, 5).map(this::getAnInt)
            .reduce(Integer::sum)
            .subscribe(
                integers -> LOGGER.info("Block of integers: {}", integers),
                throwable -> LOGGER.info("ERROR: ", throwable),
                () -> LOGGER.info("End"));
    }


    @Test
    @DisplayName("test reactive error testing")
    void testReactiveErrorTesting() {

        var mono = Flux.range(1, 5)
            .map(this::getAnInt)
            .reduce(Integer::sum);

        StepVerifier.create(mono)
            .expectErrorSatisfies(
                throwable -> {
                  Assertions.assertEquals(ArithmeticException.class, throwable.getClass());
                  Assertions.assertEquals("/ by zero", throwable.getMessage());
                })
            .verify();
    }

    @Test
    @DisplayName("test reactive error testing")
    void testReactiveErrorHandlingTesting() {

        var mono = Flux.range(1, 5)
            .flatMap(this::getMonoInt)
            .reduce(Integer::sum);

        StepVerifier.create(mono)
            .expectNext(13)
            .verifyComplete();
    }

    private Mono<Integer> getMonoInt(Integer integer) {
        return Mono.just(integer)
            .map(this::getAnInt)
            .onErrorResume(throwable ->
                {
                    LOGGER.error("ERROR in getMonoInt", throwable);
                    return Mono.empty();
                })
            .onErrorStop();
    }

    private int getAnInt(Integer integer) {
        return integer == 2 ? integer / 0 : integer;
    }

}
