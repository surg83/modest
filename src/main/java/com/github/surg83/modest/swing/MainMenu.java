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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * MainMenu class.
 * 
 * Main menu.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class MainMenu extends JMenuBar {
    
    /**
     * Action controller.
     */
    private final Controller ctrl;
    
    /**
     * MainMenu class constructor.
     * 
     * @param ctrl 
     */
    public MainMenu(Controller ctrl) {
        super();
        this.ctrl = ctrl;
    }
    
    /**
     * Setup main menu.
     */
    public void setup() {
        JMenu fileMenu = new JMenu();
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.add(getMainMenuItem("New", 'n',
                Controller.ACTION_PROJECT_NEW));
        fileMenu.add(getMainMenuItem("Open", 'o',
                Controller.ACTION_PROJECT_OPEN));
        fileMenu.add(getMainMenuItem("Save", 's',
                Controller.ACTION_PROJECT_SAVE));
        fileMenu.add(getMainMenuItem("Exit", 'x',
                Controller.ACTION_EXIT));
        add(fileMenu);
        
        JMenu docMenu = new JMenu();
        docMenu.setMnemonic('d');
        docMenu.setText("Document");
        
        docMenu.add(getMainMenuItem("Export", 's',
                Controller.ACTION_DOCUMENT_EXPORT));
        add(docMenu);
        
        JMenu helpMenu = new JMenu();
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        helpMenu.add(getMainMenuItem("About", 'a',
                Controller.ACTION_ABOUT));
        add(helpMenu);
    }
    
    /**
     * Get main menu item.
     * 
     * @param text
     * @param mnemonic
     * @param actionCommand
     * @return
     */
    private JMenuItem getMainMenuItem(
            String text,
            int mnemonic,
            String actionCommand) {
        JMenuItem item = new JMenuItem();
        item.setText(text);
        item.setActionCommand(actionCommand);
        item.setMnemonic(mnemonic);
        item.addActionListener(ctrl);
        return item;
    }
}
