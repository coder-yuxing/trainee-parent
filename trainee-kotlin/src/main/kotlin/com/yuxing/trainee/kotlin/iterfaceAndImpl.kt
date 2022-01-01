package com.yuxing.trainee.kotlin

/**
 * Kotlin中的接口
 *
 * @author yuxing
 * @since 2022/1/1
 */
fun main() {
    val child = Child()
    println("prop: " + child.prop)
    println("propWithImplements: " + child.propWithImplements)

    println("Is 7 even? - ${isEven.accept(7)}")
}

// 定义接口
interface MyInterface {
    // 接口中的属性要么是抽象的，要么提供访问器实现
    val prop: Int // 抽象的
    val propWithImplements: String
        get() = "foo"

    fun bar()

    fun foo() {
        // 可选方法体
    }
}

// 实现接口
class Child : MyInterface {
    override val prop: Int
        get() = 1

    override val propWithImplements: String
        get() = "impl do something"

    override fun bar() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        // do something
        super.foo()
    }

}
// 函数式(SAM) 接口
// 只有一个抽象方法的接口成为函数式接口或SAM(单一抽象方法)接口
// 函数式接口可以有多个非抽象成员，但只能有一个抽象成员
fun interface KRunnable {
    fun invoke()
}

fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

//val isEven = object: IntPredicate {
//    override fun accept(i: Int): Boolean {
//        return i % 2 == 0
//    }
//}
// 上述代码可使用 SAM转换，改为如下等效代码
val isEven = IntPredicate {  it % 2 == 0 }
