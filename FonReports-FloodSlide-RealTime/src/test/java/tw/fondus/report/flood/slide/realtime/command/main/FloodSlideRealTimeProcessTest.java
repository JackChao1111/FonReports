package tw.fondus.report.flood.slide.realtime.command.main;

import org.junit.Test;

import tw.fondus.report.flood.slide.realtime.command.process.ProgramArguments;

/**
 * The unit test of FloodSlideRealTimeProcess.
 * 
 * @author Chao
 *
 */
public class FloodSlideRealTimeProcessTest {
	@Test
	public void run(){
		String[] args = new String[]{
				"-b",
				"src/test/resources/",
				"-i",
				"rest.properties,SlideMapping.xml,Template_RealTime.pptx"
				};
		
		ProgramArguments arguments = new ProgramArguments();
		new FloodSlideRealTimeProcess().run( args, arguments );
	}
}
