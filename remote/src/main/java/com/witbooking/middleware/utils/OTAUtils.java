package com.witbooking.middleware.utils;

import java.util.HashMap;
import java.util.Map;


//TODO: traducir
/**
 *
 * OTAUtils Class implements OTA utilities.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public final class OTAUtils {

    /*
     Card     Code Description
     AX	 American Express
     BC	 Bank Card
     BL	 Carte Bleu
     CB	 Carte Blanche
     DN	 Diners Club
     DS	 Discover Card
     EC	 Eurocard
     JC	 Japanesecredit
     MA	 Maestro (Switch)
     MC	 Master Card
     SO  Solo
     TP	 Universal Air Travel Card
     VI	 Visa
     VE	 Visa Electron
     */
    /**
     * Map how have the credit card name for each OTA credit card code.
     */
    private static final Map<String, String> CARD_TYPES;
    /**
     * Map how have the OTA credit card code for each credit card name.
     */
    private static final Map<String, String> OTA_CODES;

    static {
        String[][] types = {
            {"AX", "American Express"},
            {"BC", "Bank Card"},
            {"BL", "Carte Bleu"},
            {"CB", "Carte Blanche"},
            {"DN", "Diners Club"},
            {"DS", "Discover Card"},
            {"EC", "Eurocard"},
            {"JC", "Japanesecredit"},
            {"MA", "Maestro (Switch)"},
            {"MC", "Mastercard"},
            {"SO", "Solo"},
            {"TP", "Universal Air Travel Card"},
            {"VI", "Visa"},
            {"VE", "Visa Electron"},};
        CARD_TYPES = new HashMap<String, String>();
        OTA_CODES = new HashMap<String, String>();
        for (String[] item : types) {
            CARD_TYPES.put(item[0], item[1]);
        }
        for (String[] item : types) {
            OTA_CODES.put(item[1], item[0]);
        }
    }
    /**
     * Finds in {@link #CARD_TYPES} the OTA credit card code given.
     * @param key OTA credit card given to find.
     * @return Credit card name if <code>key</code> is found on {@link #CARD_TYPES}, otherwise <code>key</code>.
     */
    public static String getCardName(String key){
//        System.out.println("KEY: "+key+" containsKey: "+(CARD_TYPES.containsKey(key)));
        return (CARD_TYPES.containsKey(key)) ? CARD_TYPES.get(key) : key;
    }
    /**
     * Finds in {@link #OTA_CODES} the credit card name given.
     * @param key Credit card name given to find.
     * @return OTA credit card code if <code>key</code> is found on {@link #OTA_CODES}, otherwise <code>key</code>.
     */
    public static String getCardOTACode(String key){
//        System.out.println("KEY: "+key+" containsKey: "+(OTA_CODES.containsKey(key)));
        return (OTA_CODES.containsKey(key)) ? OTA_CODES.get(key) : key;
    }
}