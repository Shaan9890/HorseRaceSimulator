import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.lang.Math;
import java.util.concurrent.TimeUnit;

/**
 * A horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 2.6
 */
public class Race
{
    private int raceLength;
    private final Horse[] horses;
    private int noOfLanes;

    /**
     * Constructor for objects of class Race
     * Initially there are horses of the default details in the lanes
     */
    Race() {
        // initialise instance variables
        raceLength = 5;
        noOfLanes = 3;
        horses = new Horse[5];
        for (int i = 0; i < horses.length; i++) {
            horses[i] = new Horse();
        }
    }

    public static void testsGUI() {
        Race race = new Race();  // Create new race

        race.startRaceGUI();
    }

    /**
     * Main method to run tests (may be removed later)
     */
    public static void main(String[] args) {
        testsGUI();
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
                    button.addActionListener(e -> horseGUI());
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
                case "START RACE" -> button.addActionListener(e -> printRaceGUI());
            }

            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private Boolean addHorseToLane(Horse theHorse, String laneText) {
        int laneNumber = Integer.parseInt(laneText);
        for (Horse eachHorse : horses) {
            if (eachHorse.getLane() == laneNumber) {
                return false;
            }
        }
        theHorse.setLane(laneNumber);
        return true;
    }

    private void horseSettings(Horse theHorse) {
        JFrame frame = new JFrame("Horse Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(5, 2, 5, 5));

        // Add labels corresponding to current horse values, some of which may be edited
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name");
        JTextField name = new JTextField();
        name.setText(theHorse.getName());
        settingsPanel.add(nameLabel);
        settingsPanel.add(name);

        JLabel symbolLabel = new JLabel();
        symbolLabel.setText("Symbol (1 character)");
        JTextField symbol = new JTextField();
        symbol.setText(theHorse.getSymbol());
        settingsPanel.add(symbolLabel);
        settingsPanel.add(symbol);

        JLabel laneLabel = new JLabel();
        laneLabel.setText("Lane (min: 1, max: " + noOfLanes + ")");

        // For lane, create a formatter used to limit text field input to integers going from 1 to 5
        NumberFormat format = NumberFormat.getInstance();
        LaneFormatter formatter = new LaneFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(noOfLanes);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        JFormattedTextField lane = new JFormattedTextField(formatter);
        lane.setText(String.valueOf(theHorse.getLane()));
        settingsPanel.add(laneLabel);
        settingsPanel.add(lane);

        JLabel confidenceLabel = new JLabel();
        confidenceLabel.setText("Confidence");
        JLabel confidence = new JLabel();
        confidence.setText(String.valueOf(theHorse.getConfidence()));
        settingsPanel.add(confidenceLabel);
        settingsPanel.add(confidence);

        // Create an update button that updates the chosen values for the horse
        JButton update = new JButton("Update Details");
        update.setBackground(new Color(85, 194, 218));
        update.addActionListener(e -> {
            if (name.getText().isEmpty() || symbol.getText().isEmpty() || lane.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fields should not be left empty.");
            }
            else {
                if (symbol.getText().length() != 1) {
                    JOptionPane.showMessageDialog(frame, "Only use one character for the symbol.");
                }
                else {
                    if (addHorseToLane(theHorse, lane.getText())) {
                        theHorse.setName(name.getText());
                        theHorse.setSymbol(symbol.getText());
                        JOptionPane.showMessageDialog(frame, "Horse settings updated.");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "A horse already belongs to this lane!");
                    }
                }
            }
        });
        settingsPanel.add(update);

        panel.add(settingsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void horseGUI() {
        JFrame frame = new JFrame("Horses Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 3, 5, 5));

        String[] buttonLabels = {"HORSE 1", "HORSE 2", "HORSE 3", "HORSE 4", "HORSE 5"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setBackground(new Color(85, 194, 218));

            int horseNumber = Integer.parseInt("" +label.charAt(6));
            button.addActionListener(e -> horseSettings(horses[horseNumber - 1]));
            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void setRaceLength(int newLength) {
        if (newLength > 50 || newLength < 1) {
            JOptionPane.showMessageDialog(null, "Length can only go from 1 to 50!");
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
        panel.add(lanes, BorderLayout.SOUTH);
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

    private String announceWinner(Horse horse) {
        horse.setConfidence(horse.getConfidence() + 0.1);
        return "And the winner is " + horse.getName();
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

    private void printRaceGUI() {
        // Create a frame called Race
        JFrame frame = new JFrame("Race");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);

        // Create a new panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add a scroller
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setAutoscrolls(true);
        frame.getContentPane().add(scroller, BorderLayout.EAST);

        // Create a new, un-editable text field to display the top of the track
        JLabel top = new JLabel();
        top.setPreferredSize(new Dimension(300, 1));
        top.setText(multiConcatenate("", "=", (raceLength / 2) + 2));
        panel.add(top, BorderLayout.NORTH);

        for (Horse eachHorse : horses) {
            eachHorse.goBackToStart();  // Sets all horse distances to 0 and fallen to false when a race starts
        }

        Horse[] laneHorses = new Horse[5];
        int laneHorsesi = 0;
        JLabel[] lanes = new JLabel[5];
        int lanesi = 0;
        for (int i = 0; i < noOfLanes; i++) {  // Loop through lanes

            Horse laneHorse = new Horse();
            for (Horse eachHorse : horses) {  // Loop through horses
                if (eachHorse.getLane() == i + 1) {
                    laneHorse = eachHorse;  // Set laneHorse to this horse if they belong to the current lane
                    laneHorses[laneHorsesi] = laneHorse;
                    laneHorsesi++;
                }
            }
            if (laneHorse.getName().isEmpty()) {  // If no horse for this lane is found, continue
                continue;
            }
            JLabel lane = new JLabel();  // If we do not continue, print out the horse's lane
            lane.setPreferredSize(new Dimension(300, 1));
            lane.setText(createLane(laneHorse));
            lanes[lanesi] = lane;
            lanesi++;
            panel.add(lane);
        }

        // Create a new label to display the bottom of the track
        JLabel bottom = new JLabel();
        bottom.setPreferredSize(new Dimension(300, 1));
        bottom.setText(multiConcatenate("", "=", (raceLength / 2) + 2));
        panel.add(bottom, BorderLayout.SOUTH);

        // Create new label to announce the winner
        JLabel winMessage = new JLabel();
        winMessage.setPreferredSize(new Dimension(300, 1));
        panel.add(winMessage, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        new Thread(() -> {
            boolean finished = false;
            Horse winner;
            String result = "";

            while (!finished) {
                // Wait for 100 milliseconds
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }

                // Move horses forward
                SwingUtilities.invokeLater(() -> {
                    for (int i = 0; i < 5; i++) {
                        if (lanes[i] != null && !(laneHorses[i].hasFallen())) {
                            moveHorse(laneHorses[i]);
                            lanes[i].setText(createLane(laneHorses[i]));
                        }
                    }
                });

                // Check if race has finished then set end message
                int horses = 0;
                int fallenHorses = 0;
                for (Horse horse : laneHorses) {
                    if (horse != null) {
                        horses++;
                        if (raceWonBy(horse)) {
                            System.out.println("Won");
                            finished = true;
                            winner = horse;
                            result = announceWinner(winner);
                        }
                        if (horse.hasFallen()) {
                            fallenHorses++;
                        }
                    }
                }

                if (horses == fallenHorses) {
                    System.out.println("Tie");
                    finished = true;
                    result = "No winner";
                }
            }

            // Update win message
            String finalResult = result;
            SwingUtilities.invokeLater(() -> winMessage.setText(finalResult));

        }).start();
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

    private String multiConcatenate(String string, String concatenation, int times) {
        string = string + String.valueOf(concatenation).repeat(Math.max(0, times));
        return string;
    }
}
