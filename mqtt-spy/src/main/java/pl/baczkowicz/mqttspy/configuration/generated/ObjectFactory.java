//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.02 at 09:24:51 PM BST 
//


package pl.baczkowicz.mqttspy.configuration.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pl.baczkowicz.mqttspy.configuration.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MqttSpyConfiguration_QNAME = new QName("http://baczkowicz.pl/mqtt-spy-configuration", "MqttSpyConfiguration");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pl.baczkowicz.mqttspy.configuration.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MqttSpyConfiguration }
     * 
     */
    public MqttSpyConfiguration createMqttSpyConfiguration() {
        return new MqttSpyConfiguration();
    }

    /**
     * Create an instance of {@link PublicationDetails }
     * 
     */
    public PublicationDetails createPublicationDetails() {
        return new PublicationDetails();
    }

    /**
     * Create an instance of {@link ConnectionDetails }
     * 
     */
    public ConnectionDetails createConnectionDetails() {
        return new ConnectionDetails();
    }

    /**
     * Create an instance of {@link Formatting }
     * 
     */
    public Formatting createFormatting() {
        return new Formatting();
    }

    /**
     * Create an instance of {@link SubstringReplaceFormatterDetails }
     * 
     */
    public SubstringReplaceFormatterDetails createSubstringReplaceFormatterDetails() {
        return new SubstringReplaceFormatterDetails();
    }

    /**
     * Create an instance of {@link UserAuthentication }
     * 
     */
    public UserAuthentication createUserAuthentication() {
        return new UserAuthentication();
    }

    /**
     * Create an instance of {@link SubscriptionDetails }
     * 
     */
    public SubscriptionDetails createSubscriptionDetails() {
        return new SubscriptionDetails();
    }

    /**
     * Create an instance of {@link SubstringConversionFormatterDetails }
     * 
     */
    public SubstringConversionFormatterDetails createSubstringConversionFormatterDetails() {
        return new SubstringConversionFormatterDetails();
    }

    /**
     * Create an instance of {@link CharacterReplaceFormatterDetails }
     * 
     */
    public CharacterReplaceFormatterDetails createCharacterReplaceFormatterDetails() {
        return new CharacterReplaceFormatterDetails();
    }

    /**
     * Create an instance of {@link UI }
     * 
     */
    public UI createUI() {
        return new UI();
    }

    /**
     * Create an instance of {@link SubstringExtractFormatterDetails }
     * 
     */
    public SubstringExtractFormatterDetails createSubstringExtractFormatterDetails() {
        return new SubstringExtractFormatterDetails();
    }

    /**
     * Create an instance of {@link Connectivity }
     * 
     */
    public Connectivity createConnectivity() {
        return new Connectivity();
    }

    /**
     * Create an instance of {@link UserCredentials }
     * 
     */
    public UserCredentials createUserCredentials() {
        return new UserCredentials();
    }

    /**
     * Create an instance of {@link FormatterDetails }
     * 
     */
    public FormatterDetails createFormatterDetails() {
        return new FormatterDetails();
    }

    /**
     * Create an instance of {@link FormatterFunction }
     * 
     */
    public FormatterFunction createFormatterFunction() {
        return new FormatterFunction();
    }

    /**
     * Create an instance of {@link ConversionFormatterDetails }
     * 
     */
    public ConversionFormatterDetails createConversionFormatterDetails() {
        return new ConversionFormatterDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MqttSpyConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baczkowicz.pl/mqtt-spy-configuration", name = "MqttSpyConfiguration")
    public JAXBElement<MqttSpyConfiguration> createMqttSpyConfiguration(MqttSpyConfiguration value) {
        return new JAXBElement<MqttSpyConfiguration>(_MqttSpyConfiguration_QNAME, MqttSpyConfiguration.class, null, value);
    }

}
