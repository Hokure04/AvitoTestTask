package org.example.Utils;

public final class ResponseUtils {

    private static final String CREATE_ITEM_PREFIX = "Сохранили объявление - ";

    private ResponseUtils() {
    }

    public static String extractItemIdFromStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Поле status пустое");
        }

        if (!status.startsWith(CREATE_ITEM_PREFIX)) {
            throw new IllegalArgumentException("Поле status имеет неожиданный формат: " + status);
        }

        return status.replace(CREATE_ITEM_PREFIX, "").trim();
    }
}
