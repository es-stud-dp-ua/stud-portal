package ua.dp.stud.StudPortalLib.util;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.join;

/**
 * @author Josby
 * @author Vladislav Pikus
 */
public final class CustomFunctions {

    private CustomFunctions() {
    }

    public static String truncateWords(String inputText, int wordsNumber) {
        String[] splitedInputText = inputText.split(" ");

        if (wordsNumber < 1 || wordsNumber > splitedInputText.length) {
            return inputText;
        }

        String[] subTextArray = new String[wordsNumber];
        System.arraycopy(splitedInputText, 0, subTextArray, 0, wordsNumber);

        return join(subTextArray, " ");
    }

    private static final Integer TOTAL_MS_PER_HOURS = 1000 * 60 * 60;
    private static final Integer TOTAL_MS_PER_MIN = 1000 * 60;
    private static final Integer TOTAL_MS = 1000;

    public static String getCreationDate(Date date) {
        StringBuilder outDate = new StringBuilder();
        Date pubDate = date;
        Date now = DateTime.now().toDate();
        Long difference = now.getTime() - date.getTime();
        if (difference < 0) {
            throw  new IllegalArgumentException();
        }
        Integer year = now.getYear() - pubDate.getYear();
        if (year == 0) {
            Integer day = now.getDate() - pubDate.getDate();
            if (day == 0) {
                Long hours = difference / TOTAL_MS_PER_HOURS;
                if (hours == 0) {
                    Long minutes = difference / TOTAL_MS_PER_MIN;
                    if (minutes == 0) {
                        Long seconds = difference / TOTAL_MS;
                        if (seconds < 10) {
                            outDate.append("только что");
                        } else {
                            outDate.append(seconds).append(" секунд назад");
                        }
                    } else {
                        outDate.append(minutes).append(" минут назад");
                    }
                } else {
                    if (hours > 0 && hours < 4) {
                        outDate.append(hours).append(" час(а) назад");
                    } else {
                        outDate.append("сегодня в ")
                                .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
                    }
                }
            } else {
                if (day == 1) {
                    outDate.append("вчера в ")
                            .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM");
                    outDate.append(dateFormat.format(pubDate)).append(" в ")
                            .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
                }
            }
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            outDate.append(dateFormat.format(pubDate)).append(" в ")
                    .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
        }
        return outDate.toString();
    }

    public static String truncateHtml(String html, Integer length) {
        Stack<String> stack = new Stack<String>();
        StringBuilder out = new StringBuilder();
        String buffer;
        Pattern re = Pattern.compile("(<.+?>|&#?\\\\w+;)");
        Splitter splitter = new Splitter(re);
        String[] parts = splitter.split(html);
        for (int i = 0, l = 0; i < parts.length; i++) {
            if ((i & 1) != 0) {
                buffer = parts[i].substring(0, 2);
                if (buffer.equals("</") || buffer.equals("[/")) {
                    stack.pop();
                } else {
                    if (parts[i].charAt(0) == '&') {
                        l++;
                    } else {
                        if (parts[i].length() >= 2) {
                            buffer = parts[i].substring(parts[i].length() - 2, parts[i].length());
                            if (!buffer.equals("/>") || !buffer.equals("/]")) {
                                stack.push(parts[i]);
                            }
                        }
                    }
                }
                out.append(parts[i]);
            } else {
                if (parts[i].length() >= 2) {
                    buffer = parts[i].substring(parts[i].length() - 2, parts[i].length());
                    if (!buffer.equals("/>")) {
                        l += parts[i].length();
                        if (l >= length) {
                            out.append(parts[i].substring(0, (length - l + parts[i].length())));
                            break;
                        } else {
                            out.append(parts[i]);
                        }
                    }
                }
            }
        }
        while (!stack.empty()) {
            StringTokenizer tokenizer = new StringTokenizer(stack.pop().substring(1), "\\t>|( )");
            if (tokenizer.hasMoreElements()) {
                out.append("</").append(tokenizer.nextElement()).append('>');
            }
        }
        return out.toString();
    }
}
