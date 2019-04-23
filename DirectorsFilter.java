
public class DirectorsFilter implements Filter {
    private String myDirectors;
    private String[] listDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = directors;
        listDirectors = myDirectors.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        String allDirectors = MovieDatabase.getDirector(id);
        for( String d : listDirectors) {
            if(allDirectors.indexOf(d) != -1) {
                return true;
            };
        };
        return false;
    }

}
