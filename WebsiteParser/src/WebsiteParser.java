import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteParser
{
    private static ArrayList<String> majors = new ArrayList<>();
    private static PrintWriter[] writers;
    
    public static void main(String[] args) throws IOException
    {
        // Get each major page by parsing main page
        URL url = new URL("http://catalog.udayton.edu/allcourses/");
        try
        {
            Document doc = Jsoup.parse(url, 3000);
            Elements majorSelector = doc.select(".sitemap a");
            for (Element major : majorSelector)
            {
                majors.add(major.attr("href").split("/")[2].toUpperCase());
                parseMajorPage("http://catalog.udayton.edu" + major.attr("href"));
            }

            // Now we are done parsing, so link everything
            if (Course.validate())
            {
                output();
            }
            
        } catch (IOException e)
        {
            System.out.println("Problem connecting to website.");
        }
    }

    public static void parseMajorPage(String urlString) throws IOException
    {
        System.out.println("Parsing: " + urlString);
        URL url = new URL(urlString);
        Document doc = Jsoup.parse(url, 3000);
        Elements courseBlocks = doc.select(".courseBlock");
        for (Element course : courseBlocks)
        {
            String title = course.select(".courseblocktitle strong").html();
            String desc = course.select(".courseblockdesc").html();
            // Create new course based on raw title and desc
            Course c = new Course(title, desc);

            // Figure out its prereqs
            Elements prereqLinks = course.select(".courseblockdesc").select("a");
            for (Element e : prereqLinks)
            {
                c.addPrereqKey(e.attr("title").replace(" ", " "));
            }
        }
    }

    /**
     * Generate csv file based on course info
     * @throws IOException 
     */
    public static void output() throws IOException
    {
        PrintWriter all = new PrintWriter(new File("../courses.csv"));
        writers = new PrintWriter[majors.size()];
        for (int i = 0; i < majors.size(); i++)
        {
            File f = new File("../" + majors.get(i) + "-courses.csv");
            writers[i] = new PrintWriter(f);
        }
        
        for (Course c : Course.courses.values())
        {
            for (int i = 0; i < majors.size(); i++)
            {
                if ((c.getKey() + c.getPrereqs("")).contains(majors.get(i)))
                {
                    writers[i].write(c + "\n");
                }
            }
            all.write(c + "\n");
        }
        
        for (int i = 0; i < writers.length; i++)
        {
            writers[i].close();            
        }
        all.close();
    }
}