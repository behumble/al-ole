package net.hanjava.alole.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.SystemColor;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Compound File 하나에 해당하는 View. 상단은 Short Description, 좌측은 Tree, 우측은 StreamView
 */
public class FileView extends JPanel {
    private POIFSFileSystem model;
    private String path;

    private JSplitPane splitPane;

    public FileView(POIFSFileSystem model, String path) {
        this.model = model;
        this.path = path;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(path));
        // 상단 Label 만들기
        JLabel descriptionLabel = new JLabel(model.getShortDescription());
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(SystemColor.activeCaption);
        descriptionLabel.setForeground(SystemColor.activeCaptionText);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(BorderLayout.NORTH, descriptionLabel);

        JTree tree = createTree(model);
        JScrollPane treeScroller = new JScrollPane(tree);

        JLabel nodeView = new JLabel("Right");
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroller, nodeView);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight( 0.4 );
        add(BorderLayout.CENTER, splitPane);
        tree.setSelectionRow(0);
    }

    private JTree createTree(POIFSFileSystem model) {
        TreeModel treeModel = buildTreeModel(model);
        final JTree tree = new JTree(treeModel);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                OLENode node = (OLENode) tree.getLastSelectedPathComponent();
                Component nodeView = createNodeView(node);
                splitPane.setRightComponent(nodeView);
            }
        });

        return tree;
    }

    private TreeModel buildTreeModel(POIFSFileSystem fs) {
        OLENode rootNode = buildNodeTree(fs.getRoot(), null);
        TreeModel treeModel = new DefaultTreeModel(rootNode);
        return treeModel;
    }

    private OLENode buildNodeTree(Entry entry, TreeNode parent) {
        OLENode node = new OLENode(entry, parent);
        if (entry instanceof DirectoryEntry) {
            DirectoryEntry dirEntry = (DirectoryEntry) entry;
            int childrenCount = dirEntry.getEntryCount();
            for (Iterator it = dirEntry.getEntries(); it.hasNext();) {
                Entry child = (Entry) it.next();
                OLENode childNode = buildNodeTree(child, node);
                node.addChild(childNode);
            }
        }
        return node;
    }

    private Component createNodeView(OLENode node) {
        JComponent result = null;
        Entry entry = node.getEntry();
        if (entry instanceof DirectoryNode) {
            DirectoryNode dirNode = (DirectoryNode) entry;
            result = new DirectoryNodeView(dirNode);
        } else if (entry instanceof DocumentNode) {
            DocumentNode docEntry = (DocumentNode) entry;
            result = new DocumentNodeView(docEntry);
        } else {
            throw new IllegalArgumentException(node.toString());
        }
        return result;
    }
    
    public POIFSFileSystem getFileSystem() {
        return model;
    }
    
    public String getPath() {
        return path;
    }
}