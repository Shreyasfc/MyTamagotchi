package softwaredesign.gui;

import java.io.IOException;

interface ProgressBarObserver {
    void update(int newValue) throws IOException;
}
