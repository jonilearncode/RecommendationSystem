
/** 
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(17042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to build the myRaters and myRating lists. Uppon the build of myRating list,
 * the Moviedatabase is automatically initialized.
 **/

import java.util.*;

public class ThirdRatings {   
    private ArrayList<Rater> myRaters;    
    private ArrayList<Rating> myRating;
    
    public ThirdRatings() {
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings frat = new FirstRatings();
        myRaters = frat.loadRaters(ratingsfile);
    };
    
    public int getRaterSize() {
      return myRaters.size();  
    };
    
    public void test() {
        ArrayList<Rating> rat = getAverageRatings(10);
        for(Rating r : rat) {
          System.out.println(r);  
        };
        String id = "0068646";
        //String title = getTitle(id);
        /*System.out.println("Title of " + id + " is: " + title);
        System.out.println("The movieID of " + title + " is: " + getID(title));*/    
    };
    
    //This method will build the myRating list and initialized the MovieDatabase.
    public ArrayList<Rating> getAverageRatings(int minRaters) {
        myRating = new ArrayList<Rating>();
        //Get the list of moviesID's by passing the database by the specific filter.
        Filter tf = new TrueFilter();
        ArrayList<String> moviesID = MovieDatabase.filterBy(tf);
        for(String movieID : moviesID) {            
            double avg = getAverageByID(movieID, minRaters);
            if(avg > 0.0) myRating.add(new Rating(movieID, avg)); //this if intends to add by default only rated movies.
        };
        return myRating;
    };
    
    //This method will build the myRating list and initialized the MovieDatabase applying an Filter f to the movieDatabase call.
    public ArrayList<Rating> getAverageRatingsByFilter(int minRaters, Filter filterCriteria) {
       myRating = new ArrayList<Rating>();
       //Get the list of moviesID's by passing the database by the specific filter.       
       ArrayList<String> moviesID = MovieDatabase.filterBy(filterCriteria);
       for(String movieID : moviesID) {            
           double avg = getAverageByID(movieID, minRaters);
           if(avg > 0.0) myRating.add(new Rating(movieID, avg)); //this if intends to add by default only rated movies.
       };
       return myRating; 
    };    
    
    //Helper methods
    public double getAverageByID(String movieID, int minRaters) {
        FirstRatings frat = new FirstRatings();
        double result = 0.0;
        int size = getRaterSize() - 1;
        int movieRaters = frat.numOfRatingsByMovieID(myRaters, movieID);
        if(minRaters <= movieRaters) {
            for(Rater rater : myRaters) {
                if(rater.hasRating(movieID)) {
                  result += rater.getRating(movieID);  
                };
            };
            result /= movieRaters;
        };
        return result;
    };
    
}
