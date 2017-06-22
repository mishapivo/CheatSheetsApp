package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CheatsheetAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Cheat;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class DetailActivity extends SearchBarActivity {

    public static final String CHEATSHEET_SLUG_KEY = "cheatsheet_slug_key";

    @BindView(R.id.dataContainer)
    RecyclerView mCheatsheetContainer;

    protected CheatSheet mCheatSheet;

    protected CheatsheetAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null) {
            String slug = getIntent().getStringExtra(CHEATSHEET_SLUG_KEY);
            mCheatSheet = API.getCheatSheet(slug);
        }
        if (mCheatSheet == null) {
            finish();
            return;
        }

        mSearchView.setQueryHint("Search in "+mCheatSheet.title);
        mCheatsheetContainer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CheatsheetAdapter(this);
        mCheatsheetContainer.setAdapter(mAdapter);
        mAdapter.add(mCheatSheet.cheat_groups);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<CheatGroup> data = new ArrayList<>();
        boolean empty = (query == null || query.isEmpty());
        for(int i = 0; i < mCheatSheet.cheat_groups.size(); i++) {
            boolean matchesCategory = empty || mCheatSheet.cheat_groups.get(i).matchesString(query, false);
            if(matchesCategory || mCheatSheet.cheat_groups.get(i).matchesString(query, true)) {
                //TODO: expand
                CheatGroup cheatGroup = mCheatSheet.cheat_groups.get(i).cloneMe();
                data.add(cheatGroup);
                for(int j = cheatGroup.cheats.size() - 1; j >= 0 ; j--) {
                    Cheat cheatSheet = cheatGroup.cheats.get(j);
                    if(!(matchesCategory || cheatSheet.matchesString(query, false))) {
                        cheatGroup.cheats.remove(j);
                    }
                }
            } else {
                //TODO: collapse
            }
        }
        mAdapter.replaceAll(data);
        return true;
    }
}
