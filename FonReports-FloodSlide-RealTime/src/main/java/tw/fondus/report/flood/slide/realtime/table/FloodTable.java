package tw.fondus.report.flood.slide.realtime.table;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTTable;
import org.docx4j.dml.CTTableCell;
import org.docx4j.dml.CTTableCol;
import org.docx4j.dml.CTTableGrid;
import org.docx4j.dml.CTTableRow;
import org.pptx4j.pml.CTGraphicalObjectFrame;

import strman.Strman;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.report.commons.slide.ITable;

/**
 * Create table for real time flood slide.
 * 
 * @author Chao
 *
 */
public class FloodTable implements ITable {
	private org.docx4j.dml.ObjectFactory dmlFactory;
	private org.pptx4j.pml.ObjectFactory pmlFactory;
	private CTTable ctTable;
	private CTGraphicalObjectFrame graphicFrame;

	public FloodTable(long extcX, long extcY, long offX, long offY) {
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
			ctTableGrid.getGridCol().add( gridCol );
			ctTableGrid.getGridCol().add( gridCol );
			ctTableGrid.getGridCol().add( gridCol );
			ctTableGrid.getGridCol().add( gridCol );
			gridCol.setW( 1016000 );

			CTTableRow titleRow = dmlFactory.createCTTableRow();
			titleRow.setH( 152400 );

			titleRow.getTc().add( createTitleCell( "主要區域" ) );
			titleRow.getTc().add( createTitleCell( Strman.append( "淹水面積", StringUtils.BREAKLINE, "(公頃)" ) ) );
			titleRow.getTc().add( createTitleCell( Strman.append( "平均淹水", StringUtils.BREAKLINE, "深度(m)" ) ) );
			titleRow.getTc().add( createTitleCell( Strman.append( "淹水面積", StringUtils.BREAKLINE, "比例(%)" ) ) );
			titleRow.getTc().add( createTitleCell( Strman.append( "淹水調查", StringUtils.BREAKLINE, "優先關注地區" ) ) );
			ctTable.getTr().add( titleRow );

			contents.forEach( content -> {
				try {
					CTTableRow contentRow = dmlFactory.createCTTableRow();
					contentRow.setH( 152400 );
					contentRow.getTc().add( createTableCell( content[0] ) );
					contentRow.getTc().add( createTableCell( content[1] ) );
					contentRow.getTc().add( createTableCell( content[2] ) );
					contentRow.getTc().add( createTableCell( content[3] ) );
					contentRow.getTc().add( createTableCell( content[4] ) );
					ctTable.getTr().add( contentRow );
				} catch (Exception e) {
					e.printStackTrace();
				}
			} );
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public CTGraphicalObjectFrame getTable() {
		return graphicFrame;
	}

	private CTTableCell createTitleCell( String cellText ) throws JAXBException {
		String contents = "<a:tc xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> "
				+ "	<a:txBody>                                                    "
				+ "		<a:bodyPr anchor=\"t\" rtlCol=\"false\" />                "
				+ "		<a:lstStyle />                                            "
				+ "		<a:p>                                                     "
				+ "			<a:pPr algn=\"ctr\" />                                "
				+ "			<a:r>                                                 "
				+ "				<a:rPr lang=\"en-US\" b=\"true\" sz=\"1000\">     "
				+ "					<a:solidFill>                                 "
				+ "						<a:srgbClr val=\"000000\" />              "
				+ "					</a:solidFill>                                "
				+ "				</a:rPr>                                          " 
				+ "				<a:t>" + cellText + "</a:t>                       " 
				+ "			</a:r>                                                "
				+ "			<a:endParaRPr lang=\"en-US\" sz=\"1100\" />           "
				+ "		</a:p>                                                    "
				+ "	</a:txBody>                                                   "
				+ "	<a:tcPr>                                                      "
				+ "		<a:lnL>                                                   "
				+ "			<a:noFill />                                          "
				+ "		</a:lnL>                                                  "
				+ "		<a:lnR>                                                   "
				+ "			<a:noFill />                                          "
				+ "		</a:lnR>                                                  "
				+ "		<a:lnT>                                                   "
				+ "			<a:noFill />                                          "
				+ "		</a:lnT>                                                  "
				+ "		<a:lnB w=\"12700\" cmpd=\"sng\" algn=\"ctr\" cap=\"flat\">"
				+ "			<a:solidFill>                                         "
				+ "				<a:srgbClr val=\"000000\" />                      "
				+ "			</a:solidFill>                                        "
				+ "			<a:prstDash val=\"solid\" />                          "
				+ "			<a:round />                                           "
				+ "			<a:headEnd type=\"none\" w=\"med\" len=\"med\" />     "
				+ "			<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />     "
				+ "		</a:lnB>                                                  "
				+ "	</a:tcPr>                                                     "
				+ "</a:tc>                                                         ";

		return ((CTTableCell) XmlUtils.unmarshalString( contents, org.docx4j.jaxb.Context.jc, CTTableCell.class ));
	}
	
	private CTTableCell createTableCell( String cellText ) throws JAXBException {
		String contents=
				"<a:tc xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> "
						 +"	<a:txBody>                                                "
						 +"		<a:bodyPr anchor=\"t\" rtlCol=\"false\" />            "
						 +"		<a:lstStyle />                                        "
						 +"		<a:p>                                                 "
						 +"			<a:pPr algn=\"ctr\" />                            "
						 +"			<a:r>                                             "
						 +"				<a:rPr lang=\"en-US\" b=\"false\" sz=\"1000\">"
						 +"					<a:solidFill>                             "
						 +"						<a:srgbClr val=\"000000\" />          "
						 +"					</a:solidFill>                            "
						 +"				</a:rPr>                                      "
						 +"				<a:t>" + cellText + "</a:t>                   "
						 +"			</a:r>                                            "
						 +"			<a:endParaRPr lang=\"en-US\" sz=\"1100\" />       "
						 +"		</a:p>                                                "
						 +"	</a:txBody>                                               "
						 +"	<a:tcPr>                                                  "
						 +"		<a:lnL>                                               "
						 +"			<a:noFill />                                      "
						 +"		</a:lnL>                                              "
						 +"		<a:lnR>                                               "
						 +"			<a:noFill />                                      "
						 +"		</a:lnR>                                              "
						 +"		<a:lnT>                                               "
						 +"			<a:noFill />                                      "
						 +"		</a:lnT>                                              "
						 +"		<a:lnB>                                               "
						 +"			<a:noFill />                                      "
						 +"		</a:lnB>                                              "
						 +"	</a:tcPr>                                                 "
						 +"</a:tc>                                                    ";
		
		return ((CTTableCell) XmlUtils.unmarshalString( contents, org.docx4j.jaxb.Context.jc, CTTableCell.class ));
	}
}

