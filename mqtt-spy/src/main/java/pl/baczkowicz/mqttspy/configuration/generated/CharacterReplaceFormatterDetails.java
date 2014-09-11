//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.11 at 09:57:15 PM BST 
//


package pl.baczkowicz.mqttspy.configuration.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.Copyable;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.builder.CopyBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBCopyBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBToStringBuilder;


/**
 * <p>Java class for CharacterReplaceFormatterDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CharacterReplaceFormatterDetails">
 *   &lt;complexContent>
 *     &lt;extension base="{http://baczkowicz.pl/mqtt-spy-configuration}ConversionFormatterDetails">
 *       &lt;sequence>
 *         &lt;element name="CharacterRangeFrom" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CharacterRangeTo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="WrapCharacter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CharacterReplaceFormatterDetails", propOrder = {
    "characterRangeFrom",
    "characterRangeTo",
    "wrapCharacter"
})
public class CharacterReplaceFormatterDetails
    extends ConversionFormatterDetails
    implements CopyTo, Copyable, Equals, HashCode, ToString
{

    @XmlElement(name = "CharacterRangeFrom")
    protected int characterRangeFrom;
    @XmlElement(name = "CharacterRangeTo")
    protected int characterRangeTo;
    @XmlElement(name = "WrapCharacter")
    protected String wrapCharacter;

    /**
     * Gets the value of the characterRangeFrom property.
     * 
     */
    public int getCharacterRangeFrom() {
        return characterRangeFrom;
    }

    /**
     * Sets the value of the characterRangeFrom property.
     * 
     */
    public void setCharacterRangeFrom(int value) {
        this.characterRangeFrom = value;
    }

    /**
     * Gets the value of the characterRangeTo property.
     * 
     */
    public int getCharacterRangeTo() {
        return characterRangeTo;
    }

    /**
     * Sets the value of the characterRangeTo property.
     * 
     */
    public void setCharacterRangeTo(int value) {
        this.characterRangeTo = value;
    }

    /**
     * Gets the value of the wrapCharacter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrapCharacter() {
        return wrapCharacter;
    }

    /**
     * Sets the value of the wrapCharacter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrapCharacter(String value) {
        this.wrapCharacter = value;
    }

    public void toString(ToStringBuilder toStringBuilder) {
        super.toString(toStringBuilder);
        {
            int theCharacterRangeFrom;
            theCharacterRangeFrom = this.getCharacterRangeFrom();
            toStringBuilder.append("characterRangeFrom", theCharacterRangeFrom);
        }
        {
            int theCharacterRangeTo;
            theCharacterRangeTo = this.getCharacterRangeTo();
            toStringBuilder.append("characterRangeTo", theCharacterRangeTo);
        }
        {
            String theWrapCharacter;
            theWrapCharacter = this.getWrapCharacter();
            toStringBuilder.append("wrapCharacter", theWrapCharacter);
        }
    }

    public String toString() {
        final ToStringBuilder toStringBuilder = new JAXBToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof CharacterReplaceFormatterDetails)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        super.equals(object, equalsBuilder);
        final CharacterReplaceFormatterDetails that = ((CharacterReplaceFormatterDetails) object);
        equalsBuilder.append(this.getCharacterRangeFrom(), that.getCharacterRangeFrom());
        equalsBuilder.append(this.getCharacterRangeTo(), that.getCharacterRangeTo());
        equalsBuilder.append(this.getWrapCharacter(), that.getWrapCharacter());
    }

    public boolean equals(Object object) {
        if (!(object instanceof CharacterReplaceFormatterDetails)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        super.hashCode(hashCodeBuilder);
        hashCodeBuilder.append(this.getCharacterRangeFrom());
        hashCodeBuilder.append(this.getCharacterRangeTo());
        hashCodeBuilder.append(this.getWrapCharacter());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        final CharacterReplaceFormatterDetails copy = ((target == null)?((CharacterReplaceFormatterDetails) createCopy()):((CharacterReplaceFormatterDetails) target));
        super.copyTo(copy, copyBuilder);
        {
            int sourceCharacterRangeFrom;
            sourceCharacterRangeFrom = this.getCharacterRangeFrom();
            int copyCharacterRangeFrom = ((int) copyBuilder.copy(sourceCharacterRangeFrom));
            copy.setCharacterRangeFrom(copyCharacterRangeFrom);
        }
        {
            int sourceCharacterRangeTo;
            sourceCharacterRangeTo = this.getCharacterRangeTo();
            int copyCharacterRangeTo = ((int) copyBuilder.copy(sourceCharacterRangeTo));
            copy.setCharacterRangeTo(copyCharacterRangeTo);
        }
        {
            String sourceWrapCharacter;
            sourceWrapCharacter = this.getWrapCharacter();
            String copyWrapCharacter = ((String) copyBuilder.copy(sourceWrapCharacter));
            copy.setWrapCharacter(copyWrapCharacter);
        }
        return copy;
    }

    public Object copyTo(Object target) {
        final CopyBuilder copyBuilder = new JAXBCopyBuilder();
        return copyTo(target, copyBuilder);
    }

    public Object createCopy() {
        return new CharacterReplaceFormatterDetails();
    }

}
