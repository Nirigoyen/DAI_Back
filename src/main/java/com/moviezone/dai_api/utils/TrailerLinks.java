package com.moviezone.dai_api.utils;

import com.google.gson.JsonObject;

public class TrailerLinks {

    public static String generateYTLink(JsonObject movie){
        return "https://www.youtube.com/watch?v=" + movie.get("videos").getAsJsonObject().get("results").getAsJsonArray().get(0)
                .getAsJsonObject().get("key").getAsString();
    }
}
