package softwaredesign.gui;

import softwaredesign.FootballerDisplayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GUISelectChar implements GUI {

    private final OnGuiClosedCallback onGuiClosedCallback;

    private final String[] footballerImages;

    private final FootballerDisplayer footballerDisplayer;

    public GUISelectChar(FootballerDisplayer footballerDisplayer, OnGuiClosedCallback onGuiClosedCallback) {
        this.footballerDisplayer = footballerDisplayer;
        this.footballerImages = footballerDisplayer.getAllFootballerModelsImagePath();
        this.onGuiClosedCallback = onGuiClosedCallback;
    }

    private int currentImageIndex = 0;


    @Override
    public void customizeGUI(JFrame frame) {

        createImageCarousel(frame);
        addTextAboveCarousel(frame);

    }

    private void createImageCarousel(JFrame frame) {

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(75, 75, 350, 350);
        frame.getContentPane().add(imagePanel);

        JLabel imageLabel = new JLabel();
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        setImage(imageLabel, currentImageIndex);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                footballerDisplayer.setChosenFootballer(footballerDisplayer.getOneFootballerName(footballerImages[currentImageIndex]));
                try {
                    onGUIClosure(frame);
                } catch (IOException ex) {
                    throw new GuiCloseException("Failed to close GUI", ex);
                }

            }

        });

        JButton leftButton = createLeftButton(imageLabel);
        imagePanel.add(leftButton, BorderLayout.WEST);

        JButton rightButton = createRightButton(imageLabel);
        imagePanel.add(rightButton, BorderLayout.EAST);

    }

    private JButton createLeftButton(JLabel imageLabel) {

        JButton leftButton = new JButton("<");
        leftButton.addActionListener(e -> {
            currentImageIndex = (currentImageIndex - 1 + footballerImages.length) % footballerImages.length;
            setImage(imageLabel, currentImageIndex);
        });
        return leftButton;

    }

    private JButton createRightButton(JLabel imageLabel) {

        JButton rightButton = new JButton(">");
        rightButton.addActionListener(e -> {
            currentImageIndex = (currentImageIndex + 1) % footballerImages.length;
            setImage(imageLabel, currentImageIndex);
        });
        return rightButton;

    }

    private void onGUIClosure(JFrame frame) throws IOException {

        frame.dispose();
        onGuiClosedCallback.onGuiClosed();

    }

    private void setImage(JLabel imageLabel, int index) {

        try {
            Image image = ImageIO.read(new File(footballerImages[index]));
            imageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addTextAboveCarousel(JFrame frame) {

        JLabel textLabel = new JLabel("Select your character. But remember: The choice is irreversible!");
        textLabel.setBounds(15, 35, 470, 25);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        frame.getContentPane().add(textLabel);

    }

}
