package tw.fondus.report.commons.word;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.report.commons.util.ReportsDocxUtils;

/**
 * The unit test of replace document variables.
 * 
 * @author Brad Chen
 *
 */
public class VariableReplaceTest {
	private File word;

	@Before
	public void setUp() throws Exception {
		Files.deleteIfExists( Paths.get( "src/test/resources/word/VariableReplace.docx" ) );

		this.word = Paths.get( "src/test/resources/word/TemplateExample.docx" ).toFile();
		Preconditions.checkState( this.word.exists(), "File doesn't exist!" );
	}

	@Test
	public void test() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( this.word );
			
			Map<String, String> mappings = new HashMap<String, String>();
			mappings.put("colour", "green");
			mappings.put("icecream", "chocolate");
			
			ReportsDocxUtils.replaceVariable( wordMLPackage, mappings );
			ReportsDocxUtils.save( wordMLPackage, "src/test/resources/word/VariableReplace.docx" );
			
		} catch (Docx4JException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
