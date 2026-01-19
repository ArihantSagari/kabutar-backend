package com.kabutar.auth.chat.api.dto;

import java.util.List;

public class PreferencesResponse {
    private List<String> availableTopics;
    private List<String> selectedTopics;

    public PreferencesResponse() {}

    public PreferencesResponse(List<String> availableTopics, List<String> selectedTopics) {
        this.availableTopics = availableTopics;
        this.selectedTopics = selectedTopics;
    }

    public List<String> getAvailableTopics() {
        return availableTopics;
    }

    public void setAvailableTopics(List<String> availableTopics) {
        this.availableTopics = availableTopics;
    }

    public List<String> getSelectedTopics() {
        return selectedTopics;
    }

    public void setSelectedTopics(List<String> selectedTopics) {
        this.selectedTopics = selectedTopics;
    }
}
