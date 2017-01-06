import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
    private Util()
    {
    }

    public static String handle(String s)
    {
        // Replace &nbsp and &amp with a space, and & respectively
        s = s.replace("&nbsp;", " ").replace("&amp;", "&");
        // Remove every <br>
        s = s.replace("<br>", "");

        // Remove every link
        Pattern anchorTag = Pattern.compile("<a[^>]*>([\\w ]*)<\\/a>");
        Matcher anchorMatcher = anchorTag.matcher(s);
        while (anchorMatcher.find())
        {
            s = s.replace(anchorMatcher.group(0), anchorMatcher.group(1));
        }
        return s;
    }
}