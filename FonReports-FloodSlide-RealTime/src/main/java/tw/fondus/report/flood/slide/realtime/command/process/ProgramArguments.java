package tw.fondus.report.flood.slide.realtime.command.process;

import java.util.List;

import com.beust.jcommander.Parameter;

import tw.fondus.commons.fews.pi.util.adapter.FileListConverter;

/**
 * Standard arguments of FonReport-FloodSlide-RealTime Command-Line Interface.
 * 
 * @author Chao
 *
 */
public class ProgramArguments {
	@Parameter( names = { "--base", "-b" }, required = true, description = "The base of working directory." )
	protected String basePath;

	@Parameter( names = { "--input",
			"-i" }, required = true, description = "The input file list with comma, and order is fixed.", listConverter = FileListConverter.class )
	protected List<String> inputs;

	@Parameter( names = { "--tdir",
			"-td" }, description = "The template file folder, relative to the current working directory." )
	protected String templateDir = "Template/";

	@Parameter( names = { "--odir",
			"-od" }, description = "The output file folder, relative to the current working directory." )
	protected String outputDir = "Output/";

	@Parameter( names = { "--help", "-h" }, description = "Show how to usage.", help = true )
	protected boolean help = false;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath( String basePath ) {
		this.basePath = basePath;
	}
	
	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs( List<String> inputs ) {
		this.inputs = inputs;
	}
	
	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir( String templateDir ) {
		this.templateDir = templateDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir( String outputDir ) {
		this.outputDir = outputDir;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp( boolean help ) {
		this.help = help;
	}
}
