package com.example.foodplanner.search.model;
import java.util.HashMap;
import java.util.Map;

public class CountryCodeConverter {
    private static final Map<String, String> COUNTRY_CODES = new HashMap<>();
    private static final Map<String, String> COUNTRY_TO_NATIONALITY = new HashMap<>();

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

        // Mapping country name to nationality
        COUNTRY_TO_NATIONALITY.put("United States", "American");
        COUNTRY_TO_NATIONALITY.put("United Kingdom", "British");
        COUNTRY_TO_NATIONALITY.put("Canada", "Canadian");
        COUNTRY_TO_NATIONALITY.put("China", "Chinese");
        COUNTRY_TO_NATIONALITY.put("Croatia", "Croatian");
        COUNTRY_TO_NATIONALITY.put("Netherlands", "Dutch");
        COUNTRY_TO_NATIONALITY.put("Egypt", "Egyptian");
        COUNTRY_TO_NATIONALITY.put("Philippines", "Filipino");
        COUNTRY_TO_NATIONALITY.put("France", "French");
        COUNTRY_TO_NATIONALITY.put("Greece", "Greek");
        COUNTRY_TO_NATIONALITY.put("India", "Indian");
        COUNTRY_TO_NATIONALITY.put("Ireland", "Irish");
        COUNTRY_TO_NATIONALITY.put("Italy", "Italian");
        COUNTRY_TO_NATIONALITY.put("Jamaica", "Jamaican");
        COUNTRY_TO_NATIONALITY.put("Japan", "Japanese");
        COUNTRY_TO_NATIONALITY.put("Kenya", "Kenyan");
        COUNTRY_TO_NATIONALITY.put("Malaysia", "Malaysian");
        COUNTRY_TO_NATIONALITY.put("Mexico", "Mexican");
        COUNTRY_TO_NATIONALITY.put("Morocco", "Moroccan");
        COUNTRY_TO_NATIONALITY.put("Norway", "Norwegian");
        COUNTRY_TO_NATIONALITY.put("Poland", "Polish");
        COUNTRY_TO_NATIONALITY.put("Portugal", "Portuguese");
        COUNTRY_TO_NATIONALITY.put("Russia", "Russian");
        COUNTRY_TO_NATIONALITY.put("Spain", "Spanish");
        COUNTRY_TO_NATIONALITY.put("Thailand", "Thai");
        COUNTRY_TO_NATIONALITY.put("Tunisia", "Tunisian");
        COUNTRY_TO_NATIONALITY.put("Turkey", "Turkish");
        COUNTRY_TO_NATIONALITY.put("Ukraine", "Ukrainian");
        COUNTRY_TO_NATIONALITY.put("Uruguay", "Uruguayan");
        COUNTRY_TO_NATIONALITY.put("Vietnam", "Vietnamese");
    }

    public static String getCountryCode(String nationality) {
        return COUNTRY_CODES.getOrDefault(nationality, "UNKNOWN");
    }

    public static String getNationality(String countryName) {
        return COUNTRY_TO_NATIONALITY.getOrDefault(countryName, "Unknown");
    }
}
