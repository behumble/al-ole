package net.hanjava.alole.ui;

import net.hanjava.util.CheckableState;
import net.hanjava.util.CheckableState.Listener;

public class InvertedCheckableState extends CheckableState implements Listener {
    private CheckableState sourceCheckable;
    
    public InvertedCheckableState(CheckableState sourceCheckable) {
        this.sourceCheckable = sourceCheckable;
        sourceCheckable.addListener( this );
    }
    
    protected boolean checkSatisfied() {
        return !sourceCheckable.isSatisfied();
    }

    public void needToUpdate(CheckableState checkable) {
        fireNotification();
    }
}