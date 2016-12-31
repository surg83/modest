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

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

/**
 * Config class.
 * 
 * Manages application settings.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class Config {
    
    /**
     * Default project file name.
     */
    public String defaultFilename;
    
    /**
     * Default file path to open project.
     */
    public String defaultFilepath;
    
    /**
     * Default project template file path.
     */
    public String defaultTemplateFilepath;
    
    /**
     * Default window size.
     */
    public Dimension defaultWindowSize;
    
    /**
     * Application name.
     */
    private String appName;

    /**
     * Get application name.
     *
     * @return 
     */
    public String getAppName() {
        return appName;
    }
    
    /**
     * Application version.
     */
    private String appVersion;

    /**
     * Get application version.
     * 
     * @return 
     */
    public String getAppVersion() {
        return appVersion;
    }
    
    /**
     * Load configuration.
     *
     * @throws IOException 
     */
    public void load() throws IOException {
        appName = "Modest";
        appVersion = "1.0.0";
        defaultFilename = "project.xml";
        defaultFilepath = new File(".").getCanonicalPath();
        defaultTemplateFilepath = new File(".").getCanonicalPath()
                + File.separator + defaultFilename;
        defaultWindowSize = new Dimension(640, 480);
    }
}
