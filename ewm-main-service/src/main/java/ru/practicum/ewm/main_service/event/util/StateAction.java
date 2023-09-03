package ru.practicum.ewm.main_service.event.util;

public enum StateAction {

    SEND_TO_REVIEW,

    CANCEL_REVIEW;


    public static StateAction changeStringToState(String state) {
        try {
            return StateAction.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        }

    }
}