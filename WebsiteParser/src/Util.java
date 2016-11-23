public class Util
{
    private Util()
    {
    }

    public static String handle(String s)
    {
        return s.replace("&nbsp;", " ").replace("&amp;", "&");
    }
}