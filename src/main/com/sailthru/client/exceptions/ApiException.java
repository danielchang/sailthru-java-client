package com.sailthru.client.exceptions;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;
import org.apache.http.StatusLine;

/**
 * Handle API related Exceptions
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class ApiException extends IOException {

    private static Logger logger = Logger.getLogger(ApiException.class.getName());

    private HashMap<String, Object> jsonResponse;
    private int statusCode;

    public ApiException(int statusCode, String reason, Object jsonResponse) {
        super(reason);
        logger.info(String.format("%d: %s", statusCode, reason));
        this.jsonResponse = (HashMap<String, Object>)jsonResponse;
        this.statusCode = statusCode;
    }

    public HashMap<String, Object> getResponse() {
        return jsonResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static ApiException create(StatusLine statusLine, Object jsonResponse) {
        int statusCode = statusLine.getStatusCode();
        HashMap<String, Object> response = (HashMap<String, Object>)jsonResponse;
        return new ApiException(statusCode, response.get("errormsg").toString(), jsonResponse);
    }
}
