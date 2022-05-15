package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

	public static List<String> startsWith(List<String> initialList, String keyword){
		List<String> returnList = new ArrayList<String>();
		if(keyword.equals("")) return initialList;

		for(String s : initialList) {
			if(s.toLowerCase().startsWith(keyword.toLowerCase())) {
				returnList.add(s);
			}
		}

		return returnList;
	}
}
