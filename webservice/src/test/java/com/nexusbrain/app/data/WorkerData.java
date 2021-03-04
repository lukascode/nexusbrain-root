package com.nexusbrain.app.data;

import com.nexusbrain.app.api.dto.request.AddWorkerRequest;
import com.nexusbrain.app.api.dto.request.SearchWorkersQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateWorkerRequest;

public class WorkerData {

    public static final String DEFAULT_FULL_NAME = "John Doe";
    public static final String DEFAULT_EMAIL = "john@example.com";
    public static final String DEFAULT_FULL_NAME_UPDATE = "Mark Owen";
    public static final String DEFAULT_EMAIL_UPDATE = "mark@example.com";
    public static final Long BAD_WORKER_ID = 554433L;
    public static final String BAD_EMAIL = "bad_email";
    public static final String BAD_FULL_NAME = "";
    public static final String DEFAULT_PHRASE = null;

    public static class AddWorkerRequestBuilder {
        private String fullName = DEFAULT_FULL_NAME;
        private String email = DEFAULT_EMAIL;

        public static AddWorkerRequestBuilder builder() {
            return new AddWorkerRequestBuilder();
        }

        public AddWorkerRequestBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public AddWorkerRequestBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AddWorkerRequest build() {
            AddWorkerRequest request = new AddWorkerRequest();
            request.setEmail(email);
            request.setFullName(fullName);
            return request;
        }
    }

    public static class UpdateWorkerRequestBuilder {
        private String fullName = DEFAULT_FULL_NAME_UPDATE;
        private String email = DEFAULT_EMAIL_UPDATE;

        public static UpdateWorkerRequestBuilder builder() {
            return new UpdateWorkerRequestBuilder();
        }

        public UpdateWorkerRequestBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UpdateWorkerRequestBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UpdateWorkerRequest build() {
            UpdateWorkerRequest request = new UpdateWorkerRequest();
            request.setEmail(email);
            request.setFullName(fullName);
            return request;
        }
    }

    public static class SearchWorkersQueryRequestBuilder {
        private String phrase = DEFAULT_PHRASE;

        public static SearchWorkersQueryRequestBuilder builder() {
            return new SearchWorkersQueryRequestBuilder();
        }

        public SearchWorkersQueryRequestBuilder withPhrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public SearchWorkersQueryRequest build() {
            SearchWorkersQueryRequest request = new SearchWorkersQueryRequest();
            request.setPhrase(phrase);
            return request;
        }
    }
}
