package com.mvinuesa.demo.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class ReactiveHandleErrorTest {


    @Test
    @DisplayName("test reactive error")
    void testReactiveError() {

        Flux.range(1, 5).map(integer -> integer == 2 ? integer / 0 : integer)
            .reduce(Integer::sum)
            .subscribe(
                integers -> LOGGER.info("Block of integers: {}", integers),
                throwable -> LOGGER.info("ERROR: ", throwable),
                () -> LOGGER.info("End"));
    }


    @Test
    @DisplayName("test reactive error testing")
    void testReactiveErrorTesting() {

    }

    @Test
    @DisplayName("test reactive error handling")
    void testReactiveErrorHandlingTesting() {


    }


}
