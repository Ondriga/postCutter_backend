/*
 * Source code for the backend of Bachelor thesis.
 * MyProgress abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter;

/**
 * Representing information about cutter progression. Stop flag can be used only one and can`t be reset.
 */
public abstract class MyProgress {
    /// Flag for stop cutter process.
    private boolean stopFlag = false;
    /// Max value of progress. Default value is 100.
    private int maxValue = 100;
    /// Actual progress value. Default value is 0.
    private int actual = 0;

    /**
     * Method definition. This method is triggering when the progress increase.
     * @param progress actual progress value.
     */
    public abstract void update(int progress);

    /**
     * Set stop flag on true.
     */
    public final void setStopFlag(){
        this.stopFlag = true;
    }

    /**
     * Get info about stop flag.
     * @return value of stop flag.
     */
    public boolean shouldStop(){
        return this.stopFlag;
    }

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
        update(this.actual);
    }

    /**
     * Getter for max value of progress.
     * @return max value of progress.
     */
    public int getMaxValue(){
        return this.maxValue;
    }
}
