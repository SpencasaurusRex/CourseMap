import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteParser
{
    public static void main(String[] args) throws IOException
    {
        URL url = new URL("http://catalog.udayton.edu/allcourses/cps/");        
        Document doc = Jsoup.parse(url, 3000);
        Elements courseBlocks = doc.select(".courseBlock");
        for (Element course : courseBlocks)
        {
            String title = course.select(".courseblocktitle strong").html();
            String desc = course.select(".courseblockdesc").html();
            new Course(title, desc);
        }
    }
}
