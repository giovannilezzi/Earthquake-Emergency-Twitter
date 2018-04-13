import twitter4j.Query;

import java.util.ArrayList;

public class QueryCriteria {

    public static Query queryA(int giornoInizio, int meseInzio, int annoInizio, int giornoFine,
                               int meseFine, int annoFine, ArrayList<String> hashTag ){
        String queryString = null;
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
        Query query = new Query(queryString);
        return query;
    }
}
