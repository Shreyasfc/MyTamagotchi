package softwaredesign;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//TODO:
//- Implement GUI interface (from class diagram)
//- Restructure the code to be more organized
//- Finish animations
//- Structure the code to enable multiple models
//- Wire it up the class
//- More immutable
//- Scale with size increase

public class GUIMain {

    public static int SCREENWIDTH = 500;
    public static int SCREENHEIGHT = 500;


    public static void runGUI(){

        // Create a window
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label with a background image
        try {
            Image backgroundImage = ImageIO.read(new File("src/main/java/softwaredesign/images/background.png"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
            frame.setContentPane(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the size and center the window
        frame.setSize(SCREENWIDTH, SCREENHEIGHT);
        frame.setLocationRelativeTo(null);

        // Create a label with an image
        ImageIcon characterIcon = new ImageIcon("src/main/java/softwaredesign/images/cristianobasic.png");
        JLabel characterLabel = new JLabel(characterIcon);
        characterLabel.setBounds(135, 175, 250, 250);
        characterLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(characterLabel);

        // Create the hunger level bar
        JProgressBar hungerBar = new JProgressBar(0, 100);
        //TODO: Allow for changing value here
        hungerBar.setValue(90);
        hungerBar.setStringPainted(true);
        hungerBar.setString("Hunger: " + hungerBar.getValue() + "%");
        hungerBar.setBounds(10, 10, 150, 20);
        hungerBar.setForeground(new Color(128, 0, 0));
        hungerBar.setBackground(new Color(255, 182, 193));
        hungerBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add a white border to the progress bar
        frame.add(hungerBar);

        // TODO: Allow for making a warning function
        ImageIcon warningImage = new ImageIcon("src/main/java/softwaredesign/images/warning.png");
        JLabel warningLabel = new JLabel(warningImage);
        warningLabel.setBounds(155, 10, 125, 25);
        warningLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(warningLabel);

        // Create the hygiene level bar
        JProgressBar hygieneBar = new JProgressBar(0, 100);
        //TODO: Allow for changing value here
        hygieneBar.setValue(85);
        hygieneBar.setStringPainted(true);
        hygieneBar.setString("Hygiene: " + hygieneBar.getValue() + "%");
        hygieneBar.setBounds(10, 50, 150, 20);
        hygieneBar.setForeground(new Color(0, 11, 255));
        hygieneBar.setBackground(new Color(204, 222, 255, 255));
        hygieneBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add a white border to the progress bar
        frame.add(hygieneBar);

        // Create the bladder level bar
        JProgressBar bladderBar = new JProgressBar(0, 100);
        //TODO: Allow for changing value here
        bladderBar.setValue(70);
        bladderBar.setStringPainted(true);
        bladderBar.setString("Bladder: " + bladderBar.getValue() + "%");
        bladderBar.setBounds(10, 90, 150, 20);
        bladderBar.setForeground(new Color(96, 77, 0));
        bladderBar.setBackground(new Color(236, 224, 181, 255));
        bladderBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add a white border to the progress bar
        frame.add(bladderBar);

        // Create the thirst level bar
        JProgressBar thirstBar = new JProgressBar(0, 100);
        //TODO: Allow for changing value here
        thirstBar.setValue(10);
        thirstBar.setStringPainted(true);
        thirstBar.setString("Thirst: " + thirstBar.getValue() + "%");
        thirstBar.setBounds(10, 130, 150, 20);
        thirstBar.setForeground(new Color(6, 58, 0));
        thirstBar.setBackground(new Color(225, 250, 225, 255));
        thirstBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add a white border to the progress bar
        frame.add(thirstBar);

        // Create the mood bar
        JProgressBar moodBar = new JProgressBar(0, 100);
        //TODO: Allow for changing value here
        moodBar.setValue(25);
        moodBar.setStringPainted(true);
        moodBar.setString("Mood: " + moodBar.getValue() + "%");
        moodBar.setBounds(10, 170, 150, 20);
        moodBar.setForeground(new Color(86, 0, 66));
        moodBar.setBackground(new Color(255, 234, 253, 255));
        moodBar.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add a white border to the progress bar
        frame.add(moodBar);

        // Create the Feeding Button
        JButton feedingButton = new JButton("Feed");
        feedingButton.setBounds(375, 10, 100, 30);
        feedingButton.setPreferredSize(new Dimension(80, 30));
        frame.add(feedingButton);
        feedingButton.addActionListener(e -> System.out.println("Feeding Pressed."));

        // Create the Shower Button
        JButton showerButton = new JButton("Shower");
        showerButton.setBounds(375, 50, 100, 30);
        showerButton.setPreferredSize(new Dimension(80, 30));
        frame.add(showerButton);

        showerButton.addActionListener(e -> {
            //TODO: Allow for changing value here

            // Disable the button to prevent multiple clicks
            showerButton.setEnabled(false);

            ImageIcon feedingImage = new ImageIcon("src/main/java/softwaredesign/images/chicken.png");

            // Create a label with the first feeding image
            JLabel feedingLabel = new JLabel(feedingImage);

            int initialYPosition = 30;

            feedingLabel.setBounds(200, initialYPosition, feedingImage.getIconWidth(), feedingImage.getIconHeight());

            frame.add(feedingLabel);

            // Create a timer to change the feeding image and animate the falling
            Timer timer = new Timer(50, null); // Decreased the delay for smoother animation
            timer.addActionListener(new ActionListener() {
                int index = 0;
                int count = 0;
                int maxCount = 8; // Set the maximum count based on frame height
                int yPosition = initialYPosition;

                public void actionPerformed(ActionEvent e) {
                    count++;

                    // Update the position of the feedingLabel
                    yPosition += 18;
                    feedingLabel.setLocation(200, yPosition);

                    // Stop the timer automatically when the count reaches the maximum
                    if (count >= maxCount) {
                        timer.stop();
                        // Remove the feedingLabel from the frame, revalidate, and repaint the frame
                        frame.remove(feedingLabel);
                        frame.revalidate();
                        frame.repaint();

                        // Enable the button again
                        showerButton.setEnabled(true);
                    }
                }

            });

            // Start the timer
            timer.start();
        });

        // Create the Pee Button
        JButton peeButton = new JButton("Pee");
        peeButton.setBounds(375, 90, 100, 30);
        peeButton.setPreferredSize(new Dimension(80, 30));
        frame.add(peeButton);
        peeButton.addActionListener(e -> System.out.println("Pee pressed."));

        // Create the Drink Button
        JButton drinkButton = new JButton("Drink");
        drinkButton.setBounds(375, 130, 100, 30);
        drinkButton.setPreferredSize(new Dimension(80, 30));
        frame.add(drinkButton);
        drinkButton.addActionListener(e -> System.out.println("Drink pressed."));

        // Create the Minigame Button
        JButton minigameButton = new JButton("Minigame");
        minigameButton.setBounds(375, 170, 100, 30);
        minigameButton.setPreferredSize(new Dimension(80, 30));
        frame.add(minigameButton);
        minigameButton.addActionListener(e -> System.out.println("Minigame pressed."));

        // Display the window
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        runGUI();

    }

}





