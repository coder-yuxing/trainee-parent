package com.yuxing.trainee.kotlin

/**
 *
 * @author yuxing
 * @since 2021/12/31
 */
class Person(firstName: String) {

    private val name: String

    init {
        name = firstName
    }

    fun getName(): String {
        return this.name
    }
}