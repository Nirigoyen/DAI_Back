package com.moviezone.dai_api.utils;

public class ImageLinks {

    public static String imageTypeToLink(IMAGE_TYPE imageType, String imageURL){
        String baseLink = "https://image.tmdb.org/t/p/";
        switch (imageType){
            case POSTER -> {
                return baseLink + "w342" + imageURL;
            }
            case LOGO -> {
                return baseLink + "w185" + imageURL;
            }
            case PROFILE -> {
                return baseLink + "w185" + imageURL;
            }
            case BACKDROP -> {
                return "w780" + imageURL;
            }
        }
        return null;
    }
}
