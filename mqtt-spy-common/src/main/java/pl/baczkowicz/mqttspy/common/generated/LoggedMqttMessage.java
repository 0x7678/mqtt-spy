//
// Copyright (c) 2015 Kamil Baczkowicz
//
// CSOFF: a.*
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Timestamp removed by maven-replacer-plugin to avoid detecting changes - see the project POM for details
//


package pl.baczkowicz.mqttspy.common.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 * <p>Java class for LoggedMqttMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoggedMqttMessage">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://baczkowicz.pl/mqtt-spy/common>BaseMqttMessage">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="connection" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subscription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="encoded" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
*/
@SuppressWarnings("all")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoggedMqttMessage")
public class LoggedMqttMessage
    extends BaseMqttMessage
    implements CopyTo, Copyable, Equals, HashCode, ToString
{

    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "timestamp", required = true)
    protected long timestamp;
    @XmlAttribute(name = "connection")
    protected String connection;
    @XmlAttribute(name = "subscription")
    protected String subscription;
    @XmlAttribute(name = "encoded")
    protected Boolean encoded;

    /**
     * Default no-arg constructor
     * 
     */
    public LoggedMqttMessage() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public LoggedMqttMessage(final String value, final String topic, final Integer qos, final Boolean retained, final int id, final long timestamp, final String connection, final String subscription, final Boolean encoded) {
        super(value, topic, qos, retained);
        this.id = id;
        this.timestamp = timestamp;
        this.connection = connection;
        this.subscription = subscription;
        this.encoded = encoded;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the connection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the value of the connection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnection(String value) {
        this.connection = value;
    }

    /**
     * Gets the value of the subscription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscription() {
        return subscription;
    }

    /**
     * Sets the value of the subscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscription(String value) {
        this.subscription = value;
    }

    /**
     * Gets the value of the encoded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEncoded() {
        return encoded;
    }

    /**
     * Sets the value of the encoded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEncoded(Boolean value) {
        this.encoded = value;
    }

    public void toString(ToStringBuilder toStringBuilder) {
        super.toString(toStringBuilder);
        {
            int theId;
            theId = this.getId();
            toStringBuilder.append("id", theId);
        }
        {
            long theTimestamp;
            theTimestamp = this.getTimestamp();
            toStringBuilder.append("timestamp", theTimestamp);
        }
        {
            String theConnection;
            theConnection = this.getConnection();
            toStringBuilder.append("connection", theConnection);
        }
        {
            String theSubscription;
            theSubscription = this.getSubscription();
            toStringBuilder.append("subscription", theSubscription);
        }
        {
            Boolean theEncoded;
            theEncoded = this.isEncoded();
            toStringBuilder.append("encoded", theEncoded);
        }
    }

    public String toString() {
        final ToStringBuilder toStringBuilder = new JAXBToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof LoggedMqttMessage)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        super.equals(object, equalsBuilder);
        final LoggedMqttMessage that = ((LoggedMqttMessage) object);
        equalsBuilder.append(this.getId(), that.getId());
        equalsBuilder.append(this.getTimestamp(), that.getTimestamp());
        equalsBuilder.append(this.getConnection(), that.getConnection());
        equalsBuilder.append(this.getSubscription(), that.getSubscription());
        equalsBuilder.append(this.isEncoded(), that.isEncoded());
    }

    public boolean equals(Object object) {
        if (!(object instanceof LoggedMqttMessage)) {
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
        hashCodeBuilder.append(this.getId());
        hashCodeBuilder.append(this.getTimestamp());
        hashCodeBuilder.append(this.getConnection());
        hashCodeBuilder.append(this.getSubscription());
        hashCodeBuilder.append(this.isEncoded());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        final LoggedMqttMessage copy = ((target == null)?((LoggedMqttMessage) createCopy()):((LoggedMqttMessage) target));
        super.copyTo(copy, copyBuilder);
        {
            int sourceId;
            sourceId = this.getId();
            int copyId = ((int) copyBuilder.copy(sourceId));
            copy.setId(copyId);
        }
        {
            long sourceTimestamp;
            sourceTimestamp = this.getTimestamp();
            long copyTimestamp = ((long) copyBuilder.copy(sourceTimestamp));
            copy.setTimestamp(copyTimestamp);
        }
        {
            String sourceConnection;
            sourceConnection = this.getConnection();
            String copyConnection = ((String) copyBuilder.copy(sourceConnection));
            copy.setConnection(copyConnection);
        }
        {
            String sourceSubscription;
            sourceSubscription = this.getSubscription();
            String copySubscription = ((String) copyBuilder.copy(sourceSubscription));
            copy.setSubscription(copySubscription);
        }
        {
            Boolean sourceEncoded;
            sourceEncoded = this.isEncoded();
            Boolean copyEncoded = ((Boolean) copyBuilder.copy(sourceEncoded));
            copy.setEncoded(copyEncoded);
        }
        return copy;
    }

    public Object copyTo(Object target) {
        final CopyBuilder copyBuilder = new JAXBCopyBuilder();
        return copyTo(target, copyBuilder);
    }

    public Object createCopy() {
        return new LoggedMqttMessage();
    }

}
