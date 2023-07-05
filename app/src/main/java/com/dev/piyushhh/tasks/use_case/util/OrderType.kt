package com.dev.piyushhh.tasks.use_case.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}