package softwaredesign;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISelectChar {

    private static final String[] imageFiles = {
            "src/main/java/softwaredesign/images/cristianobasic.png",
            "src/main/java/softwaredesign/images/messibasic.png"
    };
    private static int currentImageIndex = 0;

    public static void runGUI() {

        JFrame frame = createWindow();

        try {
            setContentPaneWithImage(frame);
            createImageCarousel(frame);
            addTextAboveCarousel(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

    }

    private static JFrame createWindow() {
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        return frame;
    }

    private static void setContentPaneWithImage(JFrame frame) throws IOException {
        Image backgroundImage = ImageIO.read(new File("src/main/java/softwaredesign/images/background.png"));
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        frame.setContentPane(backgroundLabel);
    }

    private static void createImageCarousel(JFrame frame) throws IOException {
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(75, 75, 350, 350);
        frame.getContentPane().add(imagePanel);
        imagePanel.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        setImage(imageLabel, currentImageIndex);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle the click event here
                System.out.println("Image clicked: " + imageFiles[currentImageIndex]);
            }
        });

        JButton leftButton = new JButton("<");
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex--;
                if (currentImageIndex < 0) {
                    currentImageIndex = imageFiles.length - 1;
                }
                setImage(imageLabel, currentImageIndex);
            }
        });
        imagePanel.add(leftButton, BorderLayout.WEST);

        JButton rightButton = new JButton(">");
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex++;
                if (currentImageIndex >= imageFiles.length) {
                    currentImageIndex = 0;
                }
                setImage(imageLabel, currentImageIndex);
            }
        });
        imagePanel.add(rightButton, BorderLayout.EAST);
    }

    private static void setImage(JLabel imageLabel, int index) {
        try {
            Image image = ImageIO.read(new File(imageFiles[index]));
            imageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTextAboveCarousel(JFrame frame) {
        JLabel textLabel = new JLabel("Select your character. But remember: The choice is irreversible!");
        textLabel.setBounds(15, 35, 470, 25);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        frame.getContentPane().add(textLabel);
    }

    public static void main(String[] args) {

        runGUI();

    }

}
