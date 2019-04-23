
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre) {
        myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        String allGenres = MovieDatabase.getGenres(id);
        if(allGenres.indexOf(myGenre) != -1) {
            return true;
        };
        return false;
    }

}
