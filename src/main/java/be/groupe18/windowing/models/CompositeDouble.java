package be.groupe18.windowing.models;

/**
 * Represent a composite double, which is a class that represent (a|b)
 */
public class CompositeDouble {
    public static final CompositeDouble POSITIVE_INFINITY = new CompositeDouble(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final CompositeDouble NEGATIVE_INFINITY = new CompositeDouble(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

    private final double primary;
    private final double secondary;

    public CompositeDouble(double primary, double secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }
    
    /**
     * @return true is the CompositeDouble instance value is set to +infinite
     */
    public boolean isPositiveInfinite() {
        return (primary == Double.POSITIVE_INFINITY && secondary == Double.POSITIVE_INFINITY);
    }
    /**
     * @return true is the CompositeDouble instance value is set to -infinite
     */
    public boolean isNegativeInfinite() {
        return (primary == Double.NEGATIVE_INFINITY && secondary == Double.NEGATIVE_INFINITY);
    }

    public double getAsDouble() {
        return this.primary;
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

    public static boolean equalOrGreaterThan(CompositeDouble d1, CompositeDouble d2){
        if(d1.primary >= d2.primary){
            return true;
        }
        else{
            return d1.primary == d2.primary && d1.secondary >= d2.secondary;
        }
    }

    public static boolean looseEqual(CompositeDouble d1, CompositeDouble d2){
        return d1.primary == d2.primary;
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
