import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteParser
{
    public static void main(String[] args) throws IOException
    {
        // Get each major page by parsing main page
        URL url = new URL("http://catalog.udayton.edu/allcourses/");
        try
        {
            Document doc = Jsoup.parse(url, 3000);
            Elements majors = doc.select(".sitemap a");
            for (Element major : majors)
            {
                parseMajorPage("http://catalog.udayton.edu" + major.attr("href"));
            }

            // Now we are done parsing, so link everything
            Course.checkAllLinks();

            System.out.println("All courses linked");

            output();
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
                c.addPrereqKey(e.attr("title"));
            }
        }
    }

    /**
     * Generate csv file based on course info
     * 
     * @throws FileNotFoundException
     */
    public static void output() throws FileNotFoundException
    {
        File f = new File("courses.csv");
        PrintWriter out = new PrintWriter(f);
        for (Course c : Course.courses.values())
        {
            out.write(c + "\n");
        }
        out.close();
    }
}
