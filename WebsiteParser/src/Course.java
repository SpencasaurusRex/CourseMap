import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Course
{
    public static final Map<String, Course> courses = new HashMap<>();
    private String key;
    private String title;
    private String desc;
    private List<String> prereqs;
    // TODO capture hours (need to capture ranges too, some courses have indeterminate hours)
    
    public static boolean validate()
    {
        System.out.println("Validating...");
        
        for (Course c : courses.values())
        {
            // Check that all links exist
            c.checkLinks();
        }
        
        return true;
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

    public String getKey()
    {
        return key;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getPrereqs(String delimiter)
    {
        String buffer = "";
        for (String prereq : prereqs)
        {
            buffer += prereq + delimiter;
        }
        return buffer.substring(0, buffer.length() - (buffer.length() > 0 ? delimiter.length() : 0));
    }

    public String prettyString()
    {
        return String.format("%s: %s\n%s\n%s", key, title, desc, getPrereqs(", "));
    }

    public String toString()
    {
        String buffer = String.format("%s,\"%s\",\"%s\",", key, title, desc);
        return buffer + getPrereqs(",");
    }
}