package net.hanjava.alole.view;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import net.hanjava.alole.util.Utility;

import org.apache.poi.poifs.filesystem.DirectoryNode;

public class DirectoryNodeView extends JComponent {
    private DirectoryNode dirNode;
    public DirectoryNodeView(DirectoryNode dirNode) {
        this.dirNode = dirNode;
        setLayout( new BoxLayout(this, BoxLayout.Y_AXIS ));
        add( Utility.createLabel("Name", dirNode.getName()) );
        add( Utility.createLabel("Storage Class ID", dirNode.getStorageClsid().toString()) );
        String path = Utility.getNodePath( dirNode );
        add( Utility.createLabel("Path", path) );
    }
}