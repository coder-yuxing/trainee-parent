package com.yuxing.trainee.kotlin

/**
 * Kotlin 中的类与继承
 *
 * @author yuxing
 * @since 2021/12/31
 */
fun main() {
    val car = Car("BYD")

    println("----------------------------------------")

    val car2 = Car("BYD", "hah")

    println("----------------------------------------")

    val birdWithName = Bird("小鸟")
    println("[name: ${birdWithName.getName()}]")

    println("----------------------------------------")

    val birdWithGenus = Bird("小鸟", "猛禽")
    println("[name: ${birdWithGenus.getName()}, genus: ${birdWithGenus.getGenus()}]")
}

/**
 * Kotlin中类由关键字 class 声明
 * e.g.
 * class Invoice {}
 */
class Car constructor(brand: String) /* constructor(brand: String) 为主构造函数 */ {
    private var brand: String = ""

    // 主构造函数不能包含任何代码，初始化代码可以放在 init 关键字作为前缀的代码块中
    // 在实例初始化期间，初始化块按照它们出现在类体中的顺序执行，与属性初始化器交织在一起
    init {
        this.brand = brand
        println("init first initializer blocks")
    }

    var secondProp = "Second property: ${brand.length}".also(::println)

    init {
        println("init second initializer blocks")
    }

    // 注意，初始化块的代码实际上会成为主构造函数的一部分。委托给主构造函数会作为次构造函数的第一条语句
    // 因此所有初始化块与属性初始化器中的代码都会在次构造函数体之前执行
    constructor(brand: String, secondProp: String): this(brand) {
        this.secondProp = secondProp
    }
}

// 如果一个类没有类体，则可以省略括号
class Empty

// 若类有一个主构造函数，每个次构造函数都需要委托给主构造函数
// 可以直接委托或者通过别的次构造函数间接委托
class Bird() {

    private var name: String = ""
    private var genus: String = ""

    // 直接委托给主构造函数
    constructor(name: String): this() {
        this.name = name
    }

    // 间接委托给主构造函数
    constructor(name: String, genus: String): this(name) {
        this.genus = genus
    }

    fun getName() = this.name
    fun getGenus() = this.genus

    fun setName(name: String) {
        this.name = name
    }

    fun setGenus(genus: String) {
        this.genus = genus
    }
}

// 类的继承
// Kotlin中所有类都有一个共同的超累 `Any`
// `Any` 提供三个方法：equals() hashCode() toString()
class Example // 隐式继承`Any`

// 默认情况下，Kotlin类都是最终的(final)：它们不能被继承
// 若要一个类可以被继承，可以使用 `open` 关键字标记
open class AnInheritableClass // 该类可被继承
class Derived : AnInheritableClass()

// 方法重写
// kotlin 对于可覆盖的成员以及覆盖以后的成员必须要显示修饰
open class Shape {
    // open 修饰 draw函数，标识改函数可以被重写
    open fun draw() {}

    // 对于fill() 函数，不允许在子类中被重写。因此子类中不能定义相同签名的函数
    fun fill() {}
}

class Circle(): Shape() {
    // 在重写的函数上，必须加上 override修饰符
    override fun draw() {
        super.draw()
    }
}

open class Rectrangle(): Shape() {
    // 标记为override的成员本身是开放的，也就是说它可以在子类中被覆盖。
    // 如果你想禁止再次覆盖，可以使用 final 关键字修饰：
    final override fun draw() {
        super.draw()
    }
}


// 抽象类
open class Polygon {
    open fun draw() {}
}

abstract class Rectangle1 : Polygon() {
    abstract override fun draw()
}















