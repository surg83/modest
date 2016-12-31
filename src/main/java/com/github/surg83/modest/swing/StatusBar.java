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

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StatusBar class.
 * 
 * Status bar.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class StatusBar extends JPanel {
    
    /**
     * Display text.
     */
    private final String displayText;

    /**
     * StatusBar class constructor.
     * 
     * @param displayText 
     */
    public StatusBar(String displayText) {
        this.displayText = displayText;
    }
    
    /**
     * Setup status bar.
     */
    public void setup() {
        JLabel statusLabel = new JLabel(displayText);
        add(statusLabel);
    }
}
