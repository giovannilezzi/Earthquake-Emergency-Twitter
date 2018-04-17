package Util;

import twitter4j.Query;
import java.util.ArrayList;

public class TwitterQuery {

    public static Query queryIntervalloTemporaleConParoleChiavi(int giornoInizio, int meseInzio, int annoInizio, int giornoFine,
                               int meseFine, int annoFine, ArrayList<String> hashTag, ArrayList<String> frasi, String filterWord){
        String queryString = filterWord + " ";
        for (int i = 0; i < frasi.size(); i++){
            if (i != frasi.size()-1) queryString = queryString + "\"\"" + frasi.get(i) + "\"\" OR ";
            else queryString = queryString + "\"\"" + frasi.get(i) + "\"\" ";
        }
        for (int i = 0; i < hashTag.size(); i++){
            if (i != hashTag.size()-1) queryString = queryString + hashTag.get(i) + " OR ";
            else queryString = queryString + hashTag.get(i) + " ";
        }
        for (int i = 0; i < hashTag.size(); i++){
            if (i != hashTag.size()-1) queryString = queryString + "#" + hashTag.get(i) + " OR ";
            else queryString = queryString + hashTag.get(i) + " ";
        }
        queryString = queryString + "since:" + annoInizio + "-" + meseInzio + "-" + giornoInizio +
                " until:" + annoFine + "-" + meseFine + "-" + giornoFine;
        System.out.println(queryString);
        Query query = new Query(queryString);
        return query;
    }
}
