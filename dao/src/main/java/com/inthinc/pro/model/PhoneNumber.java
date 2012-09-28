package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhoneNumber extends BaseEntity
{
    String          number;     // 10 digits
    String          extension;  // optional
    String          countryCode; // ??
    PhoneNumberType type;

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public PhoneNumberType getType()
    {
        return type;
    }

    public void setType(PhoneNumberType type)
    {
        this.type = type;
    }
}
