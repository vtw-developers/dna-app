package com.vtw.dna.apikey;

public class InvalidApiKeyException extends RuntimeException {

    private final String apiKey;

    public InvalidApiKeyException(String apiKey) {
        super(apiKey + " is not a valid api key");
        this.apiKey = apiKey;
    }
}
