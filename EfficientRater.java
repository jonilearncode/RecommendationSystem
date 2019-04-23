/** 
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(16042019)
 * 
 * CLASS DESCRIPTION
 * This class keeps track of one rater and all their ratings.
 * Re-factored to implement Rater Interface Class and HashMaps for efficiency.
 **/

import java.util.*;

public class EfficientRater implements Rater  {

    private String myID;    
    private HashMap<String,Rating> myMap;

    public EfficientRater(String id) {
        myID = id;
        myMap = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        myMap.put(item,new Rating(item,rating));
    }

    public boolean hasRating(String item) {        
        if(myMap.containsKey(item)) return true;        
        return false;
    }

    public String getID() {        
        return myID;
    }

    public double getRating(String item) {        
        if(myMap.containsKey(item)) {
          return myMap.get(item).getValue();  
        };
        return -1;
    }

    public int numRatings() {
        return myMap.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();       
        for(String ID : myMap.keySet()) {
          list.add(ID);  
        };        
        return list;
    }
}
