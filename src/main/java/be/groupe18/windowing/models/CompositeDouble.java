package be.groupe18.windowing.models;

public class CompositeDouble {
    private double primary;
    private double secondary;

    public CompositeDouble(double primary, double secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public static boolean greaterThan(CompositeDouble d1, CompositeDouble d2){
        if(d1.primary > d2.primary){
            return true;
        }
        else{
            return d1.primary == d2.primary && d1.secondary > d2.secondary;
        }
    }

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
