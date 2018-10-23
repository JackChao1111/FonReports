package tw.fondus.report.flood.slide.realtime.xml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.flood.slide.realtime.xml.mapping.SlideMapping;

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
		this.path = Paths.get( "src/test/resources/SlideMapping.xml" );
		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}
	
	@Test
	public void run() throws Exception{
		SlideMapping slideMapping = XMLUtils.fromXML( this.path.toFile(), SlideMapping.class );
		System.out.println( slideMapping.getCounties().get( 0 ).getCity() );
		Assert.assertNotNull( slideMapping );
	}
}
