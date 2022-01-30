/*
 * Source code for the backend of Bachelor thesis.
 * MyProgress abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter;

/**
 * Representing information about cutter progression.
 */
public abstract class MyProgress {
    /// Max value of progress. Default value is 100.
    private int maxValue = 100;
    /// Actual progress value. Default value is 0.
    private int actual = 0;

    /**
     * Method definition. This method is triggering when the progress increase.
     * @param progress actual progress value.
     * @param maxValue max value of progress.
     */
    public abstract void update(int progress, int maxValue);

    /**
     * Change max value of progress. And reset actual progress value and stop flag.
     * @param maxValue of progress.
     */
    public void setMaxValue(int maxValue){
        this.actual = 0;
        this.maxValue = maxValue;
    }

    /**
     * Increase actual value of progress and call update with this value.
     */
    public void increase(){
        this.actual++;
        update(this.actual, this.maxValue);
    }
}
