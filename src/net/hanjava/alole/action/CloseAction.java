package net.hanjava.alole.action;

import java.awt.event.ActionEvent;

import net.hanjava.alole.MainPanel;
import net.hanjava.util.CheckableState;
import net.hanjava.util.HanAbstractAction;

public class CloseAction extends HanAbstractAction {
    private MainPanel mainPanel;

    public CloseAction(MainPanel mainPanel, CheckableState[] checks) {
        super("Close", checks);
        this.mainPanel = mainPanel;
    }

    @Override
    protected String getLongDescription() {
        return null;
    }

    @Override
    protected String getShortDescription() {
        return "Close";
    }

    public void actionPerformed(ActionEvent e) {
        mainPanel.setModel( null, null );
    }
}