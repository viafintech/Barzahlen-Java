package de.barzahlen.request.xml;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Tests the XMLInfo class
 *
 * @author Jesus Javier Nuno Garcia
 */
public class XMLInfoTest {

	/**
	 * A variable to store a XMLInfo object, in order to test its features
	 */
	protected XMLInfo xmlInfo;

	@Before
	public void setUp() throws Exception {
		this.xmlInfo = new XMLInfo() {

			@Override
			protected void initHandlers() {
				//
			}
		};
	}

	/**
	 * Tests the method that reads a xml file (1)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testReadXMLFile1() throws Exception {
		this.xmlInfo.readXMLFile("", 200);
	}

	/**
	 * Tests the method that reads a xml file (2)
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testReadXMLFile2() throws Exception {
		this.xmlInfo.readXMLFile("", 400);
	}

	/**
	 * Tests check parameters method
	 */
	public void testCheckParameters() {
		assertFalse("Check Parameters", this.xmlInfo.checkParameters());
	}
}
