
/** 
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(05042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to 
 **/

import java.util.*;

public class SecondRatings {
    public ArrayList<Movie> myMovies;
    public ArrayList<Rater> myRaters;
    public ArrayList<Rating> myRating;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings frat = new FirstRatings();
        myMovies = frat.loadMovies(moviefile);
        myRaters = frat.loadRaters(ratingsfile);
    };
    
    public String getTitle(String id) {
        for(Movie m : myMovies) {
          String temp = m.getTitle();
          if(m.getID().equals(id)) return temp;          
        };
        return "ID not found.";
    };
    
    public String getID(String title) {        
        for(Movie m : myMovies) {
            String temp = m.getTitle();
            if(temp.equals(title)) return m.getID();
        };        
        return "NO SUCH TITLE.";
    };
    
    public int getRaterSize() {
      return myRaters.size();  
    };
    
    public int getMovieSize() {
      return myMovies.size();  
    };
    
    public void test() {
        ArrayList<Rating> rat = getAverageRatings(3);
        for(Rating r : rat) {
          System.out.println(r);  
        };
        String id = "0068646";
        String title = getTitle(id);
        System.out.println("Title of " + id + " is: " + title);
        System.out.println("The movieID of " + title + " is: " + getID(title));
    };
    
    public ArrayList<Rating> getAverageRatings(int minRaters) {
        myRating = new ArrayList<Rating>();
        for(Movie m : myMovies) {
            String movieID = m.getID();
            double avg = getAverageByID(movieID, minRaters);
            myRating.add(new Rating(movieID, avg));
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
