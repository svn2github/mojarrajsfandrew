/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
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
 */

package standard;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LoginBean {

    private static final String defaultOptions[] = {
          "pinto",
          "black",
          "garbanzo",
          "green",
          "string",
          "coffee",
          "baked"
    };

    private static final SelectItem options1[] = {
          new SelectItem("pinto", "pinto", "pinto"),
          new SelectItem("black", "black", "black"),
          new SelectItem("garbanzo", "garbanzo", "garbanzo")
    };

    private static final SelectItem options2[] = {
          new SelectItem("green", "green", "green"),
          new SelectItem("string", "string", "string"),
          new SelectItem("coffee", "coffee", "coffee"),
          new SelectItem("baked", "baked", "baked")
    };

    Long[] longOptions = {new Long(1001), new Long(1002), new Long(1003)};
    Boolean booleanOptions[] = {
          new Boolean(true), new Boolean(false),
          new Boolean(false)
    };

    ArrayList longList = new ArrayList(3);
    ArrayList booleanList = new ArrayList(3);
    ArrayList stringList = new ArrayList(3);
    Long[] currentLongOptions = null;
    String[] currentStringOptions = null;
    Boolean currentBooleanOption = null;
    //  Long currentLongOption = null;
    long currentLongOption = 1002;

    private double doubleVal;
    private ArrayList options = null;
    private ArrayList optionsGroup = null;

    private String currentOption = defaultOptions[4];
    private Date date = new Date(System.currentTimeMillis());
    private String currentShipment = "nextMonth";
    private char charVal = 'e';
    private int intVal;
    private float floatVal;
    private short shortVal;
    private long longVal = 2000;
    private byte byteVal;
    private String stringVal;
    private Date time;

    private String userName = "joebob";
    private String password = null;
    private String address = null;
    private Boolean validUser = null;
    private Integer pin = null;

    private SelectItem nextWeek = null;
    private SelectItem nextMonth = null;
    private SelectItem nextDay = null;

    SelectItemGroup group1 = null;
    SelectItemGroup group2 = null;


    public LoginBean() {
        System.out.println("Model Object Created");
        options = new ArrayList(defaultOptions.length);
        int i = 0;
        for (i = 0; i < defaultOptions.length; i++) {
            options.add(new SelectItem(defaultOptions[i], defaultOptions[i],
                                       defaultOptions[i]));
        }

        optionsGroup = new ArrayList(2);
        group1 = new SelectItemGroup("coffee1", null, true, options1);
        group2 = new SelectItemGroup("coffee2", null, true, options2);
        optionsGroup.add(group1);
        optionsGroup.add(group2);

        currentStringOptions = new String[2];
        for (i = 0; i < defaultOptions.length; i++) {
            stringList.add(new SelectItem(defaultOptions[i],
                                          (defaultOptions[i].toString()),
                                          "stringOptions"));
        }

        System.out.println("SETTING CURRENT OPTIONS IN CONSTRUCTOR...");

        currentStringOptions[0] = defaultOptions[3];
        currentStringOptions[1] = defaultOptions[4];

        for (i = 0; i < longOptions.length; i++) {
            longList.add(new SelectItem(longOptions[i],
                                        (longOptions[i].toString()),
                                        "longOption"));
        }

        for (i = 0; i < booleanOptions.length; i++) {
            booleanList.add(new SelectItem(booleanOptions[i],
                                           ("booleanOption" + i),
                                           "booleanOption"));
        }
        currentLongOptions = new Long[2];
        currentLongOptions[0] = longOptions[0];
        currentLongOptions[1] = longOptions[1];
        //   currentLongOption = longOptions[1];
        currentBooleanOption = booleanOptions[0];

        System.out.println("Populated options");

    }


    public void setUserName(String user_name) {
        userName = user_name;
        System.out.println("Set userName " + userName);
    }


    public String getUserName() {
        System.out.println("get userName " + userName);
        return userName;
    }


    public void setPin(Integer _pin) {
        this.pin = _pin;
        System.out.println("Set pin " + pin);
    }


    public Integer getPin() {
        System.out.println("get pin " + pin);
        return pin;
    }


    public void setPassword(String pwd) {
        password = pwd;
        System.out.println("set Password " + password);
    }


    public String getPassword() {
        System.out.println("get Password " + password);
        return password;
    }


    public void setAddress(String addr) {
        address = addr;
        System.out.println("Set Address " + address);
    }


    public String getAddress() {
        System.out.println("get Address " + address);
        return address;
    }


    public void setValidUser(Boolean valid_user) {
        validUser = valid_user;
        System.out.println("Set validUser " + validUser);
    }


    public Boolean getValidUser() {
        System.out.println("get validUser " + validUser);
        return validUser;
    }


    public void setDouble(double newDoubleVal) {
        doubleVal = newDoubleVal;
    }


    public double getDouble() {
        return doubleVal;
    }


    public int getInt() {
        return intVal;
    }


    public void setInt(int newIntVal) {
        intVal = newIntVal;
    }


    public float getFloat() {
        return floatVal;
    }


    public void setFloat(float newFloatVal) {
        floatVal = newFloatVal;
    }


    public short getShort() {
        return shortVal;
    }


    public void setShort(short newShortVal) {
        shortVal = newShortVal;
    }


    public long getLong() {
        return longVal;
    }


    public void setLong(long newLongVal) {
        longVal = newLongVal;
    }


    public char getChar() {
        return charVal;
    }


    public void setChar(char newCharVal) {
        charVal = newCharVal;
    }


    public byte getByte() {
        return byteVal;
    }


    public void setByte(byte newByteVal) {
        byteVal = newByteVal;
    }


    public String getString() {
        return stringVal;
    }


    public void setString(String newStringVal) {
        stringVal = newStringVal;
    }


    public Collection getOptions() {
        return options;
    }


    public void setOptions(Collection newOptions) {
        options = new ArrayList(newOptions);
    }


    public Collection getOptionsGroup() {
        return optionsGroup;
    }


    public void setOptionsGroup(Collection newGroupOptions) {
        optionsGroup = new ArrayList(newGroupOptions);
    }


    public String getCurrentOption() {
        System.out.println("get current option " + currentOption.toString());
        return currentOption;
    }


    public void setCurrentOption(String newCurrentOption) {
        currentOption = newCurrentOption;
        System.out.println("set current option " + currentOption.toString());
    }


    public Boolean getCurrentBooleanOption() {
        System.out.println(
              "get currentBooleanOption " + currentBooleanOption.toString());
        return currentBooleanOption;
    }


    public void setCurrentBooleanOption(Boolean newBooleanOption) {
        currentBooleanOption = newBooleanOption;
        System.out.println(
              "set currentBooleanOption " + currentBooleanOption.toString());
    }


    public long getCurrentLongOption() {
        //   System.out.println("get currentLongOption " + currentLongOption.toString());
        return currentLongOption;
    }


    public void setCurrentLongOption(long newLongOption) {
        currentLongOption = newLongOption;
        // System.out.println("set currentLongOption " + currentLongOption.toString());
    }


    public String[] getCurrentOptions() {
        System.out.println("get current options");
        return currentStringOptions;
    }


    public void setCurrentOptions(String[] newCurrentOptions) {
        currentStringOptions = newCurrentOptions;
        System.out.println("set current options");
    }


    public Long[] getCurrentLongOptions() {
        System.out.println("get currentLongOptions");
        return currentLongOptions;
    }


    public void setCurrentLongOptions(Long[] newCurrentOptions) {
        currentLongOptions = newCurrentOptions;
        System.out.println("set currentLongOptions");
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date newDate) {
        date = newDate;
    }


    public Date getTime() {
        return time;
    }


    public void setTime(Date newTime) {
        time = newTime;
    }


    protected Date dateTime;


    public Date getDateTime() {
        return dateTime;
    }


    public void setDateTime(Date newDateTime) {
        dateTime = newDateTime;
    }


    protected Float floater = new Float(3.141592);


    public Float getFloater() {
        return floater;
    }


    public void setFloater(Float newFloater) {
        floater = newFloater;
    }


    protected String imagePath = null;


    public String getImagePath() {
        return imagePath;
    }


    public void setImagePath(String newImagePath) {
        imagePath = newImagePath;
    }


    public SelectItem getNextWeek() {
        return new SelectItem("nextWeek", "nextWeek", "nextWeek");
    }


    public void setNextWeek(SelectItem newNextWeek) {
        nextWeek = newNextWeek;
    }


    public SelectItem getNextDay() {
        return new SelectItem("nextDay", "nextDay", "nextDay");
    }


    public void setNextDay(SelectItem newNextDay) {
        nextDay = newNextDay;
    }


    public SelectItem getNextMonth() {
        return new SelectItem("nextMonth", "nextMonth", "nextMonth");
    }


    public void setNextMonth(SelectItem newNextMonth) {
        nextMonth = newNextMonth;
    }


    public String getCurrentShipment() {
        return currentShipment;
    }


    public void setCurrentShipment(String shipment) {
        currentShipment = shipment;
    }


    public Collection getLongList() {
        return longList;
    }


    public void setLongList(Collection newLongOptions) {
        longList = new ArrayList(newLongOptions);
    }


    public Collection getBooleanList() {
        return booleanList;
    }


    public void setBooleanList(Collection newBooleanOptions) {
        booleanList = new ArrayList(newBooleanOptions);
    }
}
