/*
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 2 only ("GPL") or the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License. You can obtain
 a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 Sun designates this particular file as subject to the "Classpath" exception
 as provided by Sun in the GPL Version 2 section of the License file that
 accompanied this code.  If applicable, add the following below the License
 Header, with the fields enclosed by brackets [] replaced by your own
 identifying information: "Portions Copyrighted [year]
 [name of copyright owner]"

 Contributor(s):

 If you wish your version of this file to be governed by only the CDDL or
 only the GPL Version 2, indicate your decision by adding "[Contributor]
 elects to include this software in this distribution under the [CDDL or GPL
 Version 2] license."  If you don't indicate a single choice of license, a
 recipient has the option to distribute your version of this file under
 either the CDDL, the GPL Version 2 or to extend the choice of license to
 its licensees as provided above.  However, if you add GPL Version 2 code
 and therefore, elected the GPL Version 2 license, then the option applies
 only if the new code is made subject to such option by the copyright
 holder.
*/

if (!window["edittextdemo"]) {
    var edittextdemo = {};
}
function init(componentID, initialValue) {
    edittextdemo[componentID] = initialValue;
}
function toggle(idOn, idOff) {
    try {
        var elementon = document.getElementById(idOn);
        var elementoff = document.getElementById(idOff);
        elementon.style.display = "inline";
        elementoff.style.display = "none";
    } catch (ex) {
        alert(ex);
    }
}
function submitButton(componentID, event) {
    try {
        var edit1 = componentID + ':edit1';
        var edit2 = componentID + ':edit2';
        toggle(edit1, edit2);

        var link = componentID + ':editLink';
        var input = componentID + ':editInput';
        var subButton = componentID + ':submit';
        var exec = subButton + ',' + input;
        var rend = input + ',' + link;
        jsf.ajax.request(document.getElementById(subButton), event, {execute: exec, render: rend});
        edittextdemo[componentID] = document.getElementById(input).value;
    } catch (ex) {
        alert(ex);
    }
    return false;
}
function cancelButton(componentID) {
    try {
        var edit1 = componentID + ':edit1';
        var edit2 = componentID + ':edit2';
        toggle(edit1, edit2);
        var input = componentID + ':editInput';
        document.getElementById(input).value = edittextdemo[componentID];
    } catch (ex) {
        alert(ex);
    }
    return false;
}
function linkClick(componentID) {
    try {
        var edit1 = componentID + ':edit1';
        var edit2 = componentID + ':edit2';
        var editInput = componentID + ':editInput';
        toggle(edit2, edit1);
        document.getElementById(editInput).focus();
    } catch (ex) {
        alert(ex);
    }
    return false;
}
