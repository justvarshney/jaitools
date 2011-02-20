/*
 * Copyright 2011 Michael Bedward
 * 
 * This file is part of jai-tools.
 *
 * jai-tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * jai-tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with jai-tools.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package jaitools.jiffle.runtime;

/**
 * Provides default implementations of {@link JiffleRuntime} methods.
 *
 * @author Michael Bedward
 * @since 1.1
 * @version $Id$
 */
public abstract class AbstractJiffleRuntime implements JiffleRuntime {
    /**
     * Value to return for out of bounds image data requests
     */
    protected double _outsideValue;
    /**
     * Whether the {@code outsideValueSet} option is set
     */
    protected boolean _outsideValueSet;
    
    /**
     * {@inheritDoc}
     */
    public Double getVar(String varName) {
        return null;
    }
    
    /**
     * Initializes image-scope variables. These are fields in the runtime class.
     * They are initialized in a separate method rather than the constructor
     * because they may depend on expressions involving values that are not
     * known until the processing area is set (e.g. Jiffle's width() function).
     */
    protected abstract void initImageScopeVars();

}
