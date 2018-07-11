package tw.fondus.report.commons.xml;

import java.io.File;

import org.junit.Test;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.commons.xml.pptx.SlideMapping;

public class SlideMappingTest {
	@Test
	public void run() {
		try {
			SlideMapping slideMapping = XMLUtils.fromXML( new File( "src/test/resources/ppt/SlideMapping.xml" ),
					SlideMapping.class );

			System.out.println( Strman.append( slideMapping.getCounties().getList().get( 0 ).getId(), ",",
					slideMapping.getCounties().getList().get( 0 ).geteName() ) );
			System.out.println( Strman.append( slideMapping.getSlideImages().getList().get( 0 ).getId(), ",",
					slideMapping.getSlideImages().getList().get( 0 ).getValue(), ",",
					slideMapping.getSlideImages().getList().get( 0 ).getLocation().getOffX().toString() ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
