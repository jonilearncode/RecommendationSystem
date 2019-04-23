
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
 
public class MovieRunnerAverage {
    
    public void printAverageRatings(int minRaters) {
      SecondRatings srat = new SecondRatings();
      System.out.println("Nº of raters: " + srat.getRaterSize() + 
                        "\nNº of movies: " + srat.getMovieSize());
                        
      //**Prints a list of movies sorted by correspondingly Rating avg and sorted by this.
      //Creates a list of ratings
      srat.getAverageRatings(minRaters);
      //Sort the list by avg rating     
      for (int n = 0; n < srat.myRating.size()-1; n++) {          
          Rating value = srat.myRating.get(n);
          Rating otherValue = srat.myRating.get(n+1);
          int eval = value.compareTo(otherValue);          
          if (eval > 0) {
              srat.myRating.set(n, otherValue);
              srat.myRating.set(n+1, value);
            };
        };
      //Print the new list properly sorted
      for(int n = 0; n < srat.myRating.size(); n++) {
          double avgRating = srat.myRating.get(n).getValue();
          //Due the nature of the method srat.getAverageById we need to consider only those avg i sbigger than 0.
          if(avgRating > 0.0) {          
          String id = srat.myRating.get(n).getItem();
          String title = srat.getTitle(id);
          System.out.println(avgRating + " " + title);
        };
      };
    };
    
    public void getAverageRatingOneMovie() {
      SecondRatings srat = new SecondRatings();
      System.out.println("Nº of raters: " + srat.getRaterSize() + 
                        "\nNº of movies: " + srat.getMovieSize());
      
      String tgtTitle = "Vacation";
      String tgtID = srat.getID(tgtTitle);
      double tgtAvgRating = srat.getAverageByID(tgtID, 1);                  
      System.out.println(tgtTitle + " -- " + tgtAvgRating);
    };
    
}
