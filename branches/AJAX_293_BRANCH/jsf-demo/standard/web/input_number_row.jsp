<%--
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 
 Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 
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
--%>

<!--
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License). You may not use this file except in
 compliance with the License.
 
 You can obtain a copy of the License at
 https://javaserverfaces.dev.java.net/CDDL.html or
 legal/CDDLv1.0.txt. 
 See the License for the specific language governing
 permission and limitations under the License.
 
 When distributing Covered Code, include this CDDL
 Header Notice in each file and include the License file
 at legal/CDDLv1.0.txt.    
 If applicable, add the following below the CDDL Header,
 with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"
 
 [Name of File] [ver.__] [Date]
 
 Copyright 2005 Sun Microsystems Inc. All Rights Reserved
-->

           <tr>

             <td>

               <h:outputText id="inputNumber1Label"
                     value="input_number number readonly"/>

             </td>


             <td>

               <h:inputText id="inputNumber1" 
                                 value="1239989.6079" 
                                 readonly="true"
                                 size="10" maxlength="20"
                                 alt="input_number number readonly"
                                 accesskey="N"
                               title="input_number number readonly">
                   <f:convertNumber type="number"/>
               </h:inputText>
                           

             </td>
             <td>

             <h:message id="inputNumber1Errors"
                          for="inputNumber1" />

             </td>
            </tr>

           <tr>

             <td>

               <h:outputText id="inputNumber2Label" 
                     value="input_number currency"/>

             </td>


             <td>

               <h:inputText id="inputTest2" value="#{LoginBean.long}">
                   <f:convertNumber type="currency" currencySymbol="EUR"/>
                  
               </h:inputText>
             </td>

	      <td>

		<h:message id="inputNumber2Errors" 
			  for="inputNumber2" />

	      </td>

            </tr>

           <tr>

             <td>

               <h:outputText id="inputNumber3Label" 
                     value="input_number percent "/>

             </td>


             <td>

               <h:inputText id="inputNumber3" 
                                 value="45%" 
                                 size="10"
                                 alt="input_number percent"
                                  title="input_number percent">
                   <f:convertNumber type="percent"/>
              </h:inputText>

             </td>

	      <td>

		<h:message id="inputNumber3Errors" 
			  for="inputNumber3" />

	      </td>

            </tr>

           <tr>

             <td>

               <h:outputText id="inputNumber4Label" 
                     value="input_number PATTERN "/>

             </td>


             <td>

               <h:inputText id="inputNumber4" 
                                 value="9999.987651" 
                                 size="20" maxlength="40"
                                 alt="input_number PATTERN "
                                 accesskey="d"
                               title="input_number PATTERN">
                   <f:convertNumber pattern="####"/>
               </h:inputText>
             </td>

	      <td>

		<h:message id="inputNumber4Errors" 
			  for="inputNumber4" />

	      </td>

            </tr>

            <tr>

             <td>

               <h:outputText id="inputNumber5Label"
                     value="input_number integer with valueRef"/>

             </td>


             <td>

               <h:inputText id="inputNumber5" 
                                 size="2" maxlength="10"
                                 alt="input_number integer with valueRef"
                                 accesskey="d"
                               title="input_number integer with valueRef">
                   <f:convertNumber integerOnly="true"/>
               </h:inputText>
                               
             </td>

              <td>

                <h:message id="inputNumber5Errors"
                          for="inputNumber5" />

              </td>

            </tr>


