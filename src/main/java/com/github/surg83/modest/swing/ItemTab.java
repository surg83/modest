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
import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * ItemTab class.
 * 
 * Tab with item details.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ItemTab extends JPanel {
    
    /**
     * Project item.
     */
    private final ProjectItem item;

    /**
     * ItemTab class constructor.
     * 
     * @param item 
     */
    public ItemTab(ProjectItem item) {
        this.item = item;
    }
    
    /**
     * Setup item tab.
     */
    public void setup() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addField(ProjectItem.FORM_TEXTFIELD, "name", item.getName());
        LinkedHashMap<String, String> form = item.getFieldForm();
        form.keySet().stream().forEach((key) -> {
            addField(form.get(key), key, item.getFieldValue(key));
        });
    }
    
    /**
     * Add field.
     * 
     * @param form
     * @param name
     * @param value 
     */
    private void addField(String form, String name, String value) {
        switch (form) {
            case ProjectItem.FORM_TEXTFIELD:
                addTextfield(name, value);
                break;
            case ProjectItem.FORM_TEXTAREA:
                addTextarea(name, value);
                break;
        }
        add(Box.createRigidArea(new Dimension(0, 5)));
    }
    
    /**
     * Add text field.
     * 
     * @param name 
     * @param value
     */
    private void addTextfield(String name, String value) {
        JTextField textfield = new JTextField(20);
        textfield.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                textfield.getPreferredSize().height));
        textfield.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfield.setText(value);
        textfield.getDocument().putProperty("field", name);
        textfield.getDocument().addDocumentListener(
                new ItemSynchronizer(textfield, item));
        addLabel(name, textfield);
        add(textfield);
    }
    
    /**
     * Add text area.
     * 
     * @param name 
     * @param value
     */
    private void addTextarea(String name, String value) {
        JTextArea textarea = new JTextArea();
        textarea.setRows(20);
        textarea.getDocument().putProperty("field", name);
        textarea.getDocument().addDocumentListener(
                new ItemSynchronizer(textarea, item));
        textarea.setText(value);
        JScrollPane scrolledTextarea = new JScrollPane(textarea); 
        scrolledTextarea.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                scrolledTextarea.getPreferredSize().height));
        scrolledTextarea.setAlignmentX(Component.LEFT_ALIGNMENT);
        addLabel(name, scrolledTextarea);
        add(scrolledTextarea);
    }
    
    /**
     * Add label.
     * 
     * @param name
     * @param target 
     */
    private void addLabel(String name, JComponent target) {
        JLabel label = new JLabel(name, JLabel.TRAILING);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setLabelFor(target);
        add(label);
    }
    
    /**
     * ItemSynchronizer class.
     * 
     * Project item synchronize.
     */
    private class ItemSynchronizer implements DocumentListener {
        
        /**
         * Text component.
         */
        private final JTextComponent textcomp;
        
        /**
         * Project item.
         */
        private final ProjectItem item;

        /**
         * ItemSynchronizer class constructor.
         * 
         * @param textcomp
         * @param item 
         */
        public ItemSynchronizer(JTextComponent textcomp, ProjectItem item) {
            this.textcomp = textcomp;
            this.item = item;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            synchronize(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            synchronize(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            synchronize(e);
        }

        /**
         * Synchronize item with text component.
         * 
         * @param e
         */
        private void synchronize(DocumentEvent e) {
            String field = e.getDocument().getProperty("field").toString();
            String value = textcomp.getText();
            if ("name".equals(field)) {
                item.setName(value);
            }
            else {
                item.setFieldValue(field, value);
            }
        }
        
    }
}
