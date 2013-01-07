/**
 * Copyright (C) 2012 Patrice Brend'amour <p.brendamour@bitzeche.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brendamour.jpasskit;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import de.brendamour.jpasskit.passes.PKBoardingPass;
import de.brendamour.jpasskit.passes.PKCoupon;
import de.brendamour.jpasskit.passes.PKEventTicket;
import de.brendamour.jpasskit.passes.PKGenericPass;
import de.brendamour.jpasskit.passes.PKStoreCard;

public class PKPass implements IPKValidateable {
    private static final int EXPECTED_AUTHTOKEN_LENGTH = 16;
    private int formatVersion = 1;
    private String serialNumber;
    private String passTypeIdentifier;

    private URL webServiceURL;
    private String authenticationToken;

    private String description;

    private String teamIdentifier;

    private String organizationName;
    private String logoText;

    private Color foregroundColor;
    private Color backgroundColor;
    private Color labelColor;

    private List<PKLocation> locations;

    private PKBarcode barcode;

    private PKEventTicket eventTicket;
    private PKCoupon coupon;
    private PKStoreCard storeCard;
    private PKBoardingPass boardingPass;
    private PKGenericPass generic;
    private PKGenericPass passThatWasSet;

    private List<Integer> associatedStoreIdentifiers;

    private Date relevantDate;

    private boolean suppressStripShine;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPassTypeIdentifier() {
        return passTypeIdentifier;
    }

    public void setPassTypeIdentifier(final String passTypeIdentifier) {
        this.passTypeIdentifier = passTypeIdentifier;
    }

    public URL getWebServiceURL() {
        return webServiceURL;
    }

    public void setWebServiceURL(final URL webServiceURL) {
        this.webServiceURL = webServiceURL;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(final String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(final int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTeamIdentifier() {
        return teamIdentifier;
    }

    public void setTeamIdentifier(final String teamIdentifier) {
        this.teamIdentifier = teamIdentifier;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLogoText() {
        return logoText;
    }

    public void setLogoText(final String logoText) {
        this.logoText = logoText;
    }

    public String getForegroundColor() {
        return convertColorToString(foregroundColor);
    }

    public void setForegroundColor(final String foregroundColor) {
        this.foregroundColor = convertStringToColor(foregroundColor);
    }

    public String getBackgroundColor() {
        return convertColorToString(backgroundColor);
    }

    public void setBackgroundColor(final String backgroundColor) {
        this.backgroundColor = convertStringToColor(backgroundColor);
    }

    public Color getForegroundColorAsObject() {
        return foregroundColor;
    }

    public void setForegroundColorAsObject(final Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public Color getBackgroundColorAsObject() {
        return backgroundColor;
    }

    public void setBackgroundColorAsObject(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<PKLocation> getLocations() {
        return locations;
    }

    public void setLocations(final List<PKLocation> locations) {
        this.locations = locations;
    }

    public PKBarcode getBarcode() {
        return barcode;
    }

    public void setBarcode(final PKBarcode barcode) {
        this.barcode = barcode;
    }

    public PKEventTicket getEventTicket() {
        return eventTicket;
    }

    public void setEventTicket(final PKEventTicket eventTicket) {
        this.eventTicket = eventTicket;
        passThatWasSet = eventTicket;
        storeCard = null;
        coupon = null;
        generic = null;
        boardingPass = null;
    }

    public PKCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(final PKCoupon coupon) {
        this.coupon = coupon;
        eventTicket = null;
        storeCard = null;
        generic = null;
        boardingPass = null;
        passThatWasSet = coupon;
    }

    public PKStoreCard getStoreCard() {
        return storeCard;
    }

    public void setStoreCard(final PKStoreCard storeCard) {
        this.storeCard = storeCard;
        eventTicket = null;
        coupon = null;
        generic = null;
        boardingPass = null;
        passThatWasSet = storeCard;
    }

    public PKBoardingPass getBoardingPass() {
        return boardingPass;
    }

    public void setBoardingPass(final PKBoardingPass boardingPass) {
        this.boardingPass = boardingPass;
        eventTicket = null;
        storeCard = null;
        coupon = null;
        generic = null;
        passThatWasSet = boardingPass;
    }

    public PKGenericPass getGeneric() {
        return generic;
    }

    public void setGeneric(final PKGenericPass generic) {
        this.generic = generic;
        eventTicket = null;
        storeCard = null;
        coupon = null;
        boardingPass = null;
        passThatWasSet = generic;
    }

    public String getLabelColor() {
        return convertColorToString(labelColor);
    }

    public void setLabelColor(final String labelColor) {
        this.labelColor = convertStringToColor(labelColor);
    }

    public Color getLabelColorAsObject() {
        return labelColor;
    }

    public void setLabelColorAsObject(final Color labelColor) {
        this.labelColor = labelColor;
    }

    public List<Integer> getAssociatedStoreIdentifiers() {
        return associatedStoreIdentifiers;
    }

    public void setAssociatedStoreIdentifiers(final List<Integer> associatedStoreIdentifiers) {
        this.associatedStoreIdentifiers = associatedStoreIdentifiers;
    }

    public Date getRelevantDate() {
        return relevantDate;
    }

    public void setRelevantDate(final Date relevantDate) {
        this.relevantDate = relevantDate;
    }

    public boolean isSuppressStripShine() {
        return suppressStripShine;
    }

    public void setSuppressStripShine(final boolean suppressStripShine) {
        this.suppressStripShine = suppressStripShine;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public List<String> getValidationErrors() {
        List<String> validationErrors = new ArrayList<String>();

        if (StringUtils.isEmpty(serialNumber) || StringUtils.isEmpty(passTypeIdentifier)
                || StringUtils.isEmpty(teamIdentifier) || StringUtils.isEmpty(description) || formatVersion == 0
                || StringUtils.isEmpty(organizationName)) {
            validationErrors.add("Not all required Fields are set. SerialNumber" + serialNumber
                    + " PassTypeIdentifier: " + passTypeIdentifier + " teamIdentifier" + teamIdentifier
                    + " Description: " + description + " FormatVersion: " + formatVersion + " OrganizationName: "
                    + organizationName);
        }
        if (passThatWasSet == null) {
            validationErrors.add("No pass was defined");
        }
        if (authenticationToken != null && authenticationToken.length() < EXPECTED_AUTHTOKEN_LENGTH) {
            validationErrors.add("The authenticationToken needs to be at least " + EXPECTED_AUTHTOKEN_LENGTH + " long");
        }
        if (!passThatWasSet.isValid()) {
            validationErrors.addAll(passThatWasSet.getValidationErrors());
        }

        return validationErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    protected String convertColorToString(final Color color) {
        if (color != null) {
            return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
        }
        return null;
    }

    protected Color convertStringToColor(final String colorString) {
        Color color = null;
        if (colorString != null) {
            String rgbValues = colorString.replace("rgb(", "").replace(")", "");
            String[] rgbValuesArray = rgbValues.split(",");
            if (rgbValuesArray.length == 3) {
                int r = Integer.parseInt(rgbValuesArray[0].trim());
                int g = Integer.parseInt(rgbValuesArray[1].trim());
                int b = Integer.parseInt(rgbValuesArray[2].trim());
                color = new Color(r, g, b);
            }
        }
        return color;
    }

}
