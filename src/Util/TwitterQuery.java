package Util;

import twitter4j.Query;
import java.util.ArrayList;

public class TwitterQuery {

    public static Query queryIntervalloTemporaleConParoleChiavi(int giornoInizio, int meseInzio, int annoInizio, int giornoFine,
                               int meseFine, int annoFine, ArrayList<String> hashTag, ArrayList<String> frasi, String keyword){

        String queryString = "";

        //aggiunta parola chiave
        if (keyword != "") queryString = queryString + keyword + " ";

        //aggiunta frasi da contenere
        for (int i = 0; i < frasi.size(); i++){
            queryString = queryString + "\"" + frasi.get(i) + "\" OR ";
        }

        //aggiunta parole di cui almeno una deve essere contenuta
        for (int i = 0; i < hashTag.size(); i++){
            queryString = queryString + hashTag.get(i) + " OR ";
        }

        //aggiunta hashtag
        //essendo l'ultima porzion di query prima dell'intervallo temporale è necessario
        //sapere quando ci si trova sull'ultimo hashtag per non aggiungere "OR"
        for (int i = 0; i < hashTag.size(); i++){
            queryString = queryString + "#" + hashTag.get(i) + " ";
            if (i != hashTag.size()-1) queryString = queryString + "OR ";
        }

        //aggiunta intervallo temporale
        queryString = queryString + "since:" + annoInizio + "-" + meseInzio + "-" + giornoInizio +
                " until:" + annoFine + "-" + meseFine + "-" + giornoFine;
        System.out.println("Twitter Query: " + queryString + "\n");
        Query query = new Query(queryString);
        return query;
    }
}
