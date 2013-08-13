package ua.dp.stud.StudPortalLib.util;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private static final Integer TOTAL_MS_PER_DAY = 1000 * 60 * 60 * 24;
    private static final Integer TOTAL_MS_PER_HOURS = 1000 * 60 * 60;
    private static final Integer TOTAL_MS_PER_MIN = 1000 * 60;
    private static final Integer TOTAL_MS = 1000;
    private static final DateFormat HOURS_FORMAT = new SimpleDateFormat("HH:mm");
    private static final DateFormat DAY_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM");
    private static final Integer FOUR = 4;
    private static final Integer TEN = 10;
    private static final Integer TWENTY_FOUR = 24;
    private static final Integer SIXTY = 60;

    public static String getCreationDate(Date date) {
        StringBuilder outDate = new StringBuilder();
        Date pubDate = date;
        Date now = DateTime.now().toDate();
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(now);
        Calendar pubDateCal = Calendar.getInstance();
        pubDateCal.setTime(date);
        Long difference = now.getTime() - date.getTime();
        Long seconds = difference / TOTAL_MS;
        if (seconds < TEN) {
            outDate.append("только что");
        } else {
            if (seconds < SIXTY) {
                outDate.append(seconds).append(" секунд назад");
            } else {
                Long minutes = difference / TOTAL_MS_PER_MIN;
                if (minutes < SIXTY) {
                    outDate.append(minutes).append(" минут назад");
                } else {
                    Long hours = difference / TOTAL_MS_PER_HOURS;
                    if (hours < FOUR) {
                        outDate.append(hours).append(" час(а) назад");
                    } else {
                        if (hours < TWENTY_FOUR) {
                            Integer day = nowCal.get(Calendar.DAY_OF_MONTH) - pubDateCal.get(Calendar.DAY_OF_MONTH);
                            if (day == 0) {
                                outDate.append("сегодня в ")
                                    .append(HOURS_FORMAT.format(pubDate));
                            } else {
                                outDate.append("вчера в ")
                                        .append(HOURS_FORMAT.format(pubDate));
                            }
                        } else {
                            Long day = difference / TOTAL_MS_PER_DAY;
                            if (day == 1) {
                                outDate.append("вчера в ")
                                        .append(HOURS_FORMAT.format(pubDate));
                            } else {
                                Integer year = nowCal.get(Calendar.YEAR) - pubDateCal.get(Calendar.YEAR);
                                if (year == 0) {
                                    outDate.append(DATE_FORMAT.format(pubDate)).append(" в ")
                                            .append(HOURS_FORMAT.format(pubDate));
                                } else {
                                    outDate.append(DAY_FORMAT.format(pubDate)).append(" в ")
                                            .append(HOURS_FORMAT.format(pubDate));
                                }
                            }
                        }
                    }
                }
            }
        }
        return outDate.toString();
    }

    public static String getEventsDate(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        Integer date1 = startCal.get(Calendar.DAY_OF_MONTH);
        Integer date2 = endCal.get(Calendar.DAY_OF_MONTH);
        result.append(date1.toString());
        if (date1 < date2) {
            result.append(" - ").append(date2.toString());
        }
        Integer month = endCal.get(Calendar.MONTH);
        result.append(".");
        if(month < TEN) {
            result.append("0");
        }
        result.append(endCal.get(Calendar.MONTH));
        return result.toString();
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
