package ua.dp.stud.StudPortalLib.util;

import java.util.regex.*;
import java.util.LinkedList;

public class Splitter {
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\s+");

    private Pattern pattern;
    private boolean keep_delimiters;

    public Splitter(Pattern pattern, boolean keep_delimiters) {
        this.pattern = pattern;
        this.keep_delimiters = keep_delimiters;
    }
    public Splitter(String pattern, boolean keep_delimiters) {
        this(Pattern.compile(pattern==null?"":pattern), keep_delimiters);
    }
    public Splitter(Pattern pattern) { this(pattern, true); }
    public Splitter(String pattern) { this(pattern, true); }
    public Splitter(boolean keep_delimiters) { this(DEFAULT_PATTERN, keep_delimiters); }
    public Splitter() { this(DEFAULT_PATTERN); }

    public String[] split(String text) {
        if (text == null) {
            text = "";
        }

        int last_match = 0;
        LinkedList<String> splitted = new LinkedList<String>();

        Matcher m = this.pattern.matcher(text);

        while (m.find()) {

            splitted.add(text.substring(last_match,m.start()));

            if (this.keep_delimiters) {
                splitted.add(m.group());
            }

            last_match = m.end();
        }

        splitted.add(text.substring(last_match));

        return splitted.toArray(new String[splitted.size()]);
    }
}