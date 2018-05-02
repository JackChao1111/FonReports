package tw.fondus.report.command.main;

import org.junit.Test;

import tw.fondus.report.command.process.ProgramArguments;

/**
 * The unit test of FonReportsWordProcess.
 * 
 * @author Brad Chen
 *
 */
public class FonReportsWordProcessTest {

	@Test
	public void test() {
		String[] args = new String[]{
				"-b",
				"src/test/resources/",
				"-c",
				"ReportMapping.xml",
				"-t",
				"word/template.docx",
				"-o",
				"OuputReplace.docx"
				};
		
		ProgramArguments arguments = new ProgramArguments();
		new FonReportsWordProcess().run( args, arguments );
	}

}
