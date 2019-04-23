
public class MinuteFilter implements Filter {
    private int minMin;
    private int maxMin;
    
    public MinuteFilter(int minMinutes, int maxMinutes) {
        minMin = minMinutes;
        maxMin = maxMinutes;
    }
    
    @Override
    public boolean satisfies(String id) {
        int myMinutes = MovieDatabase.getMinutes(id);
        if(myMinutes >= minMin && myMinutes <= maxMin) {
            return true;
        };
        return false;
    }

}
