import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course
{
    public static final Map<String, Course> courses = new HashMap<>();
    private String key;
    private String title;
    private String desc;
    private List<String> prereqs;

    public static void checkAllLinks()
    {
        for (Course c : courses.values())
        {
            c.checkLinks();
        }
    }

    private static String getKey(String rawTitle)
    {
        return Util.handle(rawTitle.split("\\. ")[0]);
    }

    private static String getTitle(String rawTitle)
    {
        return Util.handle(rawTitle.split("\\. ")[1]);
    }

    private static String getDesc(String rawDesc)
    {
        return Util.handle(rawDesc.split(" Prerequisite\\(s\\)\\: ")[0]);
    }

    public Course(String rawTitle, String rawDesc)
    {
        this(getKey(rawTitle), getTitle(rawTitle), getDesc(rawDesc));
    }

    public Course(String key, String title, String desc)
    {
        courses.put(key, this);
        this.key = key;
        this.title = title;
        this.desc = desc;
        prereqs = new ArrayList<>();
    }

    public void addPrereqKey(String key)
    {
        prereqs.add(key);
    }

    public void checkLinks()
    {
        for (String key : prereqs)
        {
            if (courses.get(key) == null)
            {
                System.err.println("Missing course: \"" + key + "\"");
            }
        }
    }
    
    public String toString()
    {
        String buffer = String.format("%s,\"%s\",\"%s\",", key, title, desc);
        for (String prereq : prereqs)
        {
            buffer += prereq + ",";
        }
        return buffer.substring(0, buffer.length() - 1);
    }
}