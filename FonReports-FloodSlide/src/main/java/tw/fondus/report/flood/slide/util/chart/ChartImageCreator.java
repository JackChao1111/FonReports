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
	private BigDecimal hour6_Forecasting;
	private BigDecimal hour18_Historical;
	private BigDecimal hour24_Combine;

	public void createBarChartImage( PiTimeSeriesArray piTimeSeriesArray, String countyName, String exportPath ) {
		setHour6_Forecasting( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 74, 79 ) );
		setHour18_Historical( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 56, 73 ) );
		setHour24_Combine( PiSeriesUtils.calculateAccumulate( piTimeSeriesArray, 56, 79 ) );

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

	public BigDecimal getHour6_Forecasting() {
		return hour6_Forecasting;
	}

	public void setHour6_Forecasting( BigDecimal hour6_Forecasting ) {
		this.hour6_Forecasting = hour6_Forecasting;
	}

	public BigDecimal getHour18_Historical() {
		return hour18_Historical;
	}

	public void setHour18_Historical( BigDecimal hour18_Historical ) {
		this.hour18_Historical = hour18_Historical;
	}

	public BigDecimal getHour24_Combine() {
		return hour24_Combine;
	}

	public void setHour24_Combine( BigDecimal hour24_Combine ) {
		this.hour24_Combine = hour24_Combine;
	}

}
