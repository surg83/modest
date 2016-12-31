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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * TabbedView class.
 * 
 * Tabbed view.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class TabbedView extends JTabbedPane {
    
    /**
     * Tab action controller.
     */
    private final TabActionController ctrl = new TabActionController(this);

    /**
     * Setup tabbed view.
     */
    public void setup() {
        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
    }
    
    /**
     * Add tab from project item.
     *
     * @param item 
     */
    public void addTabFromProjectItem(ProjectItem item) {
        int tabSelectedIdx = indexOfTab(item.getId());
        if (tabSelectedIdx == -1) {
            ItemTab tab = getTab(item);
            TabComponent tabComponent = getTabComponent(item);
            add(item.getId(), tab);
            tabSelectedIdx = indexOfTab(item.getId());
            setTabComponentAt(tabSelectedIdx, tabComponent);
        }
        setSelectedIndex(tabSelectedIdx);
    }

    /**
     * Get tab.
     * 
     * @param item
     * 
     * @return ItemTab
     */
    private ItemTab getTab(ProjectItem item) {
        ItemTab tab = new ItemTab(item);
        tab.setup();
        return tab;
    }

    /**
     * Get tab component.
     * 
     * @param item
     * 
     * @return TabComponent
     */
    private TabComponent getTabComponent(ProjectItem item) {
        JLabel label = new JLabel(item.getName());
        TabCloseButton button = new TabCloseButton(ctrl);
        TabComponent tabComponent = new TabComponent(label, button);
        tabComponent.setup();
        return tabComponent;
    }
    
    /**
     * TabComponent class.
     * 
     * Custom tab component.
     */
    private class TabComponent extends JPanel {

        /**
         * Label.
         */
        private final JLabel label;
        
        /**
         * Button.
         */
        private final TabCloseButton button;
        
        /**
         * TabComponent class constructor.
         * 
         * @param label
         * @param button 
         */
        public TabComponent(JLabel label, TabCloseButton button) {
            super(new FlowLayout(FlowLayout.LEFT, 5, 3));
            this.label = label;
            this.button = button;
        }

        /**
         * Setup tab component.
         */
        public void setup() {
            setOpaque(false);
            add(label);
            button.setup();
            add(button);
        }
    }
    
    /**
     * TabCloseButton class.
     * 
     * Tab close button.
     */
    private class TabCloseButton extends JButton {
        
        /**
         * Tab action controller.
         */
        private final TabActionController ctrl;

        /**
         * TabCloseButton class constructor.
         * 
         * @param ctrl 
         */
        public TabCloseButton(TabActionController ctrl) {
            this.ctrl = ctrl;
        }

        /**
         * Setup tab close button.
         */
        public void setup() {
            int size = 15;
            setText("x");
            setPreferredSize(new Dimension(size, size));
            setMargin(new Insets(0, 0, 0, 0));
            setToolTipText("Close tab");
            //setContentAreaFilled(false);
            setFocusable(false);
            addActionListener(ctrl);
        }
    }
    
    /**
     * TabActionController class.
     * 
     * Tab action controleler.
     */
    private class TabActionController implements ActionListener {
        
        /**
         * Tabbed view.
         */
        private final TabbedView tabs;

        /**
         * TabActionController class constructor.
         *
         * @param tabs 
         */
        public TabActionController(TabbedView tabs) {
            this.tabs = tabs;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            TabCloseButton button = (TabCloseButton) e.getSource();
            TabComponent tabComponent = (TabComponent) button.getParent();
            tabs.remove(tabs.indexOfTabComponent(tabComponent));
        }
    }
}
