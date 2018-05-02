package tw.fondus.report.commons.word;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.report.commons.util.ReportsDocxUtils;

/**
 * The unit test of add image to the document.
 * 
 * @author Brad Chen
 *
 */
public class AddImageTest {
	private File word;
	private File image;

	@Before
	public void setUp() throws Exception {
		Files.deleteIfExists( Paths.get( "src/test/resources/word/AddImage.docx" ) );
		
		this.word = Paths.get( "src/test/resources/word/FONReportTestWORD.docx" ).toFile();
		this.image = Paths.get( "src/test/resources/word/imgs/Test.PNG" ).toFile();
		Preconditions.checkState( this.word.exists(), "File doesn't exist!" );
		Preconditions.checkState( this.image.exists(), "File doesn't exist!" );
	}

	@Test
	public void test() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( this.word );

			ReportsDocxUtils.replaceToImage( wordMLPackage, "Test", this.image.getPath() );
			ReportsDocxUtils.save( wordMLPackage, "src/test/resources/word/AddImage.docx" );

		} catch (Docx4JException e) {
			e.printStackTrace();
		}
	}

}
