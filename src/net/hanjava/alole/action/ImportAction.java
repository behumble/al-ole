package net.hanjava.alole.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.hanjava.alole.MainPanel;
import net.hanjava.alole.util.FileSystemConverter;
import net.hanjava.util.CheckableState;
import net.hanjava.util.HanAbstractAction;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ImportAction extends HanAbstractAction {
    private MainPanel mainPanel;

    public ImportAction(MainPanel mainPanel, CheckableState[] checks) {
        super("Import", checks);
        this.mainPanel = mainPanel;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser openChooser = new JFileChooser();
        int response = openChooser.showOpenDialog(mainPanel);
        if (response == JFileChooser.APPROVE_OPTION) {
            openRoot(openChooser.getCurrentDirectory());
        }
    }

    /** Root 디렉토리의 Meta Info File을 읽는다 */
    private void openRoot(File rootDir) {
        POIFSFileSystem poifs = null;
        try {
            poifs = FileSystemConverter.toOLEFileSystem(rootDir.toString());
            mainPanel.setModel(poifs, rootDir.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, e.getMessage(), "Shit!!",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected String getLongDescription() {
        return null;
    }

    @Override
    protected String getShortDescription() {
        return "디렉토리 임포트";
    }
}