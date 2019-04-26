import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Scraper {


    public static void main (String[]args){

        //Create a new client and log us in!
        //TODO: Put ur credentials here:
        System.out.println("Nombre: ");
        Scanner entradaEscaner = new Scanner(System.in);
        String nombre = entradaEscaner.nextLine();
        System.out.println("Password: ");
        String pass = entradaEscaner.nextLine();
        MitraClient client = new MitraClient(nombre, pass);
        client.login();

        //Let's scrape our messages, information behind a login.
        String page = client.get("https://mitra.upc.es/SIA/netarea.inici");

        //"table.taula" selects all divs with the class name "taula", that's info is stored.
        Elements messages = Jsoup.parse(page).select("table.taula").select("tr");

        //For each message in messages, let's print out message and a new line.

        ArrayList<String> listAsignatura = new ArrayList<>();
        ArrayList<String> liststNombreGeneralNota= new ArrayList<>();
        ArrayList<String> listNombreExamen = new ArrayList<>();
        ArrayList<String> liststPorcentaje = new ArrayList<>();
        ArrayList<String> listNota = new ArrayList<>();

        for (Element message : messages) {
            if(!message.text().contains("-")){
                listAsignatura.add(message.text());
            }else{
                //Control laboratori - P1 3.6% 7.9
                //split1 = [control lab, p1 3.6% 7.9]
                //split1[1].(" ") : [p1, 3.6%, 7.9]
                String[] split_one = message.text().split("-",2);
                String[] split_two = split_one[1].split(" ");

                liststNombreGeneralNota.add(split_one[0]);
                if (split_two.length == 3){
                    listNombreExamen.add(split_two[0]);
                    liststPorcentaje.add(split_two[1]);
                    listNota.add(split_two[2]);
                }else{
                    //Todo: Mejorar el nombre del examen, puede mostrar un nombre raro.
                    listNombreExamen.add(split_two[split_two.length - 3]);
                    liststPorcentaje.add(split_two[split_two.length - 2]);
                    listNota.add(split_two[split_two.length - 1]);
                }
            }
            System.out.println(message.text());
        }
        System.out.println(listAsignatura);
        System.out.println(listNombreExamen);
        System.out.println(liststNombreGeneralNota);
        System.out.println(liststPorcentaje);
        System.out.println(listNota);
    }
}
