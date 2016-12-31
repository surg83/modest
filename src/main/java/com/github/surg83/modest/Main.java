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

import com.github.surg83.modest.project.ProjectItemFactory;
import com.github.surg83.modest.project.ProjectLoader;
import com.github.surg83.modest.project.ProjectSaver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Main class.
 * 
 * Main application class.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class Main {
    
    /**
     * Events logger.
     */
    private final static Logger LOGGER
            = Logger.getLogger(Main.class.getName());

    /**
     * Application entry point.
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getDefaults().put("TextArea.font",
                    UIManager.getFont("TextField.font"));
        } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | javax.swing.UnsupportedLookAndFeelException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Config config = new Config();
                    config.load();
                    
                    ProjectItemFactory itemFactory = new ProjectItemFactory();
                    ProjectLoader loader = new ProjectLoader(itemFactory);
                    ProjectSaver saver 
                            = new ProjectSaver(config.getAppVersion());
                    Project project = new Project(loader, saver, itemFactory);
                    
                    Document document = new Document();

                    Controller ctrl = new Controller(config, project, document);
                    ctrl.run();
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        });
    }
}
