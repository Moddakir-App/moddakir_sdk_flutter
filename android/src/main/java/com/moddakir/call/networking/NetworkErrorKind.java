package com.moddakir.call.networking;

enum NetworkErrorKind {

    NETWORK,

    /**
     * A non-200 HTTP status code was received from the server.
     */
    HTTP,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}