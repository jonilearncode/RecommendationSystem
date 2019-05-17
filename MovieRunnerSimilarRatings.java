
/**
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(29042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to find the weigthed average rating of movies using different filters and two databases: Movie and Rater.
 */

import java.util.*;

public class MovieRunnerSimilarRatings {
    
    FourthRatings fourat;
    
    private void initialize() {
      fourat = new FourthRatings(); 
      MovieDatabase.initialize("ratedmoviesfull.csv");
      RaterDatabase.initialize("ratings.csv");
      System.out.println("myRaters loaded." + "\nMovieDatabase loaded." +
                        "\nNº of raters: " + RaterDatabase.size() + 
                        "\nNº of movies: " + MovieDatabase.size());
    };
    
     public void printAverageRatings(int minRaters) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      ArrayList<Rating> myRating = fourat.getAverageRatings(minRaters);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      printResults(myRating, minRaters, true, false, false, false, false);
    };
    

    public void printAverageRatingsByYearAndGenre(int minRaters, int year, String genre) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter yaf = new YearAfterFilter(year);
      Filter gf = new GenreFilter(genre);
      AllFilters combinedFilter = new AllFilters();
      combinedFilter.addFilter(yaf);
      combinedFilter.addFilter(gf);
      ArrayList<Rating> myRating = fourat.getAverageRatingsByFilter(minRaters, combinedFilter);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      System.out.println("\n"+"____________Movies sorted by average ratings (min. " + minRaters + " raters)____________");
      for(int n = 0; n < myRating.size(); n++) {
          double avgRating = myRating.get(n).getValue();                 
          String id = myRating.get(n).getItem();
          String title = MovieDatabase.getTitle(id);
          int numYear = MovieDatabase.getYear(id);
          String genres = MovieDatabase.getGenres(id);
          System.out.println(avgRating + " " + numYear + " " + title + "\n  " + genres);
        };

    };
    
    public void printSimilarRatings(int minRaters) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      ArrayList<Rating> myRating = fourat.getSimilarRatings("71", 20, minRaters);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      //sortByAvgRating(myRating);
      Collections.sort(myRating);
      Collections.reverse(myRating);
      //Print the new list properly sorted
      printResults(myRating, minRaters, true, false, false, false, false);
    };
    
    public void printSimilarRatingsByGenre(int minRaters, String genre) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter gf = new GenreFilter(genre);
      ArrayList<Rating> myRating = fourat.getSimilarRatingsByFilter("964", 20, minRaters, gf);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      //sortByAvgRating(myRating);
      Collections.sort(myRating);
      Collections.reverse(myRating);
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, false, true, false, false);
    };
    
    public void printSimilarRatingsByDirector(int minRaters, String director) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter df = new DirectorsFilter(director);
      ArrayList<Rating> myRating = fourat.getSimilarRatingsByFilter("120", 10, minRaters, df);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      //sortByAvgRating(myRating);
      Collections.sort(myRating);
      Collections.reverse(myRating);
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, false, false, false, true);
    };
    
    public void printSimilarRatingsByGenreAndMinutes(int minRaters, String genre, int minMinutes, int maxMinutes) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter gf = new GenreFilter(genre);
      Filter mf = new MinuteFilter(minMinutes, maxMinutes);
      AllFilters combinedFilter = new AllFilters();
      combinedFilter.addFilter(gf);
      combinedFilter.addFilter(mf);
      ArrayList<Rating> myRating = fourat.getSimilarRatingsByFilter("168", 10, minRaters, combinedFilter);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      //sortByAvgRating(myRating);
      Collections.sort(myRating);
      Collections.reverse(myRating);
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, false, false, false, true);
    };
    
    public void printSimilarRatingsByYearAfterAndMinutes(int minRaters, int year, int minMinutes, int maxMinutes) {
      initialize();
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter yaf = new YearAfterFilter(year);
      Filter mf = new MinuteFilter(minMinutes, maxMinutes);
      AllFilters combinedFilter = new AllFilters();
      combinedFilter.addFilter(yaf);
      combinedFilter.addFilter(mf);
      ArrayList<Rating> myRating = fourat.getSimilarRatingsByFilter("314", 10, minRaters, combinedFilter);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      //sortByAvgRating(myRating);
      Collections.sort(myRating);
      Collections.reverse(myRating);
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, false, false, false, true);
    };
    
    //**Helper method for print the results (arrayList)
    private void printResults(ArrayList<Rating> myRating, int minRaters,boolean standard, boolean year, boolean genre, boolean time, boolean directors) {
      System.out.println("\n"+"____________Movies sorted by average ratings (min. " + minRaters + " raters)____________");
      for(int n = 0; n < myRating.size(); n++) {
          double avgRating = myRating.get(n).getValue();                 
          String id = myRating.get(n).getItem();
          String title = MovieDatabase.getTitle(id);
          int numYear = MovieDatabase.getYear(id);
          String genres = MovieDatabase.getGenres(id);
          int minutes = MovieDatabase.getMinutes(id);
          String drectrs = MovieDatabase.getDirector(id);
          if(standard) System.out.println(avgRating + " " + title);          
          if(year)  System.out.println(avgRating + " " + numYear + " " + title);
          if(genre) System.out.println(avgRating + " " + title + "\n    " + genres);
          if(time)  System.out.println(avgRating + " Time: " + minutes + " " + title);
          if(directors) System.out.println(avgRating + " " + title + "\n    " + drectrs);       
      };   
    };
    
    //**Helper method for sorting an ArrayList by avg rating
    private void sortByAvgRating(ArrayList<Rating> myRating) {       
        int size = myRating.size();
        for(int n = 1; n < size -1; n++) {
            //This method will check if it's needed to recall again onePassBubbleSort again.
            if(!checkInSortedOrder(myRating)) {
              onePassBubbleSort(myRating,n);
            };
        };
        System.out.println("myRating list sorted.");
    };
    
    //***Auxiliary methods for BubbleSort    
    private void onePassBubbleSort(ArrayList<Rating> myRating, int numSorted) {
        for(int n = 0; n < myRating.size() - numSorted; n++) {
          Rating value = myRating.get(n);
          Rating nextValue = myRating.get(n+1);
          int eval = value.compareTo(nextValue);
          if(eval > 0) {
             myRating.set(n, nextValue);
             myRating.set(n+1, value);
            };          
        };
    };
    
    private boolean checkInSortedOrder(ArrayList<Rating> myRating) {
        for(int n = 0; n < myRating.size() - 1; n++) {
          if(myRating.get(n).compareTo(myRating.get(n+1)) > 0) return false;  
        };
        return true;  
    };
    //***
}
