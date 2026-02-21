package be.groupe18.windowing.models;

/**
 * Represent a composite double, which is a class that represent (a|b)
 */
public class CompositeDouble {
    private double primary;
    private double secondary;

    public CompositeDouble(double primary, double secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    /**
     * Uses lexicographic comparison to compare d1 and d2
     * @param d1 first composite number to compare
     * @param d2 second composite number to compare
     * @return true if d1 > d2, false otherwise
     */
    public static boolean greaterThan(CompositeDouble d1, CompositeDouble d2){
        if(d1.primary > d2.primary){
            return true;
        }
        else{
            return d1.primary == d2.primary && d1.secondary > d2.secondary;
        }
    }

    /**
     * Return the qé
     * @param d1
     * @param d2
     * @return
     */
    public static CompositeDouble max(CompositeDouble d1, CompositeDouble d2){
        if(greaterThan(d1, d2)){
            return d1;
        }
        else{
            return d2;
        }
    }

    public static CompositeDouble min(CompositeDouble d1, CompositeDouble d2) {
        if(greaterThan(d1, d2)){
            return d2;
        }
        else{
            return d1;
        }
    }
}
