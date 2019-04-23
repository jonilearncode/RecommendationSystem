
/**
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(17042019)
 * 
 * CLASS DESCRIPTION
 * This class intends to find the average rating of movies using different filters. 
 */

import java.util.*;

public class MovieRunnerWithFilters {
    private ArrayList<Rating> myRating;
    private ThirdRatings trat;
    
    private void initialize() {
      trat = new ThirdRatings("ratings.csv");
      MovieDatabase.initialize("ratedmoviesfull.csv");
      System.out.println("myRaters loaded." + "\nMovieDatabase loaded." +
                        "\nNº of raters: " + trat.getRaterSize() + 
                        "\nNº of movies: " + MovieDatabase.size());
    };
    
    public void printAverageRatingsByDirectorsAndMinutes(int minRaters, String directors, int minMinutes, int maxMinutes) {
      initialize();                                                
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter df = new DirectorsFilter(directors);
      Filter mf = new MinuteFilter(minMinutes, maxMinutes);
      AllFilters combinedFilter = new AllFilters();
      combinedFilter.addFilter(df);
      combinedFilter.addFilter(mf);
      myRating = trat.getAverageRatingsByFilter(minRaters, combinedFilter);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      System.out.println("\n"+"____________Movies sorted by average ratings (min. " + minRaters + " raters)____________");
      for(int n = 0; n < myRating.size(); n++) {
          double avgRating = myRating.get(n).getValue();                 
          String id = myRating.get(n).getItem();
          String title = MovieDatabase.getTitle(id);
          int minutes = MovieDatabase.getMinutes(id);
          String drectrs = MovieDatabase.getDirector(id);
          System.out.println(avgRating + " Time: " + minutes + " " + title + "\n  " + drectrs);
        };
    };
    
    public void printAverageRatingsByYearAndGenre(int minRaters, int year, String genre) {
      initialize();                                                
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter yaf = new YearAfterFilter(year);
      Filter gf = new GenreFilter(genre);
      AllFilters combinedFilter = new AllFilters();
      combinedFilter.addFilter(yaf);
      combinedFilter.addFilter(gf);
      myRating = trat.getAverageRatingsByFilter(minRaters, combinedFilter);
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
    
    public void printAverageRatings(int minRaters) {
      initialize();
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      myRating = trat.getAverageRatings(minRaters);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      printResults(myRating, minRaters, true, false, false, false, false);
    };
    
    public void printAverageRatingsByYear(int minRaters, int year) {
      initialize();
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter yaf = new YearAfterFilter(year);
      myRating = trat.getAverageRatingsByFilter(minRaters, yaf);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, true, false, false, false);
    };
    
    public void printAverageRatingsByGenre(int minRaters, String genre) {
      initialize();
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter gf = new GenreFilter(genre);
      myRating = trat.getAverageRatingsByFilter(minRaters, gf);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      printResults(myRating, minRaters,false, false, true, false, false);
    };
    
    public void printAverageRatingsByMinutes(int minRaters, int minMinutes, int maxMinutes) {
      initialize();
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter mf = new MinuteFilter(minMinutes, maxMinutes);
      myRating = trat.getAverageRatingsByFilter(minRaters, mf);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
      //Print the new list properly sorted
      printResults(myRating, minRaters, false, false, false, true, false);
    };
    
    public void printAverageRatingsByDirectors(int minRaters, String directors) {
      initialize();
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings and per consequence initializes the MovieDatabase if hasn't yet.
      Filter df = new DirectorsFilter(directors);
      myRating = trat.getAverageRatingsByFilter(minRaters, df);
      System.out.println("Found " + myRating.size() + " movies:");
      //Sort the list by avg rating
      sortByAvgRating(myRating);      
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
