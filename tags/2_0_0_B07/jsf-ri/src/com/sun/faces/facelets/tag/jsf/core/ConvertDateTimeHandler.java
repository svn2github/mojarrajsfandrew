/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sun.faces.facelets.tag.jsf.core;

import java.util.TimeZone;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import javax.faces.webapp.pdl.facelets.FaceletContext;
import javax.faces.webapp.pdl.facelets.FaceletException;
import com.sun.faces.facelets.tag.MetaRuleset;
import com.sun.faces.facelets.tag.TagAttribute;
import com.sun.faces.facelets.tag.TagAttributeException;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.facelets.tag.jsf.ConvertHandler;
import com.sun.faces.facelets.tag.jsf.ConverterConfig;

/**
 * Register a DateTimeConverter instance on the UIComponent associated with the
 * closest parent UIComponent custom action. <p/> See <a target="_new"
 * href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/tlddocs/f/convertDateTime.html">tag
 * documentation</a>.
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
public final class ConvertDateTimeHandler extends ConvertHandler {

    private final TagAttribute dateStyle;

    private final TagAttribute locale;

    private final TagAttribute pattern;

    private final TagAttribute timeStyle;

    private final TagAttribute timeZone;

    private final TagAttribute type;

    /**
     * @param config
     */
    public ConvertDateTimeHandler(ConverterConfig config) {
        super(config);
        this.dateStyle = this.getAttribute("dateStyle");
        this.locale = this.getAttribute("locale");
        this.pattern = this.getAttribute("pattern");
        this.timeStyle = this.getAttribute("timeStyle");
        this.timeZone = this.getAttribute("timeZone");
        this.type = this.getAttribute("type");
    }

    /**
     * Returns a new DateTimeConverter
     * 
     * @see DateTimeConverter
     * @see com.sun.faces.facelets.tag.jsf.ConvertHandler#createConverter(com.sun.faces.facelets.FaceletContext)
     */
    protected Converter createConverter(FaceletContext ctx)
            throws FacesException, ELException, FaceletException {
        return ctx.getFacesContext().getApplication().createConverter(DateTimeConverter.CONVERTER_ID);

    }

    /**
     * Implements tag spec, see taglib documentation.
     * 
     * @see com.sun.faces.facelets.tag.MetaTagHandler#setAttributes(com.sun.faces.facelets.FaceletContext, Object)
     */
    protected void setAttributes(FaceletContext ctx, Object obj) {
        DateTimeConverter c = (DateTimeConverter) obj;
        if (this.locale != null) {
            c.setLocale(ComponentSupport.getLocale(ctx, this.locale));
        }
        if (this.pattern != null) {
            c.setPattern(this.pattern.getValue(ctx));
        } else {
            if (this.type != null) {
                c.setType(this.type.getValue(ctx));
            }
            if (this.dateStyle != null) {
                c.setDateStyle(this.dateStyle.getValue(ctx));
            }
            if (this.timeStyle != null) {
                c.setTimeStyle(this.timeStyle.getValue(ctx));
            }
        }
        
        if (this.timeZone != null) {
            Object t = this.timeZone.getObject(ctx);
            if(t != null) {
	            if (t instanceof TimeZone) {
	                c.setTimeZone((TimeZone) t);
	            } else if (t instanceof String) {
	                TimeZone tz = TimeZone.getTimeZone((String) t);
	                c.setTimeZone(tz);
	            } else {
	                throw new TagAttributeException(
	                        this.tag,
	                        this.timeZone,
	                        "Illegal TimeZone, must evaluate to either a java.util.TimeZone or String, is type: "
	                                + t.getClass());
	            }
            }
        }
    }

    protected MetaRuleset createMetaRuleset(Class type) {
        return super.createMetaRuleset(type).ignoreAll();
    }
}
