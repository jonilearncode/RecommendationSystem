
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
    
    public void testLoadMovies() {
        ArrayList<Movie> myList = loadMovies("ratedmovies_short.csv");
        for(Movie m : myList) {
          System.out.println(m);  
        };
    };
}
