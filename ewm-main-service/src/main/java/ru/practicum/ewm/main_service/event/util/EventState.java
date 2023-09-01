package ru.practicum.ewm.main_service.event.util;

public enum EventState {

    PENDING,

    PUBLISHED,

    CANCELED;

    public static EventState changeStringToState(String state) {
        try {
            return EventState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

}