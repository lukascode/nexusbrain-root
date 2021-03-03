package com.nexusbrain.app.assertion;

import com.nexusbrain.app.api.error.ApiErrorDetails;
import org.assertj.core.api.Assertions;
import org.springframework.stereotype.Component;

@Component
public class ErrorAssertions {

    public ErrorAssertion assertThat(ApiErrorDetails errorDetails) {
        return new ErrorAssertion(errorDetails);
    }

    public static class ErrorAssertion {
        private final ApiErrorDetails errorDetails;

        public ErrorAssertion(ApiErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
        }

        public ErrorAssertion hasMessage(String message) {
            Assertions.assertThat(errorDetails.getMessage()).isEqualTo(message);
            return this;
        }

        public ErrorAssertion hasDescription(String description) {
            Assertions.assertThat(errorDetails.getDescription()).isEqualTo(description);
            return this;
        }

        public ErrorAssertion hasEventId() {
            Assertions.assertThat(errorDetails.getEventId()).isNotNull();
            return this;
        }
    }
}
