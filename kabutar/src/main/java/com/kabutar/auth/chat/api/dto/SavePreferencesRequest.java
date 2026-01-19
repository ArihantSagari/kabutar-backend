package com.kabutar.auth.chat.api.dto;

import java.util.List;

public class SavePreferencesRequest {
    private List<String> selectedTopics;

    public SavePreferencesRequest() {}

    public List<String> getSelectedTopics() {
        return selectedTopics;
    }

    public void setSelectedTopics(List<String> selectedTopics) {
        this.selectedTopics = selectedTopics;
    }
}
