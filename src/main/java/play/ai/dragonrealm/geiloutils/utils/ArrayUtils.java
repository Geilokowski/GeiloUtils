package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	public static List<String> startsWith(List<String> initialList, String keyword){
		List<String> returnList = new ArrayList<String>();
		for(String s : initialList) {
			if(s.toLowerCase().startsWith(keyword.toLowerCase())) {
				returnList.add(s);
			}
		}
		
		return returnList;
	}
	public static String[] listToArray(List<String> initialList)
	  {
	    String[] tmp = new String[initialList.size()];
	    for (int i = 0; i < initialList.size(); i++) {
	      tmp[i] = ((String)initialList.get(i));
	    }
	    return tmp;
	  }
	  
	  public static List<String> arrayToList(String[] initialArray)
	  {
	    List<String> tmp = new ArrayList();
	    for (String bannedBlock : initialArray) {
	      tmp.add(bannedBlock);
	    }
	    return tmp;
	  }
	  
	  public static boolean isStringInList(List<String> list, String removeValue)
	  {
	    for (String item : list) {
	      if (item.startsWith(removeValue)) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  public static String getStringFromList(List<String> list, String searchString)
	  {
	    for (String item : list) {
	      if (item.startsWith(searchString)) {
	        return item;
	      }
	    }
	    return "ERROR:NOTFOUND";
	  }
	  
	  public static void removeStringFromList(List<String> list, String removeValue)
	  {
	    for (int i = 0; i < list.size(); i++) {
	      if (((String)list.get(i)).startsWith(removeValue)) {
	        list.remove(i);
	      }
	    }
	  }
}
