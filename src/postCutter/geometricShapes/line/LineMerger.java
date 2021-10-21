/*
 * Source code for the backend of Bachelor thesis.
 * LineMerger class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import java.util.ArrayList;
import java.util.List;

public class LineMerger {
    /// Higher level list of lines.
    private final List<MyLine> listHigher;
    /// Lower level list of lines.
    private final List<MyLine> listLower;
    /// Level value of higher list of lines.
    private final int heightOfListHigher;
    /// List of merged lines for later use.
    private final List<MyLine> finalList;
    /// List of merged lines at lower level for later merge.
    private List<MyLine> linesLowerAfter = new ArrayList<>();
    /// Line from higher level list.
    private MyLine lineHigher;
    /// Line from lower level list.
    private MyLine lineLower;
    /// Actual line ready for merge with lines from lower and higher level lists.
    private MyLine mergedLine;

    /// Constant allow temporary of length.
    private static final int ALLOW_TEMPORARY_LENGTH = 10;

    /**
     * Constructor.
     * @param listHigher lines from higher level
     * @param listLower lines from lower level
     * @param finalList list for store merged lines that will be use later
     * @param heightOfListHigher level of lines from higher level
     */
    public LineMerger(List<MyLine> listHigher, List<MyLine> listLower, List<MyLine> finalList, int heightOfListHigher){
        this.listHigher = listHigher;
        this.listLower = listLower;
        this.finalList = finalList;
        this.heightOfListHigher = heightOfListHigher;
        this.lineHigher = getLineBiggerThan(this.listHigher);
        this.lineLower = getLineBiggerThan(this.listLower);
        loadMergeLine();
    }

    /**
     * Load line from list that is bigger than constant ALLOW_TEMPORARY_LENGTH.
     * @param lines list of lines
     * @return Line from list, that is bigger than constant, otherwise null.
     */
    private MyLine getLineBiggerThan(List<MyLine> lines){
        if(lines == null){
            return null;
        }
        MyLine line = null;
        while(lines.size() > 0){
            line = lines.remove(0);
            if(line.length() > ALLOW_TEMPORARY_LENGTH){
                return line;
            }
        }
        return null;
    }

    /**
     * Store line from lineHigher or lineLower. If lineHigher is before lineLower, than lineHigher is stored
     * into mergedLine and opposite. If bought lines are null, than null is store into mergedLine.
     */
    private void loadMergeLine(){
        if(this.lineHigher != null && this.lineLower != null){
            if(this.lineHigher.isBefore(this.lineLower)){
                this.mergedLine = this.lineHigher;
                this.lineHigher = getLineBiggerThan(this.listHigher);
            }else{
                this.mergedLine = this.lineLower;
                this.lineLower = getLineBiggerThan(this.listLower);
            }
        }else if(this.lineHigher != null){
            this.mergedLine = this.lineHigher;
            this.lineHigher = getLineBiggerThan(this.listHigher);
        }else{
            this.mergedLine = this.lineLower;
            this.lineLower = getLineBiggerThan(this.listLower);
        }
    }

    /**
     * Store merged line into finalList if its level is same as heightOfListHigher, otherwise is stored into linesLowerAfter.
     * After that new line will be stored into mergedLine.
     */
    private void mergedLineStore(){
        if(this.mergedLine.getLevel() == this.heightOfListHigher){
            this.finalList.add(this.mergedLine);
        }else{
            this.linesLowerAfter.add(this.mergedLine);
        }
        this.loadMergeLine();
    }

    /**
     * Try merge mergedLine with higherLine or lowerLine. It depends on flag isHigher. If merge fail,
     * than mergedLine will be stored. If merge is successful, than new line will be stored into higherLine/lowerLine.
     * @param isHigher
     */
    private void mergeLineWithLine(boolean isHigher){
        MyLine line = this.lineHigher;
        if(!isHigher){
            line = this.lineLower;
        }
        if(this.mergedLine.extendByLine(line)){
            if(isHigher){
                this.lineHigher = getLineBiggerThan(this.listHigher);
            }else{
                this.lineLower = getLineBiggerThan(this.listLower);
            }
        }else{
            mergedLineStore();
        }
    }

    /**
     * Do decision if merge mergedLine with higherLine or lowerLine. If bought are null, than mergedLine store.
     * @return List of mergedLine with lower level.
     */
    public List<MyLine> mergeLinesAndStore(){        
        while(this.mergedLine != null){
            if(this.lineHigher != null && this.lineLower != null){
                if(this.lineHigher.isBefore(this.lineLower)){
                    mergeLineWithLine(true);
                }else{
                    mergeLineWithLine(false);
                }
            }else if(this.lineHigher != null){
                mergeLineWithLine(true);
            }else if(this.lineLower != null){
                mergeLineWithLine(false);
            }else{
                mergedLineStore();
            }
        }
        return this.linesLowerAfter;
    }
}
