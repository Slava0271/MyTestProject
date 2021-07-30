package com.example.mytestproject.enums

enum class SnackBarErrors(val error: Int) {
    EMPTY_NAME(1),
    EMPTY_LASTNAME(2),
    EMPTY_LOGIN(3),
    EMPTY_PASSWORD(4),
    USER_NOT_EXISTS(5),
    PASSWORD_WRONG(6),
    PASSWORD_MISMATCH(7)
}