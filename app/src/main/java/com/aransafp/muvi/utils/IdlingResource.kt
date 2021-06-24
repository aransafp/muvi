package com.aransafp.muvi.utils

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {
    private const val RESOURCE: String = "GLOBAL"
    val esporessoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        esporessoTestIdlingResource.increment()
    }

    fun decerement() {
        esporessoTestIdlingResource.decrement()
    }
}