/**
 * AUTHOR
 * Capstone project for the "JAVA Programming and Software 
 * Enginnering Fundamentals" Specialization by Coursera/DukeUniversity.
 * @Author(jonilearncode)
 * @Version(12052019)
 * 
 * CLASS DESCRIPTION
 * This class intends to implement the base code into the designated website
 * for live evaluation. 
 * The deploy process needs to build the rater and movie databases, and get acces to the fourthrating class as also
 * the desired filter classes.
 * This specific class output prints in html with css embeded.
 */

import java.util.*;

public class RecommendationRunner implements Recommender {    
   
    public ArrayList<String> getItemsToRate () {      
      //settings
      Random rand = new Random();
      int year = 2010;
      int desiredSize = 10;
      Filter yaf = new YearAfterFilter(year);
      ArrayList<String> sampleMovies = MovieDatabase.filterBy(yaf);
      
      //Delete ramdomly excessive movies from sampleMovies
      while(true) {
          int aNumber = rand.nextInt(sampleMovies.size());
          sampleMovies.remove(aNumber);
          if(desiredSize > sampleMovies.size() - 1) {
              break;
            };
        };
      
      return sampleMovies;
    };
    
    public void printRecommendationsFor (String webRaterID) {
        FourthRatings fourat = new FourthRatings();
        ArrayList<Rating> mySimRat = fourat.getSimilarRatings( webRaterID, 20, 1);        
        Collections.sort(mySimRat);
        Collections.reverse(mySimRat);
        int desiredSize = 20;
        
        //debugg
        /*System.out.println("My weRaterID is " + webRaterID);
        System.out.println("mySimRat was build?");
        System.out.println(mySimRat.size());*/
        
        if(mySimRat.size() > desiredSize) {                
            //Adjust output to desired size
            if(mySimRat.size() > desiredSize) {
               ArrayList<Rating> copy = new ArrayList<Rating>();
               for(int n = 0; n < desiredSize; n++) {
                  //System.out.println("I remove one movie. Total " + n );
                  copy.add(mySimRat.get(n)); 
                };
               mySimRat.clear();
               mySimRat = copy;
            };
            //System.out.println("Checking after process... " +mySimRat.size());
            //Print results in html (table)
            System.out.println("<style> .table {width: auto; border: 5px solid white; padding: 15px; text-align: center;}" +
                                        ".body {background-color: smokewhite; font-family: monospace}" +
                                        " .th {font-size: 1.5em;}</style>" );
            System.out.println("<table style=\"width:90%\" \"border: 5px solid white\" \"padding: 15px\" \"text-align: center\" ><tr><th style = \"font-size: 1.5em\">Movie</th><th style = \"font-size: 1.5em\">Genre</th><th style = \"font-size: 1.5em\">Year</th><th style = \"font-size: 1.5em\">Rating</th></tr>");
            for(Rating r : mySimRat) {
                String poster = MovieDatabase.getPoster(r.getItem());
                String genre = MovieDatabase.getGenres(r.getItem());
                int year = MovieDatabase.getYear(r.getItem());                
                double rating = Math.round(r.getValue());                
                System.out.println("<tr><td><img src=\""+poster+"\" width=\"100\"</td><td>"+genre+"</td><td>"+year+"</td><td>"+rating+"</td></tr>");
            };                      
            System.out.println("</table>"); 
            
        } else {
          System.out.println("Oops! Sorry, you haven't rated enough movies...");
          System.out.println("Please try again.");  
        };
    };
}
