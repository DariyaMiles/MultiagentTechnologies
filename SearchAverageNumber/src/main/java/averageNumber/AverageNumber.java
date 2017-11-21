package averageNumber;

public class AverageNumber {
    private Integer sum;
    private Integer counter;

    AverageNumber(Integer value) {
        this.sum = value;
        this.counter = 1;
    }

    private AverageNumber(Integer value, Integer counter) {
        this.sum = value;
        this.counter = counter;
    }

    void add(Integer value) {
        sum += value;
        counter++;
    }

    double calculate() {
        return (double) sum / (double)counter;
    }

    public String toString(){
        return sum+ " " + counter;
    }

    static AverageNumber parseString(String str){
        String[]args = str.split(" ");
        return new AverageNumber(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
