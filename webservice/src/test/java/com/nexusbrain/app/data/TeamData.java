package com.nexusbrain.app.data;

import com.nexusbrain.app.api.dto.request.AddTeamRequest;
import com.nexusbrain.app.api.dto.request.SearchTeamsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateTeamRequest;

public class TeamData {

    public static final String DEFAULT_NAME = "Test project";
    public static final String DEFAULT_DESCRIPTION = "Description";
    public static final String DEFAULT_NAME_UPDATE = "Test project updated";
    public static final String DEFAULT_DESCRIPTION_UPDATE = "Description updated";
    public static final String BAD_NAME = "";
    public static final Long BAD_TEAM_ID = 52435L;
    public static final String DEFAULT_PHRASE = null;

    public static class AddTeamRequestBuilder {
        private String name = DEFAULT_NAME;
        private String description = DEFAULT_DESCRIPTION;

        public static AddTeamRequestBuilder builder() {
            return new AddTeamRequestBuilder();
        }

        public AddTeamRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AddTeamRequestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public AddTeamRequest build() {
            AddTeamRequest request = new AddTeamRequest();
            request.setName(name);
            request.setDescription(description);
            return request;
        }
    }

    public static class UpdateTeamRequestBuilder {
        private String name = DEFAULT_NAME_UPDATE;
        private String description = DEFAULT_DESCRIPTION_UPDATE;

        public static UpdateTeamRequestBuilder builder() {
            return new UpdateTeamRequestBuilder();
        }

        public UpdateTeamRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UpdateTeamRequestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public UpdateTeamRequest build() {
            UpdateTeamRequest request = new UpdateTeamRequest();
            request.setName(name);
            request.setDescription(description);
            return request;
        }
    }

    public static class SearchTeamsQueryRequestBuilder {
        private String phrase = DEFAULT_PHRASE;

        public static SearchTeamsQueryRequestBuilder builder() {
            return new SearchTeamsQueryRequestBuilder();
        }

        public SearchTeamsQueryRequestBuilder withPhrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public SearchTeamsQueryRequest build() {
            SearchTeamsQueryRequest request = new SearchTeamsQueryRequest();
            request.setPhrase(phrase);
            return request;
        }
    }
}
