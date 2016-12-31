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
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * ToolBarMenu class.
 * 
 * Toolbar menu.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ToolBarMenu extends JToolBar {
    
    /**
     * Action controller.
     */
    private final Controller ctrl;
    
    /**
     * ToolBarMenu class constructor.
     * 
     * @param ctrl 
     */
    public ToolBarMenu(Controller ctrl) {
        super();
        this.ctrl = ctrl;
    }
    
    /**
     * Setup toolbar menu.
     */
    public void setup() {
        setRollover(true);
        
        add(getToolBarButton("New", Controller.ACTION_PROJECT_NEW));
        add(getToolBarButton("Open", Controller.ACTION_PROJECT_OPEN));
        add(getToolBarButton("Save", Controller.ACTION_PROJECT_SAVE));
        add(getToolBarButton("Export", Controller.ACTION_DOCUMENT_EXPORT));
    }
    
    /**
     * Get toolbar menu button.
     * 
     * @param text
     * @param actionCommand
     * @return
     */
    private JButton getToolBarButton(String text, String actionCommand) {
        JButton button = new JButton();
        button.setText(text);
        button.setActionCommand(actionCommand);
        button.setFocusable(false);
        button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        setFloatable(false);
        button.addActionListener(ctrl);
        return button;
    }
    
}
