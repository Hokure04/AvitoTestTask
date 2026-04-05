package org.example.Utils;

import java.util.Map;

public final class ItemDataProvider {

    private ItemDataProvider() {
    }

    public static String validItem(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.VALID_ITEM_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String missingSellerId() {
        return JsonDataReader.readJson(
                PathUtils.MISSING_SELLER_ID_JSON
        );
    }

    public static String invalidPriceType(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.INVALID_PRICE_TYPE_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String negativePrice(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.NEGATIVE_PRICE_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String overflowInteger(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.OVERFLOW_INTEGER_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String overflowLong(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.OVERFLOW_LONG_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String maxLong(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.MAX_LONG_JSON,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }

    public static String longName(int sellerId) {
        return JsonDataReader.readJsonAndReplace(
                PathUtils.LONG_NAME,
                Map.of("sellerId", String.valueOf(sellerId))
        );
    }
}