
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
    
    public FirstRatings() {
        
    };
    
    public ArrayList<Movie> loadMovies(String filename) {
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
    
    public ArrayList<Rater> loadRaters(String filename) {
        ratersList = new ArrayList<Rater>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser) {
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            
            //to delete
            /*if(ratersList.isEmpty()) {
                Rater rater = new Rater(raterID);
                rater.addRating(movieID, rating);
                ratersList.add(rater);                
                System.out.println("I ran one time");
            };*/
            
            
            //Run trough the list, if curr id doesn't exist in the list then add to the list.
            if(!checkID(ratersList, raterID)) {                
                Rater rater = new Rater(raterID);
                rater.addRating(movieID, rating);
                ratersList.add(rater);                
            } else {
                int raterIndex = getRaterIndexByID(ratersList, raterID);
                ratersList.get(raterIndex).addRating(movieID, rating);
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
    
    //Helper/getter method for the loadRaters method. 
    private int getRaterIndexByID(ArrayList<Rater> list, String raterID) {
        for(int n = 0; n < list.size(); n++) {
            if(list.get(n).getID().equals(raterID)) {
              return n;  
            };
        };
        return -1;
    };
    
    
    
    public void testLoadRaters() {
        ArrayList<Rater> myList = loadRaters("ratings.csv");
        System.out.println("There are " + myList.size() + " raters in this file.");
        
        //printAllRaters(myList);        
        numberOfRatingsByRaterID(myList, "193"); //if you want to print more info consult this method.
        maxRatingsByRaterID (myList);
        numOfRatingsByMovieID(myList, "1798709");
        numMoviesRated(myList);
    };
    
    //Helper method for testLoadRaters and is being accesed by SecondRatings class
    public int numOfRatingsByMovieID(ArrayList<Rater> myList, String movieID) {
        int numOfRaters = 0;
        for (Rater r : myList) {
          ArrayList<String> itemsList = r.getItemsRated();
          for(String item : itemsList) {
             if(item.equals(movieID)) {
                 numOfRaters++;
                };
            };
        };
        System.out.println("The movieID " + movieID + " has " + numOfRaters + " raters.");
        return numOfRaters;
    };
    
    private int numMoviesRated(ArrayList<Rater> myList) {
        ArrayList<String> moviesID = new ArrayList<String>();
        for(Rater r : myList) {
            ArrayList<String> items = r.getItemsRated();
            for(String item : items) {
                if(!moviesID.contains(item)) {
                    moviesID.add(item);                    
                };
            };          
        };        
        System.out.println("These raters have rated " + moviesID.size() + " different movies.");       
        return moviesID.size();
    };
    
    //Helper method for testLoadRaters
    private int maxRatingsByRaterID (ArrayList<Rater> myList) {
        //**Print the maximum number of ratings by rater.
        HashMap<String,Integer> myMap = new HashMap<String,Integer>();
        int maxCount = 0;
        int sameMaxCount = 0;
        String maxCountRaterID = "None.";
        for(Rater r : myList) {
           String raterID = r.getID();
           int numOfRatings = numberOfRatingsByRaterID(myList, raterID);
           if(!myMap.containsKey(raterID)) {
                myMap.put(raterID, numOfRatings);            
           };
        };
        
        for(String ID : myMap.keySet()) {
          //System.out.println(ID + " " + myMap.get(ID));
          int temp = myMap.get(ID);
          if(temp > maxCount) {                  
              maxCount = temp;
              maxCountRaterID = ID;
          };
        };
        for(String ID : myMap.keySet()) {
          int checkHowMany = myMap.get(ID);
          if(checkHowMany == maxCount) {
              sameMaxCount++;
            };
        };
        System.out.println("Rater "+ maxCountRaterID +" has the maxCount of all, " + maxCount + " ratings.\n"
                        + "There is " + sameMaxCount + " raters with " + maxCount + " ratings.");
        return maxCount;
    };
    
    //Helper method for testLoadRaters
    private void printAllRaters(ArrayList<Rater> myList) {
        //**Print all raters       
        for(Rater r : myList) {
          System.out.println(r.getID());  
        };
    };
    
    //Helper method for testLoadRaters
    private int numberOfRatingsByRaterID(ArrayList<Rater> myList, String tgtID) {
       //**Print number of ratings for a specified rater_id
        int numrat = 0;
        ArrayList<String> items = null; //list of the movies rated
        ArrayList<Double> ratings = new ArrayList<Double>(); //list of the respective movies ratings
        for(Rater r : myList) {
          if(r.getID().equals(tgtID)) {
              numrat = r.numRatings();
              items = r.getItemsRated();
              for(String item : items) {
                  ratings.add(r.getRating(item));                  
                };
            };
        };
        //**For debug purpose
        System.out.println("The RaterID " + tgtID + " has " + numrat + " ratings.");
        //System.out.println(items);
        //System.out.println(ratings);
        return numrat;
    };
    
    
    
    public void testLoadMovies() {
        ArrayList<Movie> myList = loadMovies("ratedmoviesfull.csv");
        System.out.println("There are " + myList.size() + " movies in this file.");
        //Print all movies        
        /*for(Movie m : myList) {
          System.out.println(m);  
        };*/
        
        //**Print comedy genre        
        int countComedy = 0;
        for(Movie m : myList) {
          int index = m.getGenres().indexOf("Comedy");          
          if(index != -1) {
              //System.out.println(m);
              countComedy++;
            };            
        };
        System.out.println("There are " + countComedy + " comedy movies.");
       
        //**Filter by time
        int countFilteredByMinutes = 0;
        int tgt = 150;
        for(Movie m : myList) {        
        int currMin = m.getMinutes();          
          if(currMin > tgt) {
              //System.out.println(m);
              countFilteredByMinutes++;
            };
        };
        System.out.println("There are " + countFilteredByMinutes + " movies that runt below " 
                                + tgt + " minutes.");
       
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
