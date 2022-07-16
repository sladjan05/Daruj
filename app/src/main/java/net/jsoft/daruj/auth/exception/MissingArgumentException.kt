package net.jsoft.daruj.auth.exception

class MissingArgumentException : AuthException(
    "Not enough arguments to initialize the authenticator!"
)