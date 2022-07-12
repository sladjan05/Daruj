package net.jsoft.daruj.auth.exception

class WrongCodeException : AuthException(
    "Supplied verification code is not valid!"
)