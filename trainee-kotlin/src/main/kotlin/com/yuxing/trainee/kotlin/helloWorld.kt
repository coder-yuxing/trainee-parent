package com.yuxing.trainee.kotlin

/**
 * Kotlin hello world 程序
 *
 * Kotlin 程序的入口点位 main 函数
 *
 * @author yuxing
 * @since 2021/12/31
 */
fun main() {
    println("hello world")

    // sum 函数测试
    println("sum of 19 and 23 is ${sum(19, 23)}")

    // printSum 函数测试
    printSum(1, 2)

    // variable 函数测试
    variable()

    // 顶层变量测试
    incrementX()
    println(x)

    // Kotlin 创建对象 没有new关键字
    val person = Person("YuXing")
    println("hello, my name is ${person.getName()}")
}

// 求和
//fun sum(a: Int, b: Int): Int {
//    return a + b
//}
// 表达式作为函数体，返回值类型自动推断
fun sum(a: Int, b: Int) = a + b

// 函数返回无意义的值(无返回值)
//fun printSum(a: Int, b: Int): Unit {
//    println("sum of $a and $b is ${a + b}")
//}
// Unit 返回值可以省略
fun printSum(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}

// 定义变量
fun variable() {

    // 关键字 val 只能为变量赋值一次
    val a: Int = 1 // 立即赋值
    val b = 2 // 类型自动推断
    val c: Int // 若没有立即赋值，则变量类型不能忽略
    c = 3

    // 可重新赋值的变量需要使用 var 关键字声明
    var d = 5
    d += 1
}

// 顶层变量
val PI = 3.14
var x = 0
fun incrementX() {
    x += 1
}