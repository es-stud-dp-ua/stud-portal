package ua.dp.stud.StudPortalLib.util;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.join;

/**
 * @author Josby
 * @author Vladislav Pikus
 */
public class CustomFunctions {

    public static String truncateWords(String inputText, int wordsNumber) {
        String[] splitedInputText = inputText.split(" ");

        if (wordsNumber < 1 || wordsNumber > splitedInputText.length) {
            return inputText;
        }


        String[] subTextArray = new String[wordsNumber];
        System.arraycopy(splitedInputText, 0, subTextArray, 0, wordsNumber);

        return join(subTextArray, " ");
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
                    if (!parts[i].substring(parts[i].length() - 2, parts[i].length()).equals("/>")) {
                        if ((l += parts[i].length()) >= length) {
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
