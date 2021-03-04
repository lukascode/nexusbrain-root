package com.nexusbrain.app.flow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class FlowUtil {

    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Page.class, new PageAdapter());
        gson = builder.create();
    }

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

    private static class PageAdapter implements JsonDeserializer<Page> {
        @Override
        public Page deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext deserializationContext) throws JsonParseException {
            Type pageContentJavaType = ((ParameterizedType) type).getActualTypeArguments()[0];
            List content = deserializationContext.deserialize(jsonElement.getAsJsonObject().get("content"),
                    TypeToken.getParameterized(List.class, pageContentJavaType).getType());
            CustomPage customPage = deserializationContext.deserialize(jsonElement, CustomPage.class);
            PageRequest pageRequest = PageRequest.of(customPage.pageable.pageNumber, customPage.pageable.pageSize);
            return new PageImpl<>(content, pageRequest, customPage.totalElements);
        }

        private static class CustomPage {
            Pageable pageable;
            Integer totalElements;
            Integer numberOfElements;
        }

        private static class Pageable {
            Integer pageNumber;
            Integer pageSize;
        }
    }
}
