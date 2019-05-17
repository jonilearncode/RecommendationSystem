/**
 *AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(29042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to build the myRaters and myRating lists trough the static call from databases
 * for the Movies and the Raters.
 * The MovieDatabase and RaterDatabase are initialized upon MovieRunnerSimilarRatings Class. 
 */

import java.util.*;
import java.util.ArrayList;

public class FourthRatings {
    
    public FourthRatings () {        
    };
    
    public double getAverageByID(String movieID, int minRaters) {
        FirstRatings frat = new FirstRatings();
        double result = 0.0;
        int size = RaterDatabase.size() - 1;
        int movieRaters = frat.numOfRatingsByMovieID(RaterDatabase.getRaters(), movieID);
        if(minRaters <= movieRaters) {
            for(Rater rater : RaterDatabase.getRaters()) {
                if(rater.hasRating(movieID)) {
                  result += rater.getRating(movieID);  
                };
            };
            result /= movieRaters;
        };
        return result;
    };
    
    //This method will build the myRating list and initialized the MovieDatabase applying an Filter f to the movieDatabase call.
    public ArrayList<Rating> getAverageRatingsByFilter(int minRaters, Filter filterCriteria) {
       ArrayList<Rating> myRating = new ArrayList<Rating>();
       //Get the list of moviesID's by passing the database by the specific filter.       
       ArrayList<String> moviesID = MovieDatabase.filterBy(filterCriteria);
       for(String movieID : moviesID) {            
           double avg = getAverageByID(movieID, minRaters);
           if(avg > 0.0) myRating.add(new Rating(movieID, avg)); //this if intends to add by default only rated movies.
       };
       return myRating; 
    }; 
    
    //This method will build the myRating list and initialized the MovieDatabase.
    public ArrayList<Rating> getAverageRatings(int minRaters) {
        ArrayList<Rating> myRating = new ArrayList<Rating>();
        //Get the list of moviesID's by passing the database by the specific filter.
        Filter tf = new TrueFilter();
        ArrayList<String> moviesID = MovieDatabase.filterBy(tf);
        for(String movieID : moviesID) {            
            double avg = getAverageByID(movieID, minRaters);
            if(avg > 0.0) myRating.add(new Rating(movieID, avg)); //this if intends to add by default only rated movies.
        };
        Collections.sort(myRating);
        Collections.reverse(myRating);
        return myRating;
    };
    
    //This method return the movies ratings with weighted avg ratings using only the top similar raters          
    public ArrayList<Rating> getSimilarRatings(String ID, int numSimilarRaters, int minRaters) {
        ArrayList<Rating> moviesWeightedAvg = new ArrayList<Rating>();
        ArrayList<Rating> similarities = getSimilarities(ID);
        Filter tf = new TrueFilter();
        ArrayList <String> myMovies = MovieDatabase.filterBy(tf);
                
        //Filter similarities depending on numSimilarRaters and minRaters
        for(String movieID : myMovies) {
            double weightedAvg = 0;
            int numOfRatings = 0;
            for(int n = 0; n < numSimilarRaters; n++) {
              Rating rat = similarities.get(n);
              String raterID = rat.getItem();
              Rater similarRater = RaterDatabase.getRater(raterID);
              if(similarRater.hasRating(movieID)) {
                 numOfRatings++;
                 double rating = similarRater.getRating(movieID);
                 double w = rat.getValue();
                 weightedAvg += rating * w;
                };
            };
            if(numOfRatings >= minRaters) {
                double wAvg = weightedAvg/numOfRatings;
                moviesWeightedAvg.add(new Rating(movieID, wAvg));
            };
        };
        Collections.sort(moviesWeightedAvg);
        Collections.reverse(moviesWeightedAvg);
        return moviesWeightedAvg;
    };
       
    //Updates the getSimilarRatings with FilterCriteria
    public ArrayList<Rating> getSimilarRatingsByFilter(String ID, int numSimilarRaters, int minRaters, Filter filterCriteria) {
        ArrayList<Rating> moviesWeightedAvg = new ArrayList<Rating>();
        ArrayList<Rating> similarities = getSimilarities(ID);
        ArrayList <String> myMovies = MovieDatabase.filterBy(filterCriteria);
                
        //Filter similarities depending on numSimilarRaters and minRaters
        for(String movieID : myMovies) {
            double weightedAvg = 0;
            int numOfRatings = 0;
            for(int n = 0; n < numSimilarRaters; n++) {
              Rating rat = similarities.get(n);
              String raterID = rat.getItem();
              Rater similarRater = RaterDatabase.getRater(raterID);
              if(similarRater.hasRating(movieID)) {
                 numOfRatings++;
                 double rating = similarRater.getRating(movieID);
                 double w = rat.getValue();
                 weightedAvg += rating * w;
                };
            };
            if(numOfRatings >= minRaters) {
                double wAvg = weightedAvg/numOfRatings;
                moviesWeightedAvg.add(new Rating(movieID, wAvg));
            };
        };
        Collections.sort(moviesWeightedAvg);
        Collections.reverse(moviesWeightedAvg);
        return moviesWeightedAvg;
    };
    
    //**Helper   
    //Filters the raters from similarities<Rating> from the RatersDatabase
    private ArrayList<Rater> getRatersFromDatabase(ArrayList<Rating> similarities) {
        ArrayList<Rater> databaseRaters = RaterDatabase.getRaters();
        ArrayList<Rater> myRaters = new ArrayList<Rater>();
        for(Rating rating : similarities) {
            String ID = rating.getItem();
            for(Rater rater : databaseRaters) {
                String raterID = rater.getID();
                if(raterID.equals(ID)) {
                  myRaters.add(rater);  
                };
            };
        };
        return myRaters;
    };
    
    //Checks if a rater has rated a certain movie
    private boolean checkIfMovieWasRated(String raterID, String movieID) {
        Rater r = RaterDatabase.getRater(raterID);
        ArrayList<String> moviesRated = r.getItemsRated();
        //System.out.println("boolean check..List of movies rated by " + raterID + ":\n" + moviesRated);
        for(String m : moviesRated) {
          if(m.equals(movieID)) return true;
          //System.out.println("The movie: " + movieID + " has been rated by " + raterID);
        };
        return false;
    };    
    
    //Translates a rating from 0 to 10 to -5 to 5 and return dot product of the ratings of movies that
    //they both rated
    private double dotProduct(Rater me, Rater r) {
        double dotProduct = 0.0;
        //Get the movies rated by me Rater
        ArrayList<String> meItemsRated = me.getItemsRated();
        //Check wich of those has been rated by r Rater also
        for(String itemRated : meItemsRated) {
            //if has rated by r Rater also get the rating of each rater and dot product
            if(r.hasRating(itemRated)) {
              double scaledMeRating = me.getRating(itemRated) -5;
              double scaledRRating = r.getRating(itemRated) -5;
              dotProduct += scaledMeRating * scaledRRating;
              //System.out.println("Dot Product for " + me.getID() + " with " + r.getID() + ":\n" + 
              //                  "me: " + scaledMeRating + ", r: " + scaledRRating + ", dotProduct: " + dotProduct);
            };            
        };
        return dotProduct;
    };
    
    //Computes a similarity rating for each rater in the RaterDatabase
    //to see how similar they are to the Rater ID
    private ArrayList<Rating> getSimilarities(String ID) {        
        ArrayList<Rating> similarRatings = new ArrayList<Rating>();
        //HashMap<Double,Rater> mapSimilarityRating = new HashMap<Double,Rater>();
        Rater raterID = RaterDatabase.getRater(ID);
        //System.out.println("webRaterId is " + raterID.getID());
        for(Rater rater : RaterDatabase.getRaters()) {
          if(!rater.getID().equals(ID)) {
            similarRatings.add(new Rating(rater.getID(), dotProduct(raterID, rater)));
            };
        };        
        Collections.sort(similarRatings);
        Collections.reverse(similarRatings);
        //if(similarRatings.size() == 0) System.out.println("Error: similarRatings array size is 0");
        return similarRatings;        
    };
}
