package tw.fondus.report.commons.xml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.commons.xml.pptx.SlideMapping;

/**
 * The unit test of serialization/deserialization with SlideMapping.
 * 
 * @author Chao
 *
 */
public class SlideMappingTest {
	private Path path;

	@Before
	public void setUp() {
		this.path = Paths.get( "src/test/resources/ppt/SlideMapping.xml" );

		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}

	@Test
	public void run() {
		try {
			SlideMapping slideMapping = XMLUtils.fromXML( this.path.toFile(), SlideMapping.class );

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
