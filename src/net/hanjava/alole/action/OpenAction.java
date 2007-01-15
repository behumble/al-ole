package net.hanjava.alole.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.hanjava.alole.MainPanel;
import net.hanjava.util.CheckableState;
import net.hanjava.util.HanAbstractAction;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class OpenAction extends HanAbstractAction {
    private MainPanel mainPanel;

    public OpenAction(MainPanel alole, CheckableState[] checks) {
        super("Open", checks);
        this.mainPanel = alole;
    }

    public void actionPerformed(ActionEvent e) {
        File compoundFile = chooseFile();
        if (compoundFile != null) {
            openFile(compoundFile);
        }
    }

    public void openFile(File compoundFile) {
        POIFSFileSystem model = load(compoundFile);
        if (model != null) {
            mainPanel.setModel(model, compoundFile.toString());
        }
    }

    private POIFSFileSystem load(File file) {
        FileInputStream fis = null;
        POIFSFileSystem oleFS = null;
        try {
            fis = new FileInputStream(file);
            oleFS = new POIFSFileSystem(fis);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainPanel, e.getMessage(),
                    "Open Failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return oleFS;
    }

    private File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(mainPanel);
        return chooser.getSelectedFile();
    }

    @Override
    protected String getLongDescription() {
        return null;
    }

    @Override
    protected String getShortDescription() {
        return "컴파운드 문서(doc,xls,ppt) 읽기";
    }
}