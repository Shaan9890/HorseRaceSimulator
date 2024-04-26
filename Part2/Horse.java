/**
 * A class to represent Horse objects. These are to be used
 * with the Race class to represent the horses within the
 * race.
 * 
 * @author Shaan Shah
 * @version 2.6
 */
public class Horse
{
    //Fields of class Horse
    private String horseName;
    private String horseSymbol;
    private int horseDistance;
    private Boolean horseFallen;
    private double horseConfidence;
    private int horseLane;
    private int horseWins;


    /**
     * Constructor for objects of class Horse
     */
    Horse () {
        this.setConfidence(0.5);
        this.setSymbol("");
        this.setName("");
        this.horseFallen = false;
        this.horseDistance = 0;
        this.horseWins = 0;
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
    
    public String getSymbol()
    {
        return horseSymbol;
    }

    public int getLane() { return horseLane; }

    public int getWins() { return horseWins; }
    
    public void goBackToStart()
    {
        horseFallen = false;
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
        horseConfidence = (Math.round(10.0 * newConfidence)) / 10.0;  // Math.round prevents floating point rounding issues.
    }
    
    public void setSymbol(String newSymbol)
    {
        horseSymbol = newSymbol;
    }

    public void setName(String newName) { horseName = newName; }

    public void setLane(int newLane) { horseLane = newLane; }

    public void increaseWins() { horseWins += 1;}
}
