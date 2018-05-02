package tw.fondus.report.commons.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;

import strman.Strman;

/**
 * 
 * 
 * @author Brad Chen
 *
 */
public class ReportsDocxUtils {

	/**
	 * Save word to the path.
	 * 
	 * @param wordMLPackage
	 * @param path
	 * @throws Docx4JException
	 */
	public static void save( WordprocessingMLPackage wordMLPackage, String path ) throws Docx4JException {
		wordMLPackage.save( Paths.get( path ).toFile() );
	}

	/**
	 * Find key word inside word.
	 * 
	 * @param wordMLPackage
	 * @param key
	 * @return
	 */
	@SuppressWarnings( "deprecation" )
	public static Optional<P> findKeyWord( WordprocessingMLPackage wordMLPackage, String key ) {
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		List<Object> bodyObjects = documentPart.getContent();

		return bodyObjects.stream().map( object -> (P) object ).filter( p -> p.toString().contains( key ) ).findFirst();
	}

	/**
	 * Add image to word with center.
	 * 
	 * @param wordMLPackage
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	public static P createImage( WordprocessingMLPackage wordMLPackage, String imagePath ) throws Exception {
		return createImage( wordMLPackage, imagePath, JcEnumeration.CENTER );
	}

	/**
	 * Add image to word.
	 * 
	 * @param wordMLPackage
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	public static P createImage( WordprocessingMLPackage wordMLPackage, String imagePath, JcEnumeration jcEnumeration )
			throws Exception {
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart( wordMLPackage,
				new File( imagePath ) );
		Inline inline = imagePart.createImageInline( null, null, 0, 1, false );

		ObjectFactory factory = Context.getWmlObjectFactory();
		P p = factory.createP();
		R run = factory.createR();
		p.getContent().add( run );

		Drawing drawing = factory.createDrawing();
		run.getContent().add( drawing );
		drawing.getAnchorOrInline().add( inline );

		PPr pPr = p.getPPr();
		if ( pPr == null ) {
			pPr = factory.createPPr();
		}

		Jc jc = pPr.getJc();
		if ( jc == null ) {
			jc = new Jc();
		}

		jc.setVal( jcEnumeration );
		pPr.setJc( jc );
		p.setPPr( pPr );

		return p;
	}

	/**
	 * Replace keyword.
	 * 
	 * @param wordMLPackage
	 * @param mappings
	 * @throws JAXBException
	 * @throws Docx4JException
	 */
	public static void replaceVariable( WordprocessingMLPackage wordMLPackage, Map<String, String> mappings )
			throws JAXBException, Docx4JException {
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		documentPart.variableReplace( mappings );
	}

	/**
	 * Replace keyword to image.
	 * 
	 * @param wordMLPackage
	 * @param key
	 * @param imagePath
	 */
	public static void replaceToImage( WordprocessingMLPackage wordMLPackage, String key, String imagePath ) {
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

		Optional<P> optP = ReportsDocxUtils.findKeyWord( wordMLPackage, Strman.append( "#{", key, "}" ) );
		optP.ifPresent( p -> {
			List<Object> bodyObjects = documentPart.getContent();
			int i = bodyObjects.indexOf( p );
			bodyObjects.remove( p );

			try {
				bodyObjects.add( i, ReportsDocxUtils.createImage( wordMLPackage, imagePath ) );
			} catch (Exception e) {
				e.printStackTrace();
			}
		} );
	}

	public static void test( P paragraph, String key, String value ) {
		List<Object> runList = paragraph.getContent();
		boolean finded = runList.stream()
				.map( object -> (R) object )
				.map( r -> r.getContent()
						.stream()
						.filter( object -> object instanceof JAXBElement<?> )
						.map( object -> (JAXBElement<?>) object )
						.filter( element -> element.getValue() instanceof Text )
						.map( element -> (Text) element.getValue() )
						.filter( text -> text.getValue().equals( key ) )
						.findFirst() )
				.anyMatch( opt -> opt.isPresent() );

		System.out.println( finded );
	}
}
