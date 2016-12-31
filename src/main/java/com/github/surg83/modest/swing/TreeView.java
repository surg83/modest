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

package com.github.surg83.modest.swing;

import com.github.surg83.modest.project.ProjectItem;
import com.github.surg83.modest.project.ProjectTreeNode;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 * TreeView class.
 * 
 * Tree view for project structure.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class TreeView extends JTree {
    
    /**
     * Context menu.
     */
    private ItemContextMenu contextMenu;

    /**
     * Get context menu.
     * 
     * @return ItemContextMenu
     */
    public ItemContextMenu getContextMenu() {
        return contextMenu;
    }

    /**
     * Set context menu.
     *
     * @param contextMenu 
     */
    public void setContextMenu(ItemContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }
    
    /**
     * Controller.
     */
    private final MouseListener ctrl;

    /**
     * TreeView class constructor.
     *
     * @param ctrl 
     */
    public TreeView(MouseListener ctrl) {
        this.ctrl = ctrl;
    }
    
    /**
     * Setup tree view.
     */
    public void setup() {
        getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        setExpandsSelectedPaths(true);
        setToggleClickCount(0);
        setModel(null);
        addMouseListener(ctrl);
        setCellRenderer(new TreeViewCellRenderer());
    }
    
    /**
     * TreeViewCellRenderer class.
     * 
     * Tree view cell renderer.
     */
    private class TreeViewCellRenderer extends DefaultTreeCellRenderer {
        
        private final HashMap<String, ImageIcon> icons = new HashMap<>();

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf,
                int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value,
                    sel, expanded, leaf, row, hasFocus);
             
            ProjectTreeNode node = (ProjectTreeNode) value;
            ProjectItem item = node.getUserObject();
            String itemType = item.getType();
            if (icons.containsKey(itemType)) {
                setIcon(icons.get(itemType));
            } else {
                String iconRes = item.getIcon();
                if (iconRes != null) {
                    ImageIcon icon
                            = new ImageIcon(getClass().getResource(iconRes));
                    setIcon(icon);
                    icons.put(itemType, icon);
                }
            }

            return this;
        }
    }
}
