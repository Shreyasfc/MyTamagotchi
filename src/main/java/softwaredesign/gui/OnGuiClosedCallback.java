package softwaredesign.gui;

import java.io.IOException;

@FunctionalInterface
public interface OnGuiClosedCallback {
    void onGuiClosed() throws IOException;

}

