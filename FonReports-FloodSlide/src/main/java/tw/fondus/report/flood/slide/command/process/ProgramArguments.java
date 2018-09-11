package tw.fondus.report.flood.slide.command.process;

import com.beust.jcommander.Parameter;

/**
 * Standard arguments of FonReport-FloodSlide Command-Line Interface.
 * 
 * @author Chao
 *
 */
public class ProgramArguments {
	@Parameter(names = { "--base", "-b" }, required = true, description = "The base of working directory.")
	protected String basePath;
	
	@Parameter(names = { "--properties", "-p" }, required = true, description = "The properties of configuration.")
	protected String properties;

	@Parameter(names = { "--help", "-h" }, description = "Show how to usage.", help = true)
	protected boolean help = false;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath( String basePath ) {
		this.basePath = basePath;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties( String properties ) {
		this.properties = properties;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp( boolean help ) {
		this.help = help;
	}
}
