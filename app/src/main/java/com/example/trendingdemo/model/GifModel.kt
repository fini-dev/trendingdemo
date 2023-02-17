package com.example.trendingdemo.model

import com.google.gson.JsonObject

class GifModel(
    val id: String,
    val title: String,
    val url: String
) {

    var gifUrl = ""

    constructor(obj: JsonObject) : this(
        id = if (obj.has("id") && !obj.get("id").isJsonNull)
            obj.get("id").asString else "",
        title = if (obj.has("title") && !obj.get("title").isJsonNull)
            obj.get("title").asString else "",
        url = if (obj.has("url") && !obj.get("url").isJsonNull)
            obj.get("url").asString else "",
    ) {
        if (obj.has("images") && obj.get("images").isJsonObject) {
            val images = obj.get("images").asJsonObject
            if (images.has("original") && images.get("original").isJsonObject) {
                val original = images.get("original").asJsonObject
                if (original.has("url")) {
                    gifUrl = original.get("url").asString
                }
            }
        }
    }

}