/**
 * MIT License
 * Copyright (c) 2016 Michal Czudowski
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.surg83.modest;

import com.github.surg83.modest.project.ProjectItem;
import com.github.surg83.modest.project.ProjectTreeModel;
import com.github.surg83.modest.project.ProjectTreeNode;
import com.github.surg83.modest.swing.ItemContextMenu;
import com.github.surg83.modest.swing.ItemContextMenuItem;
import com.github.surg83.modest.swing.MainFrame;
import com.github.surg83.modest.swing.MainMenu;
import com.github.surg83.modest.swing.StatusBar;
import com.github.surg83.modest.swing.TabbedView;
import com.github.surg83.modest.swing.ToolBarMenu;
import com.github.surg83.modest.swing.TreeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Controller class.
 * 
 * Application controller.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class Controller implements ActionListener, MouseListener {
    
    /**
     * Events logger.
     */
    private final static Logger LOGGER
            = Logger.getLogger(Controller.class.getName());
    
    /**
     * Action "New project".
     */
    public final static String ACTION_PROJECT_NEW = "project_new";
    
    /**
     * Action "Open project".
     */
    public final static String ACTION_PROJECT_OPEN = "project_open";
    
    /**
     * Action "Save project".
     */
    public final static String ACTION_PROJECT_SAVE = "project_save";
    
    /**
     * Action "Save document".
     */
    public final static String ACTION_DOCUMENT_EXPORT = "document_save";
    
    /**
     * Action "Add item".
     */
    public final static String ACTION_ITEM_ADD = "item_add";

    /**
     * Action "Remove item".
     */
    public final static String ACTION_ITEM_REMOVE = "item_remove";
    
    /**
     * Action "Exit app".
     */
    public final static String ACTION_EXIT = "exit";
    
    /**
     * Action "About app".
     */
    public final static String ACTION_ABOUT = "about";
    
    /**
     * Configuration.
     */
    private final Config config;

    /**
     * Project.
     */
    private final Project project;
    
    /**
     * Document.
     */
    private final Document document;
    
    /**
     * Tree view.
     */
    private TreeView tree;
    
    /**
     * Tabbed view.
     */
    private TabbedView tabs;
        
    /**
     * Controller class constructor.
     * 
     * @param config
     * @param project
     * @param document
     */
    public Controller(Config config, Project project, Document document) {
        this.config = config;
        this.project = project;
        this.document = document;
    }
    
    /**
     * Run controller.
     */
    public void run() {
        tabs = new TabbedView();
        tree = new TreeView(this);

        MainFrame mainFrame = new MainFrame(
                config.getAppName(), config.defaultWindowSize,
                new MainMenu(this), new ToolBarMenu(this),
                new ItemContextMenu(this, project), tree, tabs,
                new StatusBar(config.getAppName()
                        + " " + config.getAppVersion()));
        mainFrame.setup();
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case ACTION_PROJECT_NEW:
                    actionProjectNew(e);
                    break;
                case ACTION_PROJECT_OPEN:
                    actionProjectOpen(e);
                    break;
                case ACTION_PROJECT_SAVE:
                    actionProjectSave(e);
                    break;
                case ACTION_DOCUMENT_EXPORT:
                    actionDocumentExport(e);
                    break;
                case ACTION_ITEM_ADD:
                    actionItemAdd(e);
                    break;
                case ACTION_ITEM_REMOVE:
                    actionItemRemove(e);
                    break;
                case ACTION_EXIT:
                    actionExit(e);
                    break;
                case ACTION_ABOUT:
                    actionAbout(e);
                    break;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }
    
    /**
     * Action "New project".
     *
     * @param e
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private void actionProjectNew(ActionEvent e)
            throws ParserConfigurationException, SAXException, IOException {
        InputSource templateSource = new InputSource(getClass()
                .getResourceAsStream("/com/github/surg83/modest/project.xml"));
        ProjectTreeModel model = project.openTemplate(templateSource);
        tree.setModel(model);
        tabs.removeAll();
    }
    
    /**
     * Action "Open project".
     * 
     * @param e
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private void actionProjectOpen(ActionEvent e)
            throws ParserConfigurationException, SAXException, IOException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(config.defaultFilepath));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ProjectTreeModel model = project.open(file.getAbsolutePath());
            tree.setModel(model);
            tabs.removeAll();
        }
    }

    /**
     * Action "Save project".
     * 
     * @param e
     * @throws ParserConfigurationException
     * @throws TransformerException 
     * @throws IOException 
     */
    private void actionProjectSave(ActionEvent e)
            throws ParserConfigurationException, TransformerException,
            IOException {
        String projectPath = project.getPath();
        if (projectPath == null) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(config.defaultFilepath));
            int returnVal = fc.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                project.save(file.getAbsolutePath());
            }
        } else {
            project.save();
        }
    }
    
    /**
     * Action "Document save".
     * 
     * @param e 
     * @throws Exception
     */
    private void actionDocumentExport(ActionEvent e) throws Exception {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(config.defaultFilepath));
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            document.export(project, file.getAbsolutePath());
        }
    }
    
    /**
     * Action "Add item".
     * 
     * @param e 
     */
    private void actionItemAdd(ActionEvent e) {
        ProjectTreeNode currentNode = null;
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            currentNode = (ProjectTreeNode) path.getLastPathComponent();
        }
        ItemContextMenuItem menuItem = (ItemContextMenuItem) e.getSource();
        String itemType = menuItem.getItemType();
        ProjectTreeNode childNode = project.addItem(itemType, currentNode);
        TreePath scrollPath = new TreePath(childNode.getPath());
        tree.scrollPathToVisible(scrollPath);
    }
    
    /**
     * Action "Remove item".
     * 
     * @param e 
     */
    private void actionItemRemove(ActionEvent e) {
        ProjectTreeNode currentNode;
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            currentNode = (ProjectTreeNode) path.getLastPathComponent();
            project.removeItem(currentNode);
        }
    }
    
    /**
     * Action "Exit".
     * 
     * @param e 
     */
    private void actionExit(ActionEvent e) {
        System.exit(0);
    }
    
    /**
     * Action "About".
     * 
     * @param e 
     */
    private void actionAbout(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                config.getAppName()+ " " + config.getAppVersion(),
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            TreeView treeSrc = (TreeView) e.getSource();
            if (treeSrc != null) {
                TreePath path 
                        = treeSrc.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    ProjectTreeNode node
                            = (ProjectTreeNode) path.getLastPathComponent();
                    ProjectItem item = node.getUserObject();
                    try {
                        item = project.loadItemFields(item);
                    } catch (ParserConfigurationException
                            | SAXException
                            | IOException ex) {
                        LOGGER.log(Level.FINE, ex.toString(), ex);
                    }
                    tabs.addTabFromProjectItem(item);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        TreeView treeSrc = (TreeView) e.getSource();
        if (treeSrc != null && e.isPopupTrigger()) {
            TreePath path = treeSrc.getPathForLocation(e.getX(), e.getY());
            if (path != null) {
                treeSrc.setSelectionPath(path);
                ItemContextMenu contextMenu = treeSrc.getContextMenu();
                if (contextMenu != null) {
                    contextMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
