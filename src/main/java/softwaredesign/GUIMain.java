package softwaredesign;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//TODO:
//- Do all the todos
//- More immutable
//- Scale with size increase

public class GUIMain {

    public static int SCREENWIDTH = 500;
    public static int SCREENHEIGHT = 500;

    public static void main(String[] args) {

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
        JLabel label = new JLabel(characterIcon);
        label.setBounds(135, 175, 250, 250);
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label);

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

        // Create a label to show XP
        //TODO: Allow for changing value here
        JLabel xpLabel = new JLabel("XP: 0");
        xpLabel.setBounds(25, 170, 50, 20);
        Font font = new Font("Arial", Font.BOLD, 12);
        xpLabel.setFont(font);
        xpLabel.setOpaque(true);
        xpLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        xpLabel.setBackground(Color.WHITE);
        xpLabel.setForeground(Color.BLACK);
        xpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(xpLabel);

        // Create a label to show Coin
        //TODO: Allow for changing value here
        JLabel coinLabel = new JLabel("Coin: 0");
        coinLabel.setBounds(90, 170, 60, 20);
        coinLabel.setFont(font);
        coinLabel.setOpaque(true);
        coinLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        coinLabel.setBackground(Color.WHITE);
        coinLabel.setForeground(Color.BLACK);
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(coinLabel);

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
        showerButton.addActionListener(e -> System.out.println("Shower Pressed."));

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

        // Create the Shop Button
        JButton shopButton = new JButton("Shop");
        shopButton.setBounds(375, 170, 100, 30);
        shopButton.setPreferredSize(new Dimension(80, 30));
        frame.add(shopButton);
        shopButton.addActionListener(e -> System.out.println("Shop pressed."));

        // Create the Feeding Button
        JButton minigameButton = new JButton("Minigame");
        minigameButton.setBounds(375, 210, 100, 30);
        minigameButton.setPreferredSize(new Dimension(80, 30));
        frame.add(minigameButton);
        minigameButton.addActionListener(e -> System.out.println("Minigame pressed."));

        // Display the window
        frame.setVisible(true);
    }

}





