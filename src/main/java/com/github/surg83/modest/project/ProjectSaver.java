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

package com.github.surg83.modest.project;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * ProjectSaver class.
 * 
 * Saving of project file.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ProjectSaver extends ProjectIO {
    
    /**
     * Application version.
     */
    private final String appVersion;

    /**
     * ProjectSaver class constructor.
     * 
     * @param appVersion 
     */
    public ProjectSaver(String appVersion) {
        this.appVersion = appVersion;
    }
    
    /**
     * Save tree model.
     *
     * @param model
     * @param filepath
     * @throws ParserConfigurationException
     * @throws TransformerException 
     * @throws java.io.IOException 
     */
    public void saveTreeModel(ProjectTreeModel model, String filepath)
            throws ParserConfigurationException, TransformerException,
            IOException {
        Document doc = getDocument();
        ProjectTreeNode rootNode = model.getRoot();
        Element root = saveTreeModelElements(doc, model, rootNode, filepath);
        doc.appendChild(root);
        saveDocument(doc, filepath);
    }
    
    /**
     * Save document.
     * 
     * @param doc
     * @param filepath
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    private void saveDocument(Document doc, String filepath)
            throws TransformerConfigurationException, TransformerException {
        Transformer transformer = getTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult target = new StreamResult(new File(filepath));
        transformer.transform(source, target);
    }
    
    /**
     * Get document.
     * 
     * @return
     * @throws ParserConfigurationException 
     */
    private Document getDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document doc = builder.newDocument();
        doc.setXmlStandalone(true);
        return doc;
    }
    
    /**
     * Get transformer.
     * 
     * @return
     * @throws TransformerConfigurationException 
     */
    private Transformer getTransformer()
            throws TransformerConfigurationException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", 
                "4"
        );
        return transformer;
    }
    
    /**
     * Save tree elements.
     * 
     * @param doc
     * @param model
     * @param parentNode
     * @param filepath
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException 
     * @throws IOException 
     */
    private Element saveTreeModelElements(Document doc, ProjectTreeModel model,
            ProjectTreeNode parentNode, String filepath)
            throws ParserConfigurationException, TransformerException,
            IOException {
        ProjectItem parentItem = parentNode.getUserObject();
        saveItem(parentItem, filepath);
        Element parentElem = getElement(doc, parentItem);
        for (int i=0; i<model.getChildCount(parentNode); i++) {
            ProjectTreeNode childNode = model.getChild(parentNode, i);
            Element childElem 
                    = saveTreeModelElements(doc, model, childNode, filepath);
            parentElem.appendChild(childElem);
        }
        return parentElem;
    }

    /**
     * Save item.
     * 
     * @param item
     * @param filepath
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws IOException 
     */
    private void saveItem(ProjectItem item, String filepath)
            throws ParserConfigurationException, TransformerException,
            IOException {
        Document doc = getDocument();
        String itemTypePath = getItemTypePath(filepath, item);
        File itemTypeDir = new File(itemTypePath);
        itemTypeDir.mkdir();
        String itemFilepath = getItemPath(filepath, item);
        Element itemElement = item.getElement(doc);
        doc.appendChild(itemElement);
        saveDocument(doc, itemFilepath);
    }
    
    /**
     * Get element.
     * 
     * @param doc
     * @param item
     * @return
     */
    private Element getElement(Document doc, ProjectItem item) {
        Element elem = doc.createElement(item.getType());
        elem.setAttribute("id", item.getId());
        if (item.isRoot()) {
            elem.setAttribute("appVersion", appVersion);
        }
        Element elemName = doc.createElement("name");
        elemName.appendChild(doc.createTextNode(item.getName()));
        elem.appendChild(elemName);
        return elem;
    }
}
