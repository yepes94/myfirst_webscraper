import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class Scraper {


    public static void main (String[]args){

        //Create a new client and log us in!
        //TODO: Put ur credentials here:
        MitraClient client = new MitraClient("", "");
        client.login();

        //Let's scrape our messages, information behind a login.
        String page = client.get("https://mitra.upc.es/SIA/netarea.inici");

        //"table.taula" selects all divs with the class name "taula", that's info is stored.
        Elements messages = Jsoup.parse(page).select("table.taula").select("tr");

        //For each message in messages, let's print out message and a new line.

        for (Element message : messages) {
            System.out.println(message.text() + "\n");
        }
    }
}
