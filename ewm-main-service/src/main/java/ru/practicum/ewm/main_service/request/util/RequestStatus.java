package ru.practicum.ewm.main_service.request.util;

public enum RequestStatus {

    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELED;

    public static RequestStatus changeStringToState(String state) {
        try {
            return RequestStatus.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}