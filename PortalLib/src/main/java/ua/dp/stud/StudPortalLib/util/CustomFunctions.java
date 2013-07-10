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

    public static String getCreationDate(Date date) {
        StringBuilder sb = new StringBuilder();
        Date pubDate = date;
        Date now = DateTime.now().toDate();
        Integer year = now.getYear() - pubDate.getYear();
        if (year == 0) {
            Integer day = now.getDate() - pubDate.getDate();
            if (day == 0) {
                Integer hours = now.getHours() - pubDate.getHours();
                if (hours == 0) {
                    Integer minutes = now.getMinutes() - pubDate.getMinutes();
                    if (minutes == 0) {
                        Integer seconds = now.getSeconds() - pubDate.getSeconds();
                        if (seconds < 10) {
                            sb.append("только что");
                        } else {
                            sb.append(seconds).append(" секунд назад");
                        }
                    } else {
                        sb.append(minutes).append(" минут назад");
                    }
                } else {
                    if (hours > 0 && hours < 4) {
                        sb.append(hours).append(" час(а) назад");
                    } else {
                        sb.append("сегодня в ")
                                .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
                    }
                }
            } else {
                if (day == 1) {
                    sb.append("вчера в ")
                            .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(pubDate));
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
                    sb.append(dateFormat.format(pubDate));
                }
            }
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            sb.append(dateFormat.format(pubDate));
        }
        return sb.toString();
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
