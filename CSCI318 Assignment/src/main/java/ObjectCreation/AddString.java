package ObjectCreation;

public class AddString {

    private int size;
    private int lowerLimit;
    private int upperLimit;

    public AddString(int size, int lowerLimit, int upperLimit) {
        this.size = size;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public int getSize() {
        return size;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }
}
