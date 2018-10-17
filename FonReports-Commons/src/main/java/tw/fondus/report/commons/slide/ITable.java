package tw.fondus.report.commons.slide;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.dml.CTPoint2D;
import org.docx4j.dml.CTPositiveSize2D;
import org.docx4j.dml.CTTable;
import org.docx4j.dml.Graphic;
import org.docx4j.dml.GraphicData;
import org.pptx4j.pml.CTGraphicalObjectFrame;

/**
 * The service is used to create table for slide part.
 * 
 * @author Chao
 *
 */
public interface ITable {

	/**
	 * Common process for create table for slide part.
	 * 
	 * @param extcX
	 * @param extcY
	 * @param offX
	 * @param offY
	 * @param dmlFactory
	 * @param pmlFactory
	 * @param ctTable
	 * @param graphicFrame
	 */
	default public void createTable( long extcX, long extcY, long offX, long offY, org.docx4j.dml.ObjectFactory dmlFactory,org.pptx4j.pml.ObjectFactory pmlFactory,
			CTTable ctTable, CTGraphicalObjectFrame graphicFrame ) {
		// Node Creation
		org.pptx4j.pml.CTGraphicalObjectFrameNonVisual nvGraphicFramePr = pmlFactory
				.createCTGraphicalObjectFrameNonVisual();
		org.docx4j.dml.CTNonVisualDrawingProps cNvPr = dmlFactory.createCTNonVisualDrawingProps();
		org.docx4j.dml.CTNonVisualGraphicFrameProperties cNvGraphicFramePr = dmlFactory
				.createCTNonVisualGraphicFrameProperties();
		org.docx4j.dml.CTGraphicalObjectFrameLocking graphicFrameLocks = new org.docx4j.dml.CTGraphicalObjectFrameLocking();
		org.docx4j.dml.CTTransform2D xfrm = dmlFactory.createCTTransform2D();
		Graphic graphic = dmlFactory.createGraphic();
		GraphicData graphicData = dmlFactory.createGraphicData();

		// Build the parent-child relationship of this slides.xml
		graphicFrame.setNvGraphicFramePr( nvGraphicFramePr );
		nvGraphicFramePr.setCNvPr( cNvPr );
		cNvPr.setName( "1" );
		nvGraphicFramePr.setCNvGraphicFramePr( cNvGraphicFramePr );
		cNvGraphicFramePr.setGraphicFrameLocks( graphicFrameLocks );
		graphicFrameLocks.setNoGrp( true );
		nvGraphicFramePr.setNvPr( pmlFactory.createNvPr() );

		graphicFrame.setXfrm( xfrm );

		CTPositiveSize2D ext = dmlFactory.createCTPositiveSize2D();
		ext.setCx( extcX );
		ext.setCy( extcY );

		xfrm.setExt( ext );

		CTPoint2D off = dmlFactory.createCTPoint2D();
		xfrm.setOff( off );
		off.setX( offX );
		off.setY( offY );

		graphicFrame.setGraphic( graphic );

		graphic.setGraphicData( graphicData );
		graphicData.setUri( "http://schemas.openxmlformats.org/drawingml/2006/table" );

		JAXBElement<CTTable> tbl = dmlFactory.createTbl( ctTable );
		graphicData.getAny().add( tbl );
	}
	
	/**
	 * Create contents of table.
	 * 
	 * @param contents
	 */
	public void createGrid( List<String[]> contents );
	
	/**
	 * Get table object.
	 * 
	 * @return
	 */
	public CTGraphicalObjectFrame getTable();

}
