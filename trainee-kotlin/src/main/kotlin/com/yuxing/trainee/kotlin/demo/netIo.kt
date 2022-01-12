package com.yuxing.trainee.kotlin.demo

import cn.hutool.http.HttpUtil
import cn.hutool.json.JSON
import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import java.net.URL

/**
 * @author yuxing
 * @since 2022/1/12
 */
fun main() {
    val requestContext = HttpUtil.get("https://www.zhihu.com/api/v3/feed/topstory/hot-list-web?limit=50&desktop=true")
    val data = JSONObject(requestContext).getJSONArray("data")
    val iterator = data.jsonIter().iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        val target = next.getJSONObject("target")
        HotMsg(
            target.getJSONObject("title_area").getStr("text"),
            target.getJSONObject("excerpt_area").getStr("text"),
            target.getJSONObject("image_area").getStr("url"),
            target.getJSONObject("metrics_area").getStr("text"),
            target.getJSONObject("link").getStr("url")
        ).standardPrint()
    }
}
