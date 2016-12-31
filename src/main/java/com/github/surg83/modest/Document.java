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
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;
import org.xml.sax.SAXException;

/**
 * Document class.
 * 
 * Document handling.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class Document {
    
    /**
     * Export document.
     * 
     * @param project
     * @param filepath 
     * @throws java.lang.Exception 
     */
    public void export(Project project, String filepath)
            throws Exception {
        TextDocument odt = TextDocument.newTextDocument();
        
        ProjectTreeModel model = project.getModel();
        ProjectTreeNode root = model.getRoot();
        ProjectItem rootItem = root.getUserObject();
        rootItem = project.loadItemFields(rootItem);
        Paragraph head = odt.addParagraph(rootItem.getName()
                + " " + rootItem.getFieldValue("version"));
        head.applyHeading(true, 1);
        addTextFromChildNodes(project, odt, model, root, 1);
        
        odt.save(filepath);
    }
    
    /**
     * Add text from child nodes.
     * 
     * @param project
     * @param odt
     * @param model
     * @param parentNode
     * @param level
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private void addTextFromChildNodes(Project project, TextDocument odt,
            ProjectTreeModel model, ProjectTreeNode parentNode, int level)
            throws ParserConfigurationException, SAXException, IOException {
        for (int i=0; i<model.getChildCount(parentNode); i++) {
            ProjectTreeNode childNode = model.getChild(parentNode, i);
            ProjectItem childItem = childNode.getUserObject();
            childItem = project.loadItemFields(childItem);
            addTextFromItem(odt, childItem, level);
            addTextFromChildNodes(project, odt, model, childNode, level+1);
        }
    }
    
    /**
     * Add text from item.
     * 
     * @param odt
     * @param item
     * @param level 
     */
    private void addTextFromItem(TextDocument odt,
            ProjectItem item, int level) {
        Paragraph heading = odt.addParagraph(item.getName());
        heading.applyHeading(true, level);
        odt.addParagraph(item.getFieldValue("description"));
    }
}
