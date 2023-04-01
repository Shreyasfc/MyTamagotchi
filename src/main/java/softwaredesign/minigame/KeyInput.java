package softwaredesign.minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyInput extends JPanel {
    private final Players p;
    public KeyInput(Players players) {
        add(new JButton("Foo")); // For consistency
        KeyLis listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
        this.p = players;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 200);
    }

    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            p.parseUserInput(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            p.processUserInput(e);
        }
    }

}