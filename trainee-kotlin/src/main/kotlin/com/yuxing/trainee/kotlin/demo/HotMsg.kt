package com.yuxing.trainee.kotlin.demo

/**
 * 知乎热点消息
 *
 * @author yuxing
 * @since 2022/1/12
 */
class HotMsg() {

    var title: String = ""
    var excerpt: String = ""
    var thumbnail: String = ""
    var metrics: String = ""
    var link: String = ""

    constructor(title: String, excerpt: String, thumbnail: String, metrics: String, link: String): this() {
        this.title = title
        this.excerpt = excerpt
        this.thumbnail = thumbnail
        this.metrics = metrics
        this.link = link
    }
    override fun toString(): String {
        return "HotMsg(title='$title', excerpt='$excerpt', thumbnail='$thumbnail', metrics='$metrics', link='$link')"
    }


    /**
     * 标准化输出
     */
    fun standardPrint() {
        println("-----------------------------------------")
        println("title: $title")
        println("excerpt: $excerpt")
        println("thumbnail: $thumbnail")
        println("metrics: $metrics")
        println("link: $link")
        println("\n\r")
    }

}