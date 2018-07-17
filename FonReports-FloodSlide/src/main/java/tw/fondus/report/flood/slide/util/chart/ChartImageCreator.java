package tw.fondus.report.flood.slide.util.chart;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import strman.Strman;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesArray;
import tw.fondus.commons.fews.pi.util.timeseries.PiSeriesUtils;
import tw.fondus.commons.util.string.StringUtils;

public class ChartImageCreator {
	private static final BigDecimal MISSING_VALUE = new BigDecimal( "-999" );
	private BigDecimal hour6_forecasting;
	private BigDecimal hour18_historical;
	private BigDecimal hour24_combinee;

	public void createBarChartImage( PiTimeSeriesArray piTimeSeriesArray, String countyName, String exportPath ) {
		setHour6_forecasting( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 74, 79 ) );
		setHour18_historical( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 56, 73 ) );
		setHour24_combinee( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 56, 79 ) );

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		IntStream.rangeClosed( 56, 79 ).forEach( i -> {
			int time = i - 73;
			if ( piTimeSeriesArray.getSeries()[i].getValue().compareTo( MISSING_VALUE ) == 0 ) {
				dataset.setValue( 0, countyName, String.valueOf( time ) );
			} else {
				dataset.setValue( piTimeSeriesArray.getSeries()[i].getValue(), countyName, String.valueOf( time ) );
			}
		} );

		JFreeChart barChart = ChartFactory.createBarChart( "", "", "", dataset, PlotOrientation.VERTICAL, false, true,
				false );

		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter( new StandardBarPainter() );
		renderer.setSeriesPaint( 0, Color.blue );
		renderer.setBaseOutlinePaint( Color.black );
		renderer.setDrawBarOutline( true );
		plot.setRangeGridlinePaint( Color.black );
		plot.setBackgroundPaint( Color.white );
		plot.getDomainAxis().setCategoryMargin( 0.0 );
		try {
			ChartUtilities.saveChartAsPNG( Paths.get(
					Strman.append( exportPath, StringUtils.PATH, "chart", StringUtils.PATH, countyName, ".png" ) )
					.toFile(), barChart, 1200, 300 );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getHour6_forecasting() {
		return hour6_forecasting;
	}

	public void setHour6_forecasting( BigDecimal hour6_forecasting ) {
		this.hour6_forecasting = hour6_forecasting;
	}

	public BigDecimal getHour18_historical() {
		return hour18_historical;
	}

	public void setHour18_historical( BigDecimal hour18_historical ) {
		this.hour18_historical = hour18_historical;
	}

	public BigDecimal getHour24_combinee() {
		return hour24_combinee;
	}

	public void setHour24_combinee( BigDecimal hour24_combinee ) {
		this.hour24_combinee = hour24_combinee;
	}

}
