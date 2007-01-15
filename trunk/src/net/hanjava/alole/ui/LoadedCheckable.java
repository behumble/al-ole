package net.hanjava.alole.ui;

import net.hanjava.alole.MainPanel;
import net.hanjava.util.CheckableState;

public class LoadedCheckable extends CheckableState {
    private MainPanel mainPanel;

    public LoadedCheckable(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    protected boolean checkSatisfied() {
        return mainPanel.getView() != null;
    }
}