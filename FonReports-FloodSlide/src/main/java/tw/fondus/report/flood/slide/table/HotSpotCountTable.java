package tw.fondus.report.flood.slide.table;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTTable;
import org.docx4j.dml.CTTableCell;
import org.docx4j.dml.CTTableCol;
import org.docx4j.dml.CTTableGrid;
import org.docx4j.dml.CTTableRow;
import org.pptx4j.pml.CTGraphicalObjectFrame;

import tw.fondus.report.commons.slide.ITable;

/**
 * Table for Taiwan hot spot count.
 * 
 * @author Chao
 *
 */
public class HotSpotCountTable implements ITable {
	private org.docx4j.dml.ObjectFactory dmlFactory;
	private org.pptx4j.pml.ObjectFactory pmlFactory;
	private CTTable ctTable;
	private CTGraphicalObjectFrame graphicFrame;
	private int countEW = 0;
	private int countIW = 0;

	public HotSpotCountTable(long extcX, long extcY, long offX, long offY) {
		dmlFactory = new org.docx4j.dml.ObjectFactory();
		pmlFactory = new org.pptx4j.pml.ObjectFactory();
		graphicFrame = pmlFactory.createCTGraphicalObjectFrame();
		ctTable = dmlFactory.createCTTable();
		this.createTable( extcX, extcY, offX, offY, dmlFactory, pmlFactory, ctTable, graphicFrame );
	}

	@Override
	public void createGrid( List<String[]> contents ) {
		try {
			CTTableGrid ctTableGrid = dmlFactory.createCTTableGrid();
			CTTableCol gridCol = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol );
			gridCol.setW( 716169 );

			CTTableCol gridCol2 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol2 );
			gridCol2.setW( 813592 );

			CTTableCol gridCol3 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol3 );
			gridCol3.setW( 927037 );

			CTTableRow titleRow = dmlFactory.createCTTableRow();
			titleRow.setH( 311151 );
			titleRow.getTc().add( createTableCell( "地區" ) );
			titleRow.getTc().add( createTableCell( "外水" ) );
			titleRow.getTc().add( createTableCell( "內水一級" ) );
			ctTable.getTr().add( titleRow );

			contents.forEach( content -> {
				try {
					countEW += Integer.valueOf( content[1] );
					countIW += Integer.valueOf( content[2] );
					CTTableRow contentRow = dmlFactory.createCTTableRow();
					contentRow.setH( 277978 );
					contentRow.getTc().add( createTableCell( content[0] ) );
					contentRow.getTc().add( createTableCell( content[1] ) );
					contentRow.getTc().add( createTableCell( content[2] ) );
					ctTable.getTr().add( contentRow );
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			} );
			
			CTTableRow contentRow = dmlFactory.createCTTableRow();
			contentRow.setH( 277978 );
			contentRow.getTc().add( createTableCell( "合計" ) );
			contentRow.getTc().add( createTableCell( String.valueOf( countEW ) ) );
			contentRow.getTc().add( createTableCell( String.valueOf( countIW ) ) );
			ctTable.getTr().add( contentRow );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public CTGraphicalObjectFrame getTable() {
		return graphicFrame;
	}

	private CTTableCell createTableCell( String cellText ) throws JAXBException {
		String contents = "<a:tc  xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" + "<a:txBody>"
				+ "<a:bodyPr/>" + "<a:lstStyle/>" + "<a:p>" + "<a:pPr algn=\"ctr\" rtl=\"0\" fontAlgn=\"b\" />"
				+ "<a:r>" + "<a:rPr lang=\"zh-TW\" altLang=\"en-US\" sz=\"1200\" b=\"1\" dirty=\"0\">"
				+ "<a:effectLst />"
				+ "<a:latin typeface=\"微軟正黑體\" panose=\"020B0604030504040204\" pitchFamily=\"34\" charset=\"-120\" />"
				+ "<a:ea typeface=\"微軟正黑體\" panose=\"020B0604030504040204\" pitchFamily=\"34\" charset=\"-120\" />"
				+ "</a:rPr>" + "<a:t>" + cellText + "</a:t>" + "</a:r>" + "</a:p>" + "</a:txBody>"
				+ "<a:tcPr marL=\"17925\" marR=\"17925\" marT=\"11950\" marB=\"11950\" anchor=\"ctr\">"
				+ "	<a:lnL w=\"6350\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                        "
				+ "		<a:solidFill>                                                                "
				+ "			<a:schemeClr val=\"tx1\" />                                              "
				+ "		</a:solidFill>                                                               "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "		<a:round />                                                                  "
				+ "		<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "		<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "	</a:lnL>                                                                         "
				+ "	<a:lnR w=\"6350\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                        "
				+ "		<a:solidFill>                                                                "
				+ "			<a:schemeClr val=\"tx1\" />                                              "
				+ "		</a:solidFill>                                                               "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "		<a:round />                                                                  "
				+ "		<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "		<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "	</a:lnR>                                                                         "
				+ "	<a:lnT w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                       "
				+ "		<a:solidFill>                                                                "
				+ "			<a:schemeClr val=\"tx1\" />                                              "
				+ "		</a:solidFill>                                                               "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "		<a:round />                                                                  "
				+ "		<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "		<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "	</a:lnT>                                                                         "
				+ "	<a:lnB w=\"6350\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                        "
				+ "		<a:solidFill>                                                                "
				+ "			<a:schemeClr val=\"tx1\" />                                              "
				+ "		</a:solidFill>                                                               "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "		<a:round />                                                                  "
				+ "		<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "		<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                            "
				+ "	</a:lnB>                                                                         "
				+ "	<a:lnTlToBr w=\"12700\" cmpd=\"sng\">                                            "
				+ "		<a:noFill />                                                                 "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "	</a:lnTlToBr>                                                                    "
				+ "	<a:lnBlToTr w=\"12700\" cmpd=\"sng\">                                            "
				+ "		<a:noFill />                                                                 "
				+ "		<a:prstDash val=\"solid\" />                                                 "
				+ "	</a:lnBlToTr>                                                                    "
				+ "	<a:noFill />                                                                     "
				+ "</a:tcPr>                                                                          " + "</a:tc>";

		return ((CTTableCell) XmlUtils.unmarshalString( contents, org.docx4j.jaxb.Context.jc, CTTableCell.class ));
	}
}
