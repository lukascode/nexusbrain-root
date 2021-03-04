package com.nexusbrain.app.data;

import com.nexusbrain.app.api.dto.request.AddProjectRequest;

public class ProjectData {

    public static final String DEFAULT_NAME = "Test project";
    public static final String DEFAULT_DESCRIPTION = "Description";
    public static final String BAD_NAME = "";

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
}
