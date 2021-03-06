package net.egordmitriev.cheatsheets.pojo;

import java.util.List;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public abstract class MatchableModel extends Model {
    protected abstract List<String> getSearchableStrings(boolean recursive);

    public boolean matchesString(String rawQuery, boolean recursive) {
        String[] qs = rawQuery.toLowerCase().split("\\s+");
        List<String> contents = getSearchableStrings(recursive);
        boolean ret = true;
        for (String query : qs) {
            ret = false;
            for (String content : contents) {
                if(content == null) continue;
                if(content.toLowerCase().contains(query)) {
                    ret = true;
                    break;
                }
            }
            if(!ret) break;
        }
        return ret;
    }
}
