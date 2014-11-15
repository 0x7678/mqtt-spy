package pl.baczkowicz.mqttspy.ui.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.baczkowicz.mqttspy.configuration.generated.ConversionMethod;
import pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails;
import pl.baczkowicz.mqttspy.configuration.generated.FormatterFunction;
import pl.baczkowicz.mqttspy.configuration.generated.SubstringConversionFormatterDetails;
import pl.baczkowicz.mqttspy.configuration.generated.SubstringExtractFormatterDetails;
import pl.baczkowicz.mqttspy.exceptions.ConversionException;
import pl.baczkowicz.mqttspy.utils.ConversionUtils;

/**
 * 
 * Uses for the formatting/conversion utils.
 * 
 * @author Kamil Baczkowicz
 *
 */
public class FormattingUtilsTest
{
	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#stringToHex(java.lang.String)}.
	 */
	@Test
	public final void testStringToHex()
	{
		assertEquals("7465737432", ConversionUtils.stringToHex("test2"));
	}

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#hexToString(java.lang.String)}.
	 * @throws ConversionException 
	 */
	@Test
	public final void testHexToString() throws ConversionException
	{
		assertEquals("test2", ConversionUtils.hexToString("7465737432"));
	}
	
	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#hexToString(java.lang.String)}.
	 * @throws ConversionException 
	 */
	@Test
	(expected = ConversionException.class)
	public final void testInvalidHexToString() throws ConversionException
	{
		assertEquals("not is not valid", ConversionUtils.hexToString("paosd9d"));
	}

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#hexToStringNoException(java.lang.String)}.
	 */
	@Test
	public final void testHexToStringNoException()
	{
		assertEquals("[invalid hex]", ConversionUtils.hexToStringNoException("hello :)"));
	}

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#base64ToString(java.lang.String)}.
	 */
	@Test
	public final void testBase64ToString()
	{
		assertEquals("GOOD", ConversionUtils.base64ToString("R09PRA=="));
	}

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#stringToBase64(java.lang.String)}.
	 */
	@Test
	public final void testStringToBase64()
	{
		assertEquals("R09PRA==", ConversionUtils.stringToBase64("GOOD"));
	}

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#custom(pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails, java.lang.String)}.
	 */
	@Test
	public final void testCustomBase64DecodingConversion()
	{
		final FormatterDetails customFormatter = new FormatterDetails();
		final FormatterFunction function = new FormatterFunction();
		function.setSubstringConversion(new SubstringConversionFormatterDetails());
		function.getSubstringConversion().setStartTag("<Body>");
		function.getSubstringConversion().setEndTag("</Body>");
		function.getSubstringConversion().setKeepTags(true);
		function.getSubstringConversion().setFormat(ConversionMethod.BASE_64_DECODE);
		customFormatter.getFunction().add(function);
		
		assertEquals("<Body>GOOD</Body>", FormattingUtils.custom(customFormatter, "<Body>R09PRA==</Body>"));
	}
	
	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#custom(pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails, java.lang.String)}.
	 */
	@Test
	public final void testCustomBase64DecodingConversionNoTags()
	{
		final FormatterDetails customFormatter = new FormatterDetails();
		final FormatterFunction function = new FormatterFunction();
		function.setSubstringConversion(new SubstringConversionFormatterDetails());
		function.getSubstringConversion().setStartTag("<Body>");
		function.getSubstringConversion().setEndTag("</Body>");
		function.getSubstringConversion().setKeepTags(false);
		function.getSubstringConversion().setFormat(ConversionMethod.BASE_64_DECODE);
		customFormatter.getFunction().add(function);
		
		assertEquals("GOOD", FormattingUtils.custom(customFormatter, "<Body>R09PRA==</Body>"));
	}
	
	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#custom(pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails, java.lang.String)}.
	 */
	@Test
	public final void testCustomBase64DecodingWithExtract()
	{
		final FormatterDetails customFormatter = new FormatterDetails();
		
		final FormatterFunction function1 = new FormatterFunction();
		function1.setSubstringConversion(new SubstringConversionFormatterDetails());
		function1.getSubstringConversion().setStartTag("<Body>");
		function1.getSubstringConversion().setEndTag("</Body>");
		function1.getSubstringConversion().setKeepTags(true);
		function1.getSubstringConversion().setFormat(ConversionMethod.BASE_64_DECODE);
		customFormatter.getFunction().add(function1);
		
		final FormatterFunction function2 = new FormatterFunction();
		function2.setSubstringExtract(new SubstringExtractFormatterDetails());
		function2.getSubstringExtract().setStartTag("<Body>");
		function2.getSubstringExtract().setEndTag("</Body>");
		function2.getSubstringExtract().setKeepTags(false);
		customFormatter.getFunction().add(function2);
		
		assertEquals("GOOD", FormattingUtils.custom(customFormatter, "some other stuff <Body>R09PRA==</Body> and even more stuff"));
	} 

	/**
	 * Test method for {@link pl.baczkowicz.mqttspy.ui.utils.FormattingUtils#convert(pl.baczkowicz.mqttspy.configuration.generated.ConversionMethod, java.lang.String)}.
	 */
	@Test
	public final void testConvert()
	{
		assertEquals("test", FormattingUtils.convert(ConversionMethod.PLAIN, "test"));
		
		assertEquals("test2", FormattingUtils.convert(ConversionMethod.HEX_DECODE, "7465737432"));
		assertEquals("7465737431", FormattingUtils.convert(ConversionMethod.HEX_ENCODE, "test1"));

		assertEquals("GOOD", FormattingUtils.convert(ConversionMethod.BASE_64_DECODE, "R09PRA=="));
		assertEquals("R09PRA==", FormattingUtils.convert(ConversionMethod.BASE_64_ENCODE, "GOOD"));
	}
	
	@Test
	public final void testReplaceCharacters()	
	{
		assertEquals("23", FormattingUtils.replaceCharacters(ConversionMethod.HEX_ENCODE, "#", 0, 40, null));
		assertEquals("-23-1", FormattingUtils.replaceCharacters(ConversionMethod.HEX_ENCODE, "#1", 0, 40, "-"));
	}

}
