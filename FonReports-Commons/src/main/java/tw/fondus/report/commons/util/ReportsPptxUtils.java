package tw.fondus.report.commons.util;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.pptx4j.jaxb.Context;
import org.pptx4j.pml.CTGraphicalObjectFrame;
import org.pptx4j.pml.Pic;

import tw.fondus.report.commons.xml.pptx.Image;

public class ReportsPptxUtils {

	public static PresentationMLPackage open( String path ) throws Docx4JException {
		PresentationMLPackage presentationMLPackage = (PresentationMLPackage) OpcPackage.load( new File( path ) );
		return presentationMLPackage;
	}

	public static PresentationMLPackage open( Path path ) throws Docx4JException {
		PresentationMLPackage presentationMLPackage = (PresentationMLPackage) OpcPackage.load( path.toFile() );
		return presentationMLPackage;
	}
	
	public static void addTable(SlidePart slidePart, CTGraphicalObjectFrame graphicFrame) throws Docx4JException{
		slidePart.getContents()
		.getCSld()
		.getSpTree()
		.getSpOrGrpSpOrGraphicFrame()
		.add( graphicFrame );
	}

	public static void addImage( PresentationMLPackage presentationMLPackage, SlidePart slidePart, String imagePath,
			Image image ) throws Docx4JException, Exception {
		slidePart.getContents()
				.getCSld()
				.getSpTree()
				.getSpOrGrpSpOrGraphicFrame()
				.add( createImageObject( presentationMLPackage, slidePart, imagePath, image ) );
	}

	public static Object createImageObject( PresentationMLPackage presentationMLPackage, SlidePart slidePart,
			String imagePath, Image image ) throws Exception {
		File imageFile = new File( imagePath );
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart( presentationMLPackage, slidePart,
				imageFile );

		BigDecimal mumultiply = new BigDecimal( "12700" );
		DecimalFormat df = new DecimalFormat( "0" );

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put( "id1", "4" );
		mappings.put( "name", "Image" );
		mappings.put( "descr", imageFile.getName() );
		mappings.put( "rEmbedId", imagePart.getSourceRelationships().get( 0 ).getId() );
		mappings.put( "offx", df.format( mumultiply.multiply( image.getLocation().getOffX() ).doubleValue() ) );
		mappings.put( "offy", df.format( mumultiply.multiply( image.getLocation().getOffY() ).doubleValue() ) );
		mappings.put( "extcx", df.format( mumultiply.multiply( image.getLocation().getExtcX() ).doubleValue() ) );
		mappings.put( "extcy", df.format( mumultiply.multiply( image.getLocation().getExtcY() ).doubleValue() ) );

		return XmlUtils.unmarshallFromTemplate( SAMPLE_PICTURE, mappings, Context.jcPML, Pic.class );
	}

	public static Object createPicture( PresentationMLPackage presentationMLPackage, SlidePart slidePart,
			String imagePath, BigDecimal offX, BigDecimal offY, BigDecimal extcX, BigDecimal extcY ) throws Exception {
		File imageFile = new File( imagePath );
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart( presentationMLPackage, slidePart,
				imageFile );

		BigDecimal mumultiply = new BigDecimal( "12700" );
		DecimalFormat df = new DecimalFormat( "0" );

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put( "id1", "4" );
		mappings.put( "name", "Image" );
		mappings.put( "descr", imageFile.getName() );
		mappings.put( "rEmbedId", imagePart.getSourceRelationships().get( 0 ).getId() );
		mappings.put( "offx", df.format( mumultiply.multiply( offX ).doubleValue() ) );
		mappings.put( "offy", df.format( mumultiply.multiply( offY ).doubleValue() ) );
		mappings.put( "extcx", df.format( mumultiply.multiply( extcX ).doubleValue() ) );
		mappings.put( "extcy", df.format( mumultiply.multiply( extcY ).doubleValue() ) );

		return XmlUtils.unmarshallFromTemplate( SAMPLE_PICTURE, mappings, Context.jcPML, Pic.class );
	}

	private static String SAMPLE_PICTURE = "<p:pic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\"> "
			+ "<p:nvPicPr>" + "<p:cNvPr id=\"${id1}\" name=\"${name}\" descr=\"${descr}\"/>" + "<p:cNvPicPr>"
			+ "<a:picLocks noChangeAspect=\"1\"/>" + "</p:cNvPicPr>" + "<p:nvPr/>" + "</p:nvPicPr>" + "<p:blipFill>"
			+ "<a:blip r:embed=\"${rEmbedId}\" cstate=\"print\"/>" + "<a:stretch>" + "<a:fillRect/>" + "</a:stretch>"
			+ "</p:blipFill>" + "<p:spPr>" + "<a:xfrm>" + "<a:off x=\"${offx}\" y=\"${offy}\"/>"
			+ "<a:ext cx=\"${extcx}\" cy=\"${extcy}\"/>" + "</a:xfrm>" + "<a:prstGeom prst=\"rect\">" + "<a:avLst/>"
			+ "</a:prstGeom>" + "</p:spPr>" + "</p:pic>";

	public static void save( PresentationMLPackage presentationMLPackage, String path ) throws Docx4JException {
		presentationMLPackage.save( Paths.get( path ).toFile() );
	}
}
