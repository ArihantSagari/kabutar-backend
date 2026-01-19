package com.kabutar.auth.chat.api;

import org.springframework.stereotype.Service;

import com.kabutar.auth.chat.api.dto.PreferencesResponse;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PreferencesService {

    // username -> selected topics
    private final Map<String, Set<String>> selectedTopicsByUser = new ConcurrentHashMap<>();

    // username -> custom topics
    private final Map<String, Set<String>> customTopicsByUser = new ConcurrentHashMap<>();

    private static final List<String> DEFAULT_TOPICS = List.of(
            "Movies","Music","Cricket","Football","Gaming","Anime","Tech","Startups","Coding","Java",
            "Spring Boot","Next.js","AI","Space","Fitness","Gym","Diet","Travel","Food","Photography",
            "Books","Poetry","Relationships","College","Jobs","Interview Prep","Memes","Finance",
            "Stock Market","Crypto","Business","Design","UI/UX","Art","Drawing","Cars","Bikes",
            "Science","History","Politics","Psychology","Meditation","Spirituality","Pets","Nature",
            "Fashion","Shopping","Languages","K-pop"
    );

    public PreferencesResponse getPreferences(String username) {
        List<String> available = new ArrayList<>(DEFAULT_TOPICS);

        // add user custom topics into available list
        Set<String> custom = customTopicsByUser.getOrDefault(username, Set.of());
        for (String t : custom) {
            if (!available.contains(t)) available.add(t);
        }

        List<String> selected = new ArrayList<>(selectedTopicsByUser.getOrDefault(username, Set.of()));
        selected.sort(String::compareToIgnoreCase);

        return new PreferencesResponse(available, selected);
    }

    public void saveSelectedTopics(String username, List<String> selectedTopics) {
        if (selectedTopics == null) selectedTopics = List.of();

        // max 10
        List<String> trimmed = selectedTopics.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .distinct()
                .limit(10)
                .toList();

        selectedTopicsByUser.put(username, new HashSet<>(trimmed));
    }

    public void addCustomTopic(String username, String topic) {
        if (topic == null) return;
        String t = topic.trim();
        if (t.isBlank()) return;

        customTopicsByUser.computeIfAbsent(username, k -> ConcurrentHashMap.newKeySet()).add(t);

        // auto-select custom topic
        Set<String> selected = selectedTopicsByUser.computeIfAbsent(username, k -> ConcurrentHashMap.newKeySet());
        if (selected.size() < 10) selected.add(t);
    }
}
