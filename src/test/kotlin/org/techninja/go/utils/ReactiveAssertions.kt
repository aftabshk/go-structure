package org.techninja.go.utils

import org.reactivestreams.Publisher
import reactor.test.StepVerifier

fun  <T> assertNextWith(publisher: Publisher<T>, assertions: (t: T) -> Unit) {
    StepVerifier.create(publisher)
        .consumeNextWith(assertions)
        .verifyComplete()
}