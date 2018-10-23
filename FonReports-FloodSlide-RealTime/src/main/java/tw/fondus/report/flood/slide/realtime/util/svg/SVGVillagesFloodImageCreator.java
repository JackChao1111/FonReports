package tw.fondus.report.flood.slide.realtime.util.svg;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import strman.Strman;
import tw.fondus.commons.fews.pi.json.warning.PiWarning;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.commons.util.svg.Format;
import tw.fondus.commons.util.svg.SVGUtils;
import tw.fondus.commons.util.xml.Dom4JUtils;

public class SVGVillagesFloodImageCreator {
	private static final String CLASS_DEFAULT = "default";
	private static final String CLASS_WARNING1 = "warring1";
	private static final String LEVEL_WARNING1 = "高";
	private static final String CLASS_WARNING2 = "warring2";
	private static final String LEVEL_WARNING2 = "中";
	private static final String CLASS_WARNING3 = "warring3";
	private static final String LEVEL_WARNING3 = "低";

	public static void createImageBySVG( Map<String, PiWarning> piWarningMap, String templatePath ) throws Exception {
		Path svgPath = Paths.get( Strman.append( templatePath, StringUtils.PATH, "Villages.svg" ) );
		if ( Files.exists( svgPath ) ) {
			Document svgDocument = Dom4JUtils.parseXML( svgPath.toFile() );
			Element rootElement = svgDocument.getRootElement();
			List<Element> paths = rootElement.elements( "path" );
			String pathId = "v-id";
			String attributeClassName = "class";
			paths.forEach( path -> {
				PiWarning piWarning = piWarningMap.get( path.attributeValue( pathId ) );

				path.remove( path.attribute( attributeClassName ) );
				if ( piWarning.getWarning().equals( LEVEL_WARNING1 ) ) {
					path.addAttribute( attributeClassName, CLASS_WARNING1 );
				} else if ( piWarning.getWarning().equals( LEVEL_WARNING2 ) ) {
					path.addAttribute( attributeClassName, CLASS_WARNING2 );
				} else if ( piWarning.getWarning().equals( LEVEL_WARNING3 ) ) {
					path.addAttribute( attributeClassName, CLASS_WARNING3 );
				} else {
					path.addAttribute( attributeClassName, CLASS_DEFAULT );
				}
			} );

			String tempSVGPath = Strman.append( templatePath, StringUtils.PATH, "temp.svg" );
			Dom4JUtils.write( svgDocument, tempSVGPath );
			SVGUtils.setTranscoder( Format.PNG );
			SVGUtils.setWidth( 800 );
			SVGUtils.export( tempSVGPath, Strman.append( templatePath, StringUtils.PATH, "Villages.png" ) );
			Files.delete( Paths.get( tempSVGPath ) );
		} else {
			throw new FileNotFoundException( "Can not find SVG file." );
		}
	}
}
