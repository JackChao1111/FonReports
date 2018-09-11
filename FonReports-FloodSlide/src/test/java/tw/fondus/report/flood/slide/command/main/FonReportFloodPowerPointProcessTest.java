package tw.fondus.report.flood.slide.command.main;

import org.junit.Test;

import tw.fondus.report.flood.slide.command.process.ProgramArguments;

/**
 * The unit test of FonReportFloodPowerPointProcess.
 * 
 * @author Chao
 *
 */
public class FonReportFloodPowerPointProcessTest {
	@Test
	public void run(){
		String[] args = new String[]{
				"-b",
				"src/test/resources/",
				"-p",
				"config.properties"
				};
		
		ProgramArguments arguments = new ProgramArguments();
		new FonReportFloodPowerPointProcess().run( args, arguments );
	}
}
