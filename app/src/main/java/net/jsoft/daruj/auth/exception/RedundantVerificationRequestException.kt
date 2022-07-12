package net.jsoft.daruj.auth.exception

class RedundantVerificationRequestException : AuthException(
    "No need for SMS verification!"
)