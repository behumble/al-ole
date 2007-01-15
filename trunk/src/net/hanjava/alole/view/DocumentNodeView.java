package net.hanjava.alole.view;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import net.hanjava.alole.util.Utility;

import org.apache.poi.poifs.filesystem.DocumentNode;

public class DocumentNodeView extends JComponent {
    private DocumentNode docNode;

    public DocumentNodeView(DocumentNode docNode) {
        this.docNode = docNode;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Utility.createLabel("Name", docNode.getName()));
        add(Utility.createLabel("Size", ""+docNode.getSize()));
        String path = Utility.getNodePath(docNode);
        add(Utility.createLabel("Path", path));
    }
}
