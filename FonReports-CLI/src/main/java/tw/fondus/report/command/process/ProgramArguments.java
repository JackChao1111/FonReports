package tw.fondus.report.command.process;

import com.beust.jcommander.Parameter;

/**
 * Standard arguments of FonReports Command-Line Interface.
 * 
 * @author Brad Chen
 *
 */
public class ProgramArguments {
	@Parameter(names = { "--base", "-b" }, required = true, description = "The base of working directory.")
	protected String basePath;
	
	@Parameter(names = { "--config", "-c" }, required = true, description = "The input of report mapping of XML configuration.")
	protected String config;
	
	@Parameter(names = { "--template", "-t" }, required = true, description = "The template of word document.")
	protected String template;
	
	@Parameter(names = { "--output", "-o" }, description = "The output of document.")
	protected String output = "Output.docx";

	@Parameter(names = { "--help", "-h" }, description = "Show how to usage.", help = true)
	protected boolean help = false;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath( String basePath ) {
		this.basePath = basePath;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig( String config ) {
		this.config = config;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate( String template ) {
		this.template = template;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput( String output ) {
		this.output = output;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp( boolean help ) {
		this.help = help;
	}
}
