package softwaredesign.gui;

import softwaredesign.FootballerDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private int carouselCurrentImageIndex = 0;

    @Override
    public void customizeGUI(JFrame frame) {

        addImageCarousel(frame);
        addTextAboveCarousel(frame);

    }

    private String getCurrentCarouselImagePath(){

        return footballerImages[carouselCurrentImageIndex];

    }

    private void addImageCarousel(JFrame frame) {

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(75, 75, 350, 350);
        frame.getContentPane().add(imagePanel);

        JLabel imageLabel = new JLabel();
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        showSelectableFootballerModel(imageLabel, carouselCurrentImageIndex);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                footballerDisplayer.setChosenFootballer(footballerDisplayer.getFootballerNameByImagePath(getCurrentCarouselImagePath()));
                try {
                    onGUIClosure(frame);
                } catch (IOException ex) {
                    throw new GuiCloseException("Failed to close GUI", ex);
                }

            }

        });

        JButton leftButton = addLeftButton(imageLabel);
        imagePanel.add(leftButton, BorderLayout.WEST);

        JButton rightButton = addRightButton(imageLabel);
        imagePanel.add(rightButton, BorderLayout.EAST);

    }

    private JButton addLeftButton(JLabel imageLabel) {

        JButton leftButton = new JButton("<");
        leftButton.addActionListener(e -> {
            carouselCurrentImageIndex = (carouselCurrentImageIndex - 1 + footballerImages.length) % footballerImages.length;
            showSelectableFootballerModel(imageLabel, carouselCurrentImageIndex);
        });
        return leftButton;

    }

    private JButton addRightButton(JLabel imageLabel) {

        JButton rightButton = new JButton(">");
        rightButton.addActionListener(e -> {
            carouselCurrentImageIndex = (carouselCurrentImageIndex + 1) % footballerImages.length;
            showSelectableFootballerModel(imageLabel, carouselCurrentImageIndex);
        });
        return rightButton;

    }

    private void onGUIClosure(JFrame frame) throws IOException {

        frame.dispose();
        onGuiClosedCallback.onGuiClosed();

    }

    private void showSelectableFootballerModel(JLabel imageLabel, int index) {

        Image image = new ImageIcon(footballerImages[index]).getImage();
        imageLabel.setIcon(new ImageIcon(image));

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
