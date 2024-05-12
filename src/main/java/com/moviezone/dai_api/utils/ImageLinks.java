package com.moviezone.dai_api.utils;

public class ImageLinks {

    public static String imageTypeToLink(IMAGE_TYPE imageType){
        String baseLink = "https://image.tmdb.org/t/p/";
        switch (imageType){
            case POSTER -> {
                return baseLink + "w342";
            }
            case LOGO -> {
                return baseLink + "w185";
            }
            case PROFILE -> {
                return baseLink + "w185";
            }
            case BACKDROP -> {
                return "w780";
            }
        }
        return null;
    }
}
