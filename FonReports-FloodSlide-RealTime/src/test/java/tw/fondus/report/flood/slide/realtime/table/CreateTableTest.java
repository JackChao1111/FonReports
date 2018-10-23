package tw.fondus.report.flood.slide.realtime.table;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.report.commons.slide.ITable;
import tw.fondus.report.commons.util.ReportsPptxUtils;

/**
 * The unit test of creating table for flood slide.
 * 
 * @author Chao
 *
 */
public class CreateTableTest {
	private Path path;
	private List<String[]> contents;

	@Before
	public void setUp() {
		path = Paths.get( "src/test/resources/Template/Template_RealTime.pptx" );
		Preconditions.checkState( Files.exists( path ), "File doesn't exist!" );
		
		contents = new ArrayList<String[]>();
		contents.add( new String[] { "高雄市新興區", "1", "0.1", "1.1", "低" } );
		contents.add( new String[] { "高雄市新興區", "2", "0.2", "2.2", "低" } );
		contents.add( new String[] { "高雄市新興區", "3", "0.3", "3.3", "低" } );
		contents.add( new String[] { "高雄市新興區", "4", "0.4", "4.4", "低" } );
	}
	
	@Test
	public void run() throws Exception{
		PresentationMLPackage presentationMLPackage = ReportsPptxUtils.open( path );
		
		SlidePart slidePart = presentationMLPackage.getMainPresentationPart().getSlide( 0 );
		ITable floodTable = new FloodTable( 6096000, 6096000, 4064000, 3175000 );
		floodTable.createGrid( contents );
		ReportsPptxUtils.addTable( slidePart, floodTable.getTable() );
		
		presentationMLPackage.save( Paths.get( "src/test/resources/Output/output.pptx" ).toFile() );
	}
}
