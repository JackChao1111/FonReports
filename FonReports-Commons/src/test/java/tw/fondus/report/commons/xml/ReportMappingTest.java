package tw.fondus.report.commons.xml;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;

/**
 * The unit test of serialization/deserialization with ReportMapping.
 * 
 * @author Brad Chen
 *
 */
public class ReportMappingTest {
	private File xml;

	@Before
	public void setUp() throws Exception {
		this.xml = Paths.get( "src/test/resources/word/ReportMapping.xml" ).toFile();
		Preconditions.checkState( this.xml.exists(), "File doesn't exist!" );
	}

	@Test
	public void test() throws Exception {
		ReportMapping reportMapping = XMLUtils.fromXML( this.xml, ReportMapping.class );

		reportMapping.getImages().getMappings().forEach( mapping -> {
			System.out.println( mapping.getId() + "\t" + mapping.getValue() );
		} );
		
		reportMapping.getTexts().getMappings().forEach( mapping -> {
			System.out.println( mapping.getId() + "\t" + mapping.getValue() );
		} );
	}

}
