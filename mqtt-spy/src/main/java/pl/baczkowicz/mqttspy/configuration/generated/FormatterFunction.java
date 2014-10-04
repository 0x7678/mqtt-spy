//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.04 at 09:24:19 AM BST 
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
 * <p>Java class for FormatterFunction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormatterFunction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Conversion" type="{http://baczkowicz.pl/mqtt-spy-configuration}ConversionFormatterDetails"/>
 *         &lt;element name="SubstringConversion" type="{http://baczkowicz.pl/mqtt-spy-configuration}SubstringConversionFormatterDetails"/>
 *         &lt;element name="SubstringReplace" type="{http://baczkowicz.pl/mqtt-spy-configuration}SubstringReplaceFormatterDetails"/>
 *         &lt;element name="SubstringExtract" type="{http://baczkowicz.pl/mqtt-spy-configuration}SubstringExtractFormatterDetails"/>
 *         &lt;element name="CharacterReplace" type="{http://baczkowicz.pl/mqtt-spy-configuration}CharacterReplaceFormatterDetails"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormatterFunction", propOrder = {
    "conversion",
    "substringConversion",
    "substringReplace",
    "substringExtract",
    "characterReplace"
})
public class FormatterFunction
    implements CopyTo, Copyable, Equals, HashCode, ToString
{

    @XmlElement(name = "Conversion")
    protected ConversionFormatterDetails conversion;
    @XmlElement(name = "SubstringConversion")
    protected SubstringConversionFormatterDetails substringConversion;
    @XmlElement(name = "SubstringReplace")
    protected SubstringReplaceFormatterDetails substringReplace;
    @XmlElement(name = "SubstringExtract")
    protected SubstringExtractFormatterDetails substringExtract;
    @XmlElement(name = "CharacterReplace")
    protected CharacterReplaceFormatterDetails characterReplace;

    /**
     * Gets the value of the conversion property.
     * 
     * @return
     *     possible object is
     *     {@link ConversionFormatterDetails }
     *     
     */
    public ConversionFormatterDetails getConversion() {
        return conversion;
    }

    /**
     * Sets the value of the conversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConversionFormatterDetails }
     *     
     */
    public void setConversion(ConversionFormatterDetails value) {
        this.conversion = value;
    }

    /**
     * Gets the value of the substringConversion property.
     * 
     * @return
     *     possible object is
     *     {@link SubstringConversionFormatterDetails }
     *     
     */
    public SubstringConversionFormatterDetails getSubstringConversion() {
        return substringConversion;
    }

    /**
     * Sets the value of the substringConversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstringConversionFormatterDetails }
     *     
     */
    public void setSubstringConversion(SubstringConversionFormatterDetails value) {
        this.substringConversion = value;
    }

    /**
     * Gets the value of the substringReplace property.
     * 
     * @return
     *     possible object is
     *     {@link SubstringReplaceFormatterDetails }
     *     
     */
    public SubstringReplaceFormatterDetails getSubstringReplace() {
        return substringReplace;
    }

    /**
     * Sets the value of the substringReplace property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstringReplaceFormatterDetails }
     *     
     */
    public void setSubstringReplace(SubstringReplaceFormatterDetails value) {
        this.substringReplace = value;
    }

    /**
     * Gets the value of the substringExtract property.
     * 
     * @return
     *     possible object is
     *     {@link SubstringExtractFormatterDetails }
     *     
     */
    public SubstringExtractFormatterDetails getSubstringExtract() {
        return substringExtract;
    }

    /**
     * Sets the value of the substringExtract property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstringExtractFormatterDetails }
     *     
     */
    public void setSubstringExtract(SubstringExtractFormatterDetails value) {
        this.substringExtract = value;
    }

    /**
     * Gets the value of the characterReplace property.
     * 
     * @return
     *     possible object is
     *     {@link CharacterReplaceFormatterDetails }
     *     
     */
    public CharacterReplaceFormatterDetails getCharacterReplace() {
        return characterReplace;
    }

    /**
     * Sets the value of the characterReplace property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharacterReplaceFormatterDetails }
     *     
     */
    public void setCharacterReplace(CharacterReplaceFormatterDetails value) {
        this.characterReplace = value;
    }

    public void toString(ToStringBuilder toStringBuilder) {
        {
            ConversionFormatterDetails theConversion;
            theConversion = this.getConversion();
            toStringBuilder.append("conversion", theConversion);
        }
        {
            SubstringConversionFormatterDetails theSubstringConversion;
            theSubstringConversion = this.getSubstringConversion();
            toStringBuilder.append("substringConversion", theSubstringConversion);
        }
        {
            SubstringReplaceFormatterDetails theSubstringReplace;
            theSubstringReplace = this.getSubstringReplace();
            toStringBuilder.append("substringReplace", theSubstringReplace);
        }
        {
            SubstringExtractFormatterDetails theSubstringExtract;
            theSubstringExtract = this.getSubstringExtract();
            toStringBuilder.append("substringExtract", theSubstringExtract);
        }
        {
            CharacterReplaceFormatterDetails theCharacterReplace;
            theCharacterReplace = this.getCharacterReplace();
            toStringBuilder.append("characterReplace", theCharacterReplace);
        }
    }

    public String toString() {
        final ToStringBuilder toStringBuilder = new JAXBToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof FormatterFunction)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final FormatterFunction that = ((FormatterFunction) object);
        equalsBuilder.append(this.getConversion(), that.getConversion());
        equalsBuilder.append(this.getSubstringConversion(), that.getSubstringConversion());
        equalsBuilder.append(this.getSubstringReplace(), that.getSubstringReplace());
        equalsBuilder.append(this.getSubstringExtract(), that.getSubstringExtract());
        equalsBuilder.append(this.getCharacterReplace(), that.getCharacterReplace());
    }

    public boolean equals(Object object) {
        if (!(object instanceof FormatterFunction)) {
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
        hashCodeBuilder.append(this.getConversion());
        hashCodeBuilder.append(this.getSubstringConversion());
        hashCodeBuilder.append(this.getSubstringReplace());
        hashCodeBuilder.append(this.getSubstringExtract());
        hashCodeBuilder.append(this.getCharacterReplace());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        final FormatterFunction copy = ((target == null)?((FormatterFunction) createCopy()):((FormatterFunction) target));
        {
            ConversionFormatterDetails sourceConversion;
            sourceConversion = this.getConversion();
            ConversionFormatterDetails copyConversion = ((ConversionFormatterDetails) copyBuilder.copy(sourceConversion));
            copy.setConversion(copyConversion);
        }
        {
            SubstringConversionFormatterDetails sourceSubstringConversion;
            sourceSubstringConversion = this.getSubstringConversion();
            SubstringConversionFormatterDetails copySubstringConversion = ((SubstringConversionFormatterDetails) copyBuilder.copy(sourceSubstringConversion));
            copy.setSubstringConversion(copySubstringConversion);
        }
        {
            SubstringReplaceFormatterDetails sourceSubstringReplace;
            sourceSubstringReplace = this.getSubstringReplace();
            SubstringReplaceFormatterDetails copySubstringReplace = ((SubstringReplaceFormatterDetails) copyBuilder.copy(sourceSubstringReplace));
            copy.setSubstringReplace(copySubstringReplace);
        }
        {
            SubstringExtractFormatterDetails sourceSubstringExtract;
            sourceSubstringExtract = this.getSubstringExtract();
            SubstringExtractFormatterDetails copySubstringExtract = ((SubstringExtractFormatterDetails) copyBuilder.copy(sourceSubstringExtract));
            copy.setSubstringExtract(copySubstringExtract);
        }
        {
            CharacterReplaceFormatterDetails sourceCharacterReplace;
            sourceCharacterReplace = this.getCharacterReplace();
            CharacterReplaceFormatterDetails copyCharacterReplace = ((CharacterReplaceFormatterDetails) copyBuilder.copy(sourceCharacterReplace));
            copy.setCharacterReplace(copyCharacterReplace);
        }
        return copy;
    }

    public Object copyTo(Object target) {
        final CopyBuilder copyBuilder = new JAXBCopyBuilder();
        return copyTo(target, copyBuilder);
    }

    public Object createCopy() {
        return new FormatterFunction();
    }

}
