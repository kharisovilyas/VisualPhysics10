package com.example.visualphysics10.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceHolderItem> ITEMS = new ArrayList<PlaceHolderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceHolderItem> ITEM_MAP = new HashMap<String, PlaceHolderItem>();

    private static final int COUNT = 11;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceHolderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceHolderItem createPlaceholderItem(int position) {
        return new PlaceHolderItem(String.valueOf(position), switchLesson(position) + position, makeDetails(position));
    }

    private static String switchLesson(int position) {
        switch (position){
            case 1:
                return "Item 1";
            case 2:
                return "Item 2";
            default: return "";
        }
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceHolderItem {
        public final String id;
        public final String title;
        public final String details;

        public PlaceHolderItem(String id, String content, String details) {
            this.id = id;
            this.title = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}