
/**
 * A class to represent Horse objects. These are to be used
 * with the Race class to represent the horses within the
 * race.
 * 
 * @author Shaan Shah
 * @version 1.3
 */
public class Horse
{
    //Fields of class Horse
    private final String horseName;
    private char horseSymbol;
    private int horseDistance;
    private Boolean horseFallen;
    private double horseConfidence;

    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.setConfidence(horseConfidence);
        this.setSymbol(horseSymbol);
        this.horseName = horseName;

        this.horseFallen = false;
        this.horseDistance = 0;
    }

    /**
     * Print all details of the horse
     */
    public void printDetails() {
        System.out.println("Horse details\n");
        System.out.println("Name: " + this.getName());
        System.out.println("Symbol: " + this.getSymbol());
        System.out.println("Fallen: " + this.hasFallen());
        System.out.println("Confidence: " + this.getConfidence());
        System.out.println("Distance: " + this.getDistanceTravelled());
        System.out.println("\n");
    }

    /**
     * Perform various tests
     */
    public static void tests() {
        Horse horse = new Horse('♘', "horse", 0.5);  // Create new horse
        System.out.println("Created new horse with symbol: ♘, name: horse and confidence: 0.5\n");

        horse.printDetails();

        // Change horse details
        horse.setConfidence(0.7);
        horse.setSymbol('♞');
        horse.moveForward();
        System.out.println("Horse confidence changed to 0.7, symbol changed to ♞ and moved forward by 1\n");

        horse.printDetails();

        // Fall
        horse.fall();
        System.out.println("Horse has fallen\n");

        horse.printDetails();

        // Go back to start
        horse.goBackToStart();
        System.out.println("Horse has moved back to the start\n");

        horse.printDetails();

        // Attempt to enter invalid data
        horse.setConfidence(-1);
        System.out.println("Attempted to set confidence to -1\n");

        horse.printDetails();

        horse.setConfidence(1);
        System.out.println("Attempted to set confidence to 1\n");

        horse.printDetails();

        horse.setConfidence(0);
        System.out.println("Attempted to set confidence to 0\n");

        horse.printDetails();

        horse.setConfidence(2);
        System.out.println("Attempted to set confidence to 2\n");

        horse.printDetails();
    }

    /**
     * Main method (only used for testing and may be removed)
     */
    public static void main(String[] args) {
        tests();
    }
    
    //Other methods of class Horse
    public void fall()
    {
        horseFallen = true;
        this.setConfidence(this.getConfidence() - 0.1);
    }
    
    public double getConfidence()
    {
        return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return horseDistance;
    }
    
    public String getName()
    {
        return horseName;
    }
    
    public char getSymbol()
    {
        return horseSymbol;
    }
    
    public void goBackToStart()
    {
        horseDistance = 0;
    }
    
    public boolean hasFallen()
    {
        return horseFallen;
    }

    public void moveForward()
    {
        horseDistance += 1;
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence <= 0) {
            this.horseConfidence = 0.1;  // Sets a lower limit of 0.1 confidence
            return;
        }
        if (newConfidence >= 1) {
            this.horseConfidence = 0.9;  // Sets an upper limit of 0.9 confidence
            return;
        }
        horseConfidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }
    
}
