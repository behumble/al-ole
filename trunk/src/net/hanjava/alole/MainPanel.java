package net.hanjava.alole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import net.hanjava.alole.action.CloseAction;
import net.hanjava.alole.action.SaveAction;
import net.hanjava.alole.action.OpenAction;
import net.hanjava.alole.action.ImportAction;
import net.hanjava.alole.action.ExportAction;
import net.hanjava.alole.ui.InvertedCheckableState;
import net.hanjava.alole.ui.LoadedCheckable;
import net.hanjava.alole.view.FileView;
import net.hanjava.util.CheckableState;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MainPanel extends JPanel {
    private JToolBar toolBar;

    private FileView view;

    private CheckableState loadedCheckable;

    public MainPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        toolBar = new JToolBar();
        initActions();
        initToolbar();
        add(BorderLayout.NORTH, toolBar);
        loadedCheckable.fireNotification();
    }

    private void initToolbar() {
        ActionMap am = getActionMap();
        Object[] keys = am.allKeys();
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            Action action = am.get(key);
            toolBar.add(action);
        }
    }

    private void initActions() {
        // Checkable 초기화
        loadedCheckable = new LoadedCheckable(this);
        CheckableState unloadedCheckable = new InvertedCheckableState(
                loadedCheckable);

        registerAction(new OpenAction(this,
                new CheckableState[] { unloadedCheckable }));
        registerAction(new ImportAction(this,
                new CheckableState[] { unloadedCheckable }));
        registerAction(new SaveAction(this,
                new CheckableState[] { loadedCheckable }));
        registerAction(new ExportAction(this,
                new CheckableState[] { loadedCheckable }));
        registerAction(new CloseAction(this,
                new CheckableState[] { loadedCheckable }));
    }

    private void registerAction(Action action) {
        getActionMap().put(action.getValue(Action.NAME), action);
    }

    public void setModel(POIFSFileSystem model, String path) {
        if (view != null) {
            remove(view);
        }

        if (model == null) {
            view = null;
        } else {
            if (view == null) {
                view = new FileView(model, path);
            }
            add(BorderLayout.CENTER, view);            
        }

        revalidate();
        repaint();
        loadedCheckable.fireNotification();
    }

    public FileView getView() {
        return view;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("알 OLE");
        frame.setSize(500, 500);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel alOLE = new MainPanel();
        frame.getContentPane().add(alOLE);
        if (args.length == 1) {
            OpenAction openAction = (OpenAction) alOLE.getActionMap().get(
                    "Open");
            openAction.openFile(new File(args[0]));
        }
        frame.setVisible(true);
    }
}