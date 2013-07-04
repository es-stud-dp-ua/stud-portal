package ua.dp.stud.StudPortalLib.util;

import static org.apache.commons.lang.StringUtils.join;

/**
 * Created with IntelliJ IDEA.
 * User: Josby
 * Date: 18.02.13
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */
public class CustomFunctions
{
    private CustomFunctions(){};
    public static String truncateWords(String inputText, int wordsNumber)
    {
        String[] splitedInputText = inputText.split(" ");

        if (wordsNumber < 1 || wordsNumber > splitedInputText.length)
        {
            return inputText;
        }


        String[] subTextArray= new String[wordsNumber];
        System.arraycopy(splitedInputText, 0, subTextArray, 0,wordsNumber );

        return join(subTextArray, " ");
    }

}
