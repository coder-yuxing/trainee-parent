package com.yuxing.trainee.kotlin

/**
 * kotlin 属性与字段
 *
 * @author yuxing
 * @since 2022/1/1
 */
fun main() {

}

// 声明属性
// Kotlin 类中的属性既可以用关键字var声明为可变的，也可以用关键字val声明为只读的
class Address {
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip: String = "123456"

    val isEmpty: Boolean
        get() = this.state == null

//    var stringRepresentation: String = ""
//        get() = this.toString()
//        set(value) {
//            field = value
//        }


}

// 要使用一个属性，只要用名字引用它即可
fun copyAddress(address: Address): Address {
    val result = Address()
    result.name = address.name
    result.street = address.street
    result.city = address.city
    result.state = address.state
    result.zip = address.zip
    return result
}



