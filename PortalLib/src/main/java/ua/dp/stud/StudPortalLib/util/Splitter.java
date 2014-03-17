package ua.dp.stud.StudPortalLib.util;

import java.util.List;
import java.util.regex.*;
import java.util.LinkedList;

public class Splitter {
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\s+");

    private Pattern pattern;
    private boolean keepDelimiters;

    public Splitter(Pattern pattern, boolean keepDelimiters) {
        this.pattern = pattern;
        this.keepDelimiters = keepDelimiters;
    }

    public Splitter(String pattern, boolean keepDelimiters) {
        this(Pattern.compile(pattern == null ? "" : pattern), keepDelimiters);
    }

    public Splitter(Pattern pattern) {
        this(pattern, true);
    }

    public Splitter(String pattern) {
        this(pattern, true);
    }

    public Splitter(boolean keepDelimiters) {
        this(DEFAULT_PATTERN, keepDelimiters);
    }

    public Splitter() {
        this(DEFAULT_PATTERN);
    }

    public String[] split(String toSplit) {
        String text = toSplit;
        if (text == null) {
            text = "";
        }
        int lastMatch = 0;
        List<String> splitted = new LinkedList<String>();
        Matcher matcher = this.pattern.matcher(text);
        while (matcher.find()) {
            splitted.add(text.substring(lastMatch, matcher.start()));
            if (this.keepDelimiters) {
                splitted.add(matcher.group());
            }
            lastMatch = matcher.end();
        }
        splitted.add(text.substring(lastMatch));
        return splitted.toArray(new String[splitted.size()]);
    }
}