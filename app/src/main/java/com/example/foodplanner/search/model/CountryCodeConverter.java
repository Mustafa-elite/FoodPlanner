package com.example.foodplanner.search.model;
import java.util.HashMap;
import java.util.Map;

public class CountryCodeConverter {
    private static final Map<String, String> COUNTRY_CODES = new HashMap<>();

    static {
        COUNTRY_CODES.put("American", "US");
        COUNTRY_CODES.put("British", "GB");
        COUNTRY_CODES.put("Canadian", "CA");
        COUNTRY_CODES.put("Chinese", "CN");
        COUNTRY_CODES.put("Croatian", "HR");
        COUNTRY_CODES.put("Dutch", "NL");
        COUNTRY_CODES.put("Egyptian", "EG");
        COUNTRY_CODES.put("Filipino", "PH");
        COUNTRY_CODES.put("French", "FR");
        COUNTRY_CODES.put("Greek", "GR");
        COUNTRY_CODES.put("Indian", "IN");
        COUNTRY_CODES.put("Irish", "IE");
        COUNTRY_CODES.put("Italian", "IT");
        COUNTRY_CODES.put("Jamaican", "JM");
        COUNTRY_CODES.put("Japanese", "JP");
        COUNTRY_CODES.put("Kenyan", "KE");
        COUNTRY_CODES.put("Malaysian", "MY");
        COUNTRY_CODES.put("Mexican", "MX");
        COUNTRY_CODES.put("Moroccan", "MA");
        COUNTRY_CODES.put("Norwegian", "NO");
        COUNTRY_CODES.put("Polish", "PL");
        COUNTRY_CODES.put("Portuguese", "PT");
        COUNTRY_CODES.put("Russian", "RU");
        COUNTRY_CODES.put("Spanish", "ES");
        COUNTRY_CODES.put("Thai", "TH");
        COUNTRY_CODES.put("Tunisian", "TN");
        COUNTRY_CODES.put("Turkish", "TR");
        COUNTRY_CODES.put("Ukrainian", "UA");
        COUNTRY_CODES.put("Uruguayan", "UY");
        COUNTRY_CODES.put("Vietnamese", "VN");
    }

    public static String getCountryCode(String countryName) {
        return COUNTRY_CODES.getOrDefault(countryName, "UNKNOWN");
    }
}

