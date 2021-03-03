package com.nexusbrain.app.flow;

import com.google.gson.Gson;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;

public final class FlowUtil {

    private static final Gson gson = new Gson();

    private FlowUtil() {
    }

    public static <T> Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<T>> parseAndGetResponse(ResponseEntity<String> response, Type responseType) {
        if (response.getStatusCode().is2xxSuccessful()) {
            T body = gson.fromJson(response.getBody(), responseType);
            ResponseEntity<T> parsedResponse = new ResponseEntity<>(body, response.getHeaders(), response.getStatusCode());
            return Either.right(parsedResponse);
        }
        ApiErrorDetails body = gson.fromJson(response.getBody(), ApiErrorDetails.class);
        ResponseEntity<ApiErrorDetails> parsedResponse = new ResponseEntity<>(body, response.getHeaders(), response.getStatusCode());
        return Either.left(parsedResponse);
    }

}
