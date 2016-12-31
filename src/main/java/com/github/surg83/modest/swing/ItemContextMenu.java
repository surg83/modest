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

import com.github.surg83.modest.Controller;
import com.github.surg83.modest.Project;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * ItemContextMenu class.
 *
 * Item context menu.
 *
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ItemContextMenu extends JPopupMenu {

    /**
     * Action controller.
     */
    private final Controller ctrl;

    /**
     * Project.
     */
    private final Project project;

    /**
     * ItemContextMenu class constructor.
     *
     * @param ctrl
     * @param project
     */
    public ItemContextMenu(Controller ctrl, Project project) {
        super();
        this.ctrl = ctrl;
        this.project = project;
    }

    /**
     * Setup context menu items.
     */
    public void setup() {
        JMenuItem item;

        JMenu menuAdd = new JMenu("Add new");

        String[] itemTypes = project.getItemTypes();
        for (String itemType : itemTypes) {
            item = new ItemContextMenuItem(itemType);
            item.setActionCommand(Controller.ACTION_ITEM_ADD);
            item.addActionListener(ctrl);
            menuAdd.add(item);
        }
        add(menuAdd);

        item = new JMenuItem("Delete");
        item.setActionCommand(Controller.ACTION_ITEM_REMOVE);
        item.addActionListener(ctrl);
        add(item);
    }

}
