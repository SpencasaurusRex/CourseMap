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
    private List<String> prereqKeys;
    private List<Course> prerequisites;
    
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
        return Util.handle(rawDesc.split(" Prerequisite(s): ")[0]);
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
        prereqKeys = new ArrayList<>();
    }
    
    public void addPrereqKey(String key)
    {
        prereqKeys.add(key);
    }
    
    public void link()
    {
        prerequisites = new ArrayList<>();
        for (String key : prereqKeys)
        {
            Course c = courses.get(key);
            if (c != null)
            {
                prerequisites.add(c);
            }
            else
            {
                System.err.println("Missing course: " + c);
            }
        }
    }
    
    public String toString()
    {
        return key + ": " + title;
    }
}
