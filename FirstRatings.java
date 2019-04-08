
/** 
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(05042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to process the movie and ratings data and 
 * to answer questions about them.
 **/
 
 import edu.duke.*;
 import java.util.*;
 import org.apache.commons.csv.*;
  
public class FirstRatings {
    private ArrayList<Movie> moviesList;
    private ArrayList<Rater> ratersList;
    
    private ArrayList<Movie> loadMovies(String filename) {
        moviesList = new ArrayList<Movie>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser) {
            String anID = record.get("id");
            String aTitle = record.get("title");
            String aYear = record.get("year");
            String theGenres = record.get("genre");
            String aDirector = record.get("director");
            String aCountry = record.get("country");
            String aPoster = record.get("poster");
            int theMinutes = Integer.parseInt(record.get("minutes"));
            moviesList.add(new Movie (anID, aTitle, aYear,theGenres, aDirector,
                    aCountry, aPoster, theMinutes));
        };
        return moviesList;
    };
    
    private ArrayList<Rater> loadRaters(String filename) {
        ratersList = new ArrayList<Rater>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser) {
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            if(ratersList.isEmpty()) {
                Rater rater = new Rater(raterID);
                rater.addRating(movieID, rating);
                ratersList.add(rater);                
                System.out.println("I ran one time");
            };            
            //Run trough the list, if curr id doesn't exist in the list then add to the list.
            if(!checkID(ratersList, raterID)) {                
                Rater rater = new Rater(raterID);
                rater.addRating(movieID, rating);
                ratersList.add(rater);                
            };
        };
        return ratersList;
    };
    
    //Helper method for the loadRaters method.
    private boolean checkID(ArrayList<Rater> list, String raterID) {
        for(Rater r : list) {
            if(r.getID().equals(raterID)) return true;
        };        
        return false;
    };
    
    public void testLoadRaters() {
        ArrayList<Rater> myList = loadRaters("ratings_short.csv");
        System.out.println("There are " + myList.size() + " raters in this file.");
        //**Print all raters       
        for(Rater r : myList) {
          System.out.println(r.getID());  
        };
        
        //**Print number of ratings for a specified rater_id
        String designatedID = "2";
        int numrat = 0;
        for(Rater r : myList) {
          if(r.getID().equals(designatedID)) {
              numrat = r.numRatings();
            };
        };
        System.out.println("The RaterID " + designatedID + " has " + numrat + " ratings.");
    };
    
    
    
    public void testLoadMovies() {
        ArrayList<Movie> myList = loadMovies("ratedmovies_short.csv");
        System.out.println("There are " + myList.size() + " movies in this file.");
        //Print all movies        
        for(Movie m : myList) {
          System.out.println(m);  
        };
        
        //**Print comedy genre
        /*
        for(Movie m : myList) {
          int index = m.getGenres().indexOf("Comedy");          
          if(index != -1) {
              System.out.println(m);  
            };          
        };
        */
       
        //**Filter by time
        /*
        for(Movie m : myList) {
        int tgt = 150;
        int currMin = m.getMinutes();          
          if(currMin > tgt) {
              System.out.println(m);  
            };
        };
        */
       
        //**Max number of movies by director
        //Build a hashMap to store counts per Director name
        HashMap<String,Integer> myMap = new HashMap<String, Integer> ();
        for(Movie m : myList) {
            String s = m.getDirector();
            String[] directors = s.split(",");
            for(int n = 0; n  < directors.length; n++) directors[n] = directors[n].trim();
            for(String t : directors) {
               if(!myMap.containsKey(t)) {
                myMap.put(t,1);
                } else {
                myMap.put(t,myMap.get(t)+1);
                };
            };
        };
        //Find the maxCount in the HashMap
        int max = -1;
        int count = 0;
        String directorMaxCounts = null;
        for(String dir : myMap.keySet()) {
            count = myMap.get(dir);
            if(max < count) {
                max = count;
                directorMaxCounts = dir;
            };
        };
        //Print the director with the max Count (more movies directed)
        System.out.println("The director with most directed movies is " + directorMaxCounts + 
                                        ", directed " + max + " movies.");
        //Print out the hashMap
        /*for(String dir : myMap.keySet()) {
              System.out.println(dir + " : " + myMap.get(dir));  
            };*/
    }
}
