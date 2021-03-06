package net.egordmitriev.cheatsheets.pojo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Category extends MatchableModel {
	public boolean temp;
	public int id;
	public String title;
	public String description;
	public CheatSheet[] cheat_sheets;
	
	public Category(int id, String title, String description, CheatSheet[] cheat_sheets) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.cheat_sheets = cheat_sheets;
	}
	
	public Category(String title, String description, CheatSheet[] cheat_sheets) {
		this.title = title;
		this.description = description;
		this.cheat_sheets = cheat_sheets;
	}
	
	@Override
	protected List<String> getSearchableStrings(boolean recursive) {
		List<String> ret = new ArrayList<String>() {{
			add(title);
			add(description);
		}};
		if (recursive) {
			for (CheatSheet cheatSheet : cheat_sheets) {
				ret.addAll(cheatSheet.getSearchableStrings(false));
			}
		}
		return ret;
	}
	
	public CheatSheet getCheatSheet(int id) {
		for (CheatSheet cheatSheet : cheat_sheets) {
			if (cheatSheet.id == id) {
				return cheatSheet;
			}
		}
		return null;
	}
	
	public static CheatSheet getCheatSheet(int id, Category[] categories) {
		for (Category category : categories) {
			CheatSheet temp = category.getCheatSheet(id);
			if (temp != null) return temp;
		}
		return null;
	}
	
	public boolean filter(String query, boolean recursive) {
		if(TextUtils.isEmpty(query) || matchesString(query, false)) {
			return true;
		}
		if(recursive) {
			for (CheatSheet cheatSheet : cheat_sheets) {
				if (cheatSheet.filter(query)) return true;
			}
		}
		return false;
	}
}
