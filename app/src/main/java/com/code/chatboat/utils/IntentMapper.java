package com.code.chatboat.utils;

public interface IntentMapper<INTENT, T> {
    INTENT mapToIntent(T data);
    T mapFromIntent(INTENT data);
}
