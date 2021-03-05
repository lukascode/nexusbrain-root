package com.nexusbrain.app.data;

import com.nexusbrain.app.api.dto.request.AddProjectRequest;
import com.nexusbrain.app.api.dto.request.SearchProjectsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateProjectRequest;

public class ProjectData {

    public static final String DEFAULT_NAME = "Test project";
    public static final String DEFAULT_DESCRIPTION = "Description";
    public static final String DEFAULT_NAME_UPDATE = "Test project updated";
    public static final String DEFAULT_DESCRIPTION_UPDATE = "Description update";
    public static final String BAD_NAME = "";
    public static final Long BAD_PROJECT_ID = 52345L;
    public static final String DEFAULT_PHRASE = null;

    public static class AddProjectRequestBuilder {
        private String name = DEFAULT_NAME;
        private String description = DEFAULT_DESCRIPTION;

        public static AddProjectRequestBuilder builder() {
            return new AddProjectRequestBuilder();
        }

        public AddProjectRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AddProjectRequestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public AddProjectRequest build() {
            AddProjectRequest request = new AddProjectRequest();
            request.setName(name);
            request.setDescription(description);
            return request;
        }
    }

    public static class UpdateProjectRequestBuilder {
        private String name = DEFAULT_NAME_UPDATE;
        private String description = DEFAULT_DESCRIPTION_UPDATE;

        public static UpdateProjectRequestBuilder builder() {
            return new UpdateProjectRequestBuilder();
        }

        public UpdateProjectRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UpdateProjectRequestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public UpdateProjectRequest build() {
            UpdateProjectRequest request = new UpdateProjectRequest();
            request.setName(name);
            request.setDescription(description);
            return request;
        }
    }

    public static class SearchProjectsQueryRequestBuilder {
        private String phrase = DEFAULT_PHRASE;

        public static SearchProjectsQueryRequestBuilder builder() {
            return new SearchProjectsQueryRequestBuilder();
        }

        public SearchProjectsQueryRequestBuilder withPhrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public SearchProjectsQueryRequest build() {
            SearchProjectsQueryRequest request = new SearchProjectsQueryRequest();
            request.setPhrase(phrase);
            return request;
        }
    }
}
