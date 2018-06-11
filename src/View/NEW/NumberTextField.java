package View.NEW;


import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class NumberTextField extends TextField
{
    private static Pattern integerPattern = Pattern.compile("[0-9]*");

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return integerPattern.matcher(text).matches();
    }
}
