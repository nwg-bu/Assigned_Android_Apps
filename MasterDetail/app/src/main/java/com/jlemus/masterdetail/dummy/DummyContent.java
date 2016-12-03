package com.jlemus.masterdetail.dummy;

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
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final List<String> Characters = new ArrayList<String>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 6;

    public static final String walterWhiteText = "Walter White is a bad ass chemist.";
    public static final String jesseText = "Jesse Pinkman is a meth dealer with a heart, bitch.";
    public static final String goodmanText = "Saul Goodman is a CRIMNAL lawyer.";

    static {
        addItem(new DummyItem("1", "Walter White",
                "https://goo.gl/Jo09aE", "heisenberg", walterWhiteText));
        addItem(new DummyItem("2", "Jesse Pinkman",
                "https://goo.gl/1Ozq28", "pinkman", jesseText));
        addItem(new DummyItem("3", "Saul Goodman",
                "https://goo.gl/pZ3KfM", "goodman", goodmanText));
        }


    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
//        public final String id;
//        public final String content;
//        public final String details;

        public String id;
        public String website_name;
        public String website_url;
        public String imageName;
        public String info;

        public DummyItem(String id, String website_name,
                         String website_url, String imageName, String info)
        {
            this.id = id;
            this.website_name = website_name;
            this.website_url = website_url;
            this.imageName = imageName;
            this.info = info;
        }

        @Override
        public String toString() {
            return website_name;
        }




//        public DummyItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }
//
//
//
//        @Override
//        public String toString() {
//            return content;
//        }
    }
}
