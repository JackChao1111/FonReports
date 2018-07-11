package tw.fondus.report.flood.slide.table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.junit.Before;
import org.junit.Test;

public class CreateTableTest {
	private List<String[]> forecastingContents;
	private List<String[]> hotSpotContents;
	private List<String[]> countyEWContents;
	private List<String[]> countyIWContents;

	@Before
	public void data() {
		forecastingContents = new ArrayList<String[]>();
		forecastingContents.add( new String[] { "台北地區", "1" } );
		forecastingContents.add( new String[] { "新北地區", "2" } );
		forecastingContents.add( new String[] { "桃園地區", "3" } );
		forecastingContents.add( new String[] { "新竹地區", "4" } );

		hotSpotContents = new ArrayList<String[]>();
		hotSpotContents.add( new String[] { "台北地區", "1", "4" } );
		hotSpotContents.add( new String[] { "新北地區", "2", "3" } );
		hotSpotContents.add( new String[] { "桃園地區", "3", "2" } );
		hotSpotContents.add( new String[] { "新竹地區", "4", "1" } );

		countyEWContents = new ArrayList<String[]>();
		countyEWContents.add( new String[] { "River1", "Basin1", "Protect1", "Point1", "Potention1" } );
		countyEWContents.add( new String[] { "River2", "Basin2", "Protect2", "Point2", "Potention2" } );
		countyEWContents.add( new String[] { "River3", "Basin3", "Protect3", "Point3", "Potention3" } );
		countyEWContents.add( new String[] { "River4", "Basin4", "Protect4", "Point4", "Potention4" } );

		countyIWContents = new ArrayList<String[]>();
		countyIWContents.add( new String[] { "台北地區", "信義區1", "低窪1", "疏散1" } );
		countyIWContents.add( new String[] { "新北地區", "信義區2", "低窪2", "疏散2" } );
		countyIWContents.add( new String[] { "桃園地區", "信義區3", "低窪3", "疏散3" } );
		countyIWContents.add( new String[] { "新竹地區", "信義區4", "低窪4", "疏散4" } );
	}

	@Test
	public void run() {
		try {
			PresentationMLPackage presentationMLPackage = (PresentationMLPackage) OpcPackage
					.load( new File( "src/test/resources/template/Template_WRAP_搜圖成果.pptx" ) );

			// Forecasting6hrRainfallTable: 2302791, 5100911, 4944980, 1048887
			// HotSpotCountTable: 2456798, 7080656, 4971557, 885490
			// CountyEWTable: 5546381, 2398034, 3429001, 985696
			// CountyIWTable: 5546382, 2559095, 3429000, 3660730

			SlidePart slidePart1 = presentationMLPackage.getMainPresentationPart().getSlide( 1 );
			Forecasting6hrRainfallTable fTable = new Forecasting6hrRainfallTable( 2302791, 5100911, 4944980, 1048887 );
			fTable.createGrid( forecastingContents );
			slidePart1.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().add( fTable.getTable() );

			SlidePart slidePart2 = presentationMLPackage.getMainPresentationPart().getSlide( 2 );
			HotSpotCountTable hTable = new HotSpotCountTable( 2456798, 7080656, 4971557, 885490 );
			hTable.createGrid( hotSpotContents );
			slidePart2.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().add( hTable.getTable() );

			SlidePart slidePart3 = presentationMLPackage.getMainPresentationPart().getSlide( 3 );
			CountyEWTable cEWTable = new CountyEWTable( 5546381, 2398034, 3429001, 985696 );
			cEWTable.createGrid( countyEWContents );
			slidePart3.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().add( cEWTable.getTable() );

			CountyIWTable cIWTable = new CountyIWTable( 5546382, 2559095, 3429000, 3660730 );
			cIWTable.createGrid( countyIWContents );
			slidePart3.getContents().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().add( cIWTable.getTable() );

			presentationMLPackage.getMainPresentationPart().addSlide( slidePart3 );

			presentationMLPackage.save( new File( "src/test/resources/output.pptx" ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}