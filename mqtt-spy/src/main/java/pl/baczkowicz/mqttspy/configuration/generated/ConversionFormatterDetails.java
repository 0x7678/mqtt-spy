//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.01 at 09:23:50 PM BST 
//


package pl.baczkowicz.mqttspy.configuration.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
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
 * <p>Java class for ConversionFormatterDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConversionFormatterDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Format" type="{http://baczkowicz.pl/mqtt-spy-configuration}ConversionMethod"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConversionFormatterDetails", propOrder = {
    "format"
})
@XmlSeeAlso({
    CharacterReplaceFormatterDetails.class
})
public class ConversionFormatterDetails
    implements CopyTo, Copyable, Equals, HashCode, ToString
{

    @XmlElement(name = "Format", required = true)
    protected ConversionMethod format;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link ConversionMethod }
     *     
     */
    public ConversionMethod getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConversionMethod }
     *     
     */
    public void setFormat(ConversionMethod value) {
        this.format = value;
    }

    public void toString(ToStringBuilder toStringBuilder) {
        {
            ConversionMethod theFormat;
            theFormat = this.getFormat();
            toStringBuilder.append("format", theFormat);
        }
    }

    public String toString() {
        final ToStringBuilder toStringBuilder = new JAXBToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof ConversionFormatterDetails)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final ConversionFormatterDetails that = ((ConversionFormatterDetails) object);
        equalsBuilder.append(this.getFormat(), that.getFormat());
    }

    public boolean equals(Object object) {
        if (!(object instanceof ConversionFormatterDetails)) {
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
        hashCodeBuilder.append(this.getFormat());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        final ConversionFormatterDetails copy = ((target == null)?((ConversionFormatterDetails) createCopy()):((ConversionFormatterDetails) target));
        {
            ConversionMethod sourceFormat;
            sourceFormat = this.getFormat();
            ConversionMethod copyFormat = ((ConversionMethod) copyBuilder.copy(sourceFormat));
            copy.setFormat(copyFormat);
        }
        return copy;
    }

    public Object copyTo(Object target) {
        final CopyBuilder copyBuilder = new JAXBCopyBuilder();
        return copyTo(target, copyBuilder);
    }

    public Object createCopy() {
        return new ConversionFormatterDetails();
    }

}
