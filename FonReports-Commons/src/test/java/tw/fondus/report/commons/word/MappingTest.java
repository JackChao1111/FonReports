package tw.fondus.report.commons.word;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.commons.util.ReportsDocxUtils;
import tw.fondus.report.commons.xml.ReportMapping;

/**
 * The unit test of of replace document variables with ReportMapping.
 * 
 * @author Brad Chen
 *
 */
public class MappingTest {
	private File xml;
	private File input;
	
	@Before
	public void setUp() throws Exception {
		Files.deleteIfExists( Paths.get( "src/test/resources/word/VariableReplace.docx" ) );
		
		this.input = Paths.get( "src/test/resources/word/FONReportTestWORD.docx" ).toFile();
		Preconditions.checkState( this.input.exists(), "File doesn't exist!" );
		
		this.xml = Paths.get( "src/test/resources/word/ReportMapping.xml" ).toFile();
		Preconditions.checkState( this.xml.exists(), "File doesn't exist!" );
	}

	@Test
	public void test() throws Exception {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( this.input );
		
		ReportMapping reportMapping = XMLUtils.fromXML( this.xml, ReportMapping.class );
		reportMapping.getImages().getMappings().forEach( mapping -> {
			ReportsDocxUtils.replaceToImage( wordMLPackage, mapping.getId(), mapping.getValue() );
		} );
		
		Map<String, String> mappings = reportMapping.getTexts().getMappings().stream()
			.collect( Collectors.toMap( mapping -> mapping.getId(), mapping -> mapping.getValue() ) );
		ReportsDocxUtils.replaceVariable( wordMLPackage, mappings );
		
		ReportsDocxUtils.save( wordMLPackage, "src/test/resources/word/VariableReplace.docx" );
	}
}
