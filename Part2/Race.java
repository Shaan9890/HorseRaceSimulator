import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.2
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    private int noOfLanes;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        noOfLanes = 3;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    /**
     * Perform various tests
     */
    /*
    public static void tests() {
        Race race = new Race(5);  // Create new race

        // Create new horses
        Horse horse1 = new Horse('e', "Epona", 0.5);
        Horse horse2 = new Horse('T', "Torrent", 0.5);
        Horse horse3 = new Horse('L', "Ludwig", 0.9);

        // Add the horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }
     */

    public static void testsGUI() {
        Race race = new Race(5);  // Create new race

        // Create new horses
        Horse horse1 = new Horse("horses/Clavelandbay(brownCoat).jpg", "Epona", 0.5);
        Horse horse2 = new Horse("horses/Lippizaner(whiteCoat).jpg", "Torrent", 0.5);
        Horse horse3 = new Horse("horses/Shire(blackCoat).jpg", "Ludwig", 0.9);

        // Add the horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRaceGUI();
    }

    /**
     * Main method to run tests (may be removed later)
     */
    public static void main(String[] args) {
        testsGUI();
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                finished = true;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        if (raceWonBy(lane1Horse)) {
            announceWinner(lane1Horse);
        }
        else if (raceWonBy(lane2Horse)) {
            announceWinner(lane2Horse);
        }
        else {
            announceWinner(lane3Horse);
        }
    }

    public void startRaceGUI() {
        JFrame frame = new JFrame("Main Race Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {"TRACK", "HORSES", "STATISTICS", "BETTING", "START RACE"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);

            switch (label) {
                case "TRACK" -> {
                    button.setBackground(new Color(51, 178, 73));
                    button.addActionListener(e -> trackGUI());
                }
                case "HORSES" -> {
                    button.setBackground(new Color(85, 194, 218));
                    button.addActionListener(e -> {

                    });
                }
                case "STATISTICS" -> {
                    button.setBackground(new Color(255, 189, 3));
                    button.addActionListener(e -> {

                    });
                }
                case "BETTING" -> {
                    button.setBackground(new Color(235, 45, 58));
                    button.addActionListener(e -> {

                    });
                }
                case "START RACE" -> {
                    Horse horse = new Horse("♘", "Epona", 0.5);
                    button.addActionListener(e -> printRaceGUI());
                }
            }

            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void setRaceLength(int newLength) {
        if (newLength > 15 || newLength < 1) {
            JOptionPane.showMessageDialog(null, "Length can only go from 1 to 15!");
            return;
        }
        raceLength = newLength;
    }

    private void setNoOfLanes(int newNoOfLanes) {
        if (newNoOfLanes > 5 || newNoOfLanes < 1) {
            JOptionPane.showMessageDialog(null, "Number of lanes can only go from 1 to 5!");
            return;
        }
        noOfLanes = newNoOfLanes;
    }

    private String showLength() {
        return "Length: " + raceLength;
    }

    private String showLanes() {
        return "Lanes: " + noOfLanes;
    }

    private void trackGUI() {
        JFrame frame = new JFrame("Track Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create text fields displaying the current distance of the track and number of lanes
        JTextField length = new JTextField();
        length.setPreferredSize(new Dimension(300, 50));
        length.setEditable(false);
        length.setHorizontalAlignment(JTextField.RIGHT);
        panel.add(length, BorderLayout.NORTH);
        length.setText(showLength());

        JTextField lanes = new JTextField();
        lanes.setPreferredSize(new Dimension(300, 50));
        lanes.setEditable(false);
        lanes.setHorizontalAlignment(JTextField.RIGHT);
        panel.add(lanes, BorderLayout.PAGE_END);
        lanes.setText(showLanes());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {"INCREASE LENGTH", "DECREASE LENGTH", "ADD LANE", "REMOVE LANE"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);

            switch (label) {
                case "INCREASE LENGTH" -> {
                    button.setBackground(new Color(51, 178, 73));
                    button.addActionListener(e -> {
                        setRaceLength(raceLength + 1);
                        length.setText(showLength());
                    });
                }
                case "DECREASE LENGTH" -> {
                    button.setBackground(new Color(85, 194, 218));
                    button.addActionListener(e -> {
                        setRaceLength(raceLength - 1);
                        length.setText(showLength());
                    });
                }
                case "ADD LANE" -> {
                    button.setBackground(new Color(255, 189, 3));
                    button.addActionListener(e -> {
                        setNoOfLanes(noOfLanes + 1);
                        lanes.setText(showLanes());
                    });
                }
                case "REMOVE LANE" -> {
                    button.setBackground(new Color(235, 45, 58));
                    button.addActionListener(e -> {
                        setNoOfLanes(noOfLanes - 1);
                        lanes.setText(showLanes());
                    });
                }
            }

            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void announceWinner(Horse horse) {
        System.out.println("And the winner is " + horse.getName());
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        return theHorse.getDistanceTravelled() == raceLength;
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        // System.out.print('\u000C'); //clear the terminal window
        clearScreen();
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }


    private void printRaceGUI() {

    }
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        //print the horse's details
        System.out.print(" " + theHorse.getName() + " (Current confidence: " + theHorse.getConfidence() + ")");
    }

    private String createLane(Horse theHorse) {
        String lane = "";
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //Add a | for the beginning of the lane
        lane += '|';

        //Add the spaces before the horse
        lane = multiConcatenate(lane, " ", spacesBefore);

        //if the horse has fallen then add X
        //else add the horse's icon
        if(theHorse.hasFallen())
        {
            lane += 'X';
        }
        else
        {
            lane += theHorse.getSymbol();
        }

        //add the spaces after the horse
        lane = multiConcatenate(lane, " ", spacesAfter);

        //add the | for the end of the track
        lane += '|';

        //add the horse's details
        lane += " " + theHorse.getName() + " (Current confidence: " + theHorse.getConfidence() + ")";

        return lane;
    }

    private void printLaneGUI(Horse theHorse) {

        // Create a frame called Race
        JFrame frame = new JFrame("Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        // Create a new panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a new, un-editable text field to display the lane
        JTextField lane = new JTextField();
        lane.setPreferredSize(new Dimension(600, 100));
        lane.setEditable(false);

        lane.setText(createLane(theHorse));

        panel.add(lane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. multiplePrint('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    private String multiConcatenate(String string, String concatenation, int times) {
        string = string + String.valueOf(concatenation).repeat(Math.max(0, times));
        return string;
    }
}
