package de.eurogo.exam.util;

/**
 * Error codes enumeration
 *
 * Created by haykhayryan on 8/31/15.
 */
public enum Errors {
    INTERNAL_ERROR(501), //Internal processing error

    ARGUMENT_ERROR(404), //Provided illegal argument value error

    HTTP_ERROR(408); //HTTP status code error

    int errorCode;

    Errors(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.valueOf(errorCode);
    }
}
