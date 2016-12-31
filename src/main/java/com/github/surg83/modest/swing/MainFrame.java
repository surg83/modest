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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 * MainFrame class.
 * 
 * Application main frame.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class MainFrame extends JFrame {
    
    /**
     * Frame size.
     */
    private final Dimension frameSize;
    
    /**
     * Main menu.
     */
    private final MainMenu mainMenu;
    
    /**
     * Toolbar menu.
     */
    private final ToolBarMenu toolBarMenu;
    
    /**
     * Item context menu.
     */
    private final ItemContextMenu itemContextMenu;
    
    /**
     * Tree view.
     */
    private final TreeView tree;
    
    /**
     * Tabbed view.
     */
    private final TabbedView tabs;
    
    /**
     * Status bar.
     */
    private final StatusBar statusBar;

    /**
     * MainFrame class constructor.
     * 
     * @param title
     * @param frameSize
     * @param mainMenu
     * @param toolBarMenu
     * @param itemContextMenu
     * @param tree
     * @param tabs
     * @param statusBar
     */
    public MainFrame(String title, Dimension frameSize,
            MainMenu mainMenu, ToolBarMenu toolBarMenu,
            ItemContextMenu itemContextMenu, TreeView tree,
            TabbedView tabs, StatusBar statusBar) {
        super(title);
        this.frameSize = frameSize;
        this.mainMenu = mainMenu;
        this.toolBarMenu = toolBarMenu;
        this.itemContextMenu = itemContextMenu;
        this.tree = tree;
        this.tabs = tabs;
        this.statusBar = statusBar;
    }
    
    /**
     * Setup main frame.
     */
    public void setup() {
        Container contentPane = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    
        setPreferredSize(frameSize);
        setLocationByPlatform(true);

        mainMenu.setup();
        setJMenuBar(mainMenu);
        
        toolBarMenu.setup();
        contentPane.add(toolBarMenu, BorderLayout.NORTH);

        itemContextMenu.setup();
        tree.setContextMenu(itemContextMenu);
        tree.setup();
        
        tabs.setup();

        JSplitPane split = getSplitPane(tree, tabs);
        contentPane.add(split, BorderLayout.CENTER);
        
        statusBar.setup();
        contentPane.add(statusBar, BorderLayout.SOUTH);
        
        pack(); 
    }

    /**
     * Get split pane.
     * 
     * @param tree
     * @param tabs
     * @return
     */
    private JSplitPane getSplitPane(TreeView tree, TabbedView tabs) {
        JScrollPane left = new JScrollPane();
        left.setViewportView(tree);
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(left);
        split.setRightComponent(tabs);
        split.setDividerLocation(frameSize.width / 4);
        return split;
    }
}
