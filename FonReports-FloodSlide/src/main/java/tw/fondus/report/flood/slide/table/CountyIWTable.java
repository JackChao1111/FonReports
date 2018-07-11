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

/**
 * Table for county hot spot of inner water count.
 * 
 * @author Chao
 *
 */
public class CountyIWTable  implements Table{
	private org.docx4j.dml.ObjectFactory dmlFactory;
	private org.pptx4j.pml.ObjectFactory pmlFactory;
	private CTTable ctTable;
	private CTGraphicalObjectFrame graphicFrame;

	public CountyIWTable(long extcX, long extcY, long offX, long offY) {
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
			gridCol.setW( 280672 );

			CTTableCol gridCol2 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol2 );
			gridCol2.setW( 585986 );
			
			CTTableCol gridCol3 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol3 );
			gridCol3.setW( 1367301 );
			
			CTTableCol gridCol4 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol4 );
			gridCol4.setW( 1171973 );
			
			CTTableCol gridCol5 = dmlFactory.createCTTableCol();
			ctTable.setTblGrid( ctTableGrid );
			ctTableGrid.getGridCol().add( gridCol5 );
			gridCol5.setW( 2140450 );

			CTTableRow titleRow = dmlFactory.createCTTableRow();
			titleRow.setH( 353286 );
			titleRow.getTc().add( createTableCell( "項次" ) );
			titleRow.getTc().add( createTableCell( "影響區域" ) );
			titleRow.getTc().add( createTableCell( "可能致災位置" ) );
			titleRow.getTc().add( createTableCell( "致災原因" ) );
			titleRow.getTc().add( createTableCell( "預定警急對策" ) );
			ctTable.getTr().add( titleRow );

			for(int i = 0 ; i < contents.size();i++){
				try {
					CTTableRow contentRow = dmlFactory.createCTTableRow();
					contentRow.setH( 218381 );
					contentRow.getTc().add( createTableCell( String.valueOf( i+1 ) ) );
					contentRow.getTc().add( createTableCell( contents.get( i )[0] ) );
					contentRow.getTc().add( createTableCell( contents.get( i )[1] ) );
					contentRow.getTc().add( createTableCell( contents.get( i )[2] ) );
					contentRow.getTc().add( createTableCell( contents.get( i )[3] ) );
					ctTable.getTr().add( contentRow );
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public CTGraphicalObjectFrame getTable() {
		return graphicFrame;
	}

	private CTTableCell createTableCell( String cellText ) throws JAXBException {
		String contents = 
						"<a:tc xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">                    "
						+"	<a:txBody>                                                                               "
						+"		<a:bodyPr />                                                                         "
						+"		<a:lstStyle />                                                                       "
						+"		<a:p>                                                                                "
						+"			<a:pPr algn=\"ctr\" fontAlgn=\"ctr\" />                                          "
						+"			<a:r>                                                                            "
						+"				<a:rPr lang=\"zh-TW\" altLang=\"en-US\" sz=\"1200\" b=\"1\" i=\"0\"          "
						+"					u=\"none\" strike=\"noStrike\" dirty=\"0\">                              "
						+"					<a:solidFill>                                                            "
						+"						<a:srgbClr val=\"000000\" />                                         "
						+"					</a:solidFill>                                                           "
						+"					<a:effectLst />                                                          "
						+"					<a:latin typeface=\"Microsoft JhengHei\" panose=\"020B0604030504040204\" "
						+"						pitchFamily=\"34\" charset=\"-120\" />                               "
						+"					<a:ea typeface=\"Microsoft JhengHei\" panose=\"020B0604030504040204\"    "
						+"						pitchFamily=\"34\" charset=\"-120\" />                               "
						+"				</a:rPr>                                                                     "
						+"				<a:t>" + cellText + "</a:t>                                                  "
						+"			</a:r>                                                                           "
						+"		</a:p>                                                                               "
						+"	</a:txBody>                                                                              "
						+"	<a:tcPr marL=\"9525\" marR=\"9525\" marT=\"9525\" marB=\"0\"                             "
						+"		anchor=\"ctr\">                                                                      "
						+"		<a:lnL w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                           "
						+"			<a:solidFill>                                                                    "
						+"				<a:schemeClr val=\"bg1\">                                                    "
						+"					<a:lumMod val=\"50000\" />                                               "
						+"				</a:schemeClr>                                                               "
						+"			</a:solidFill>                                                                   "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"			<a:round />                                                                      "
						+"			<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"			<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"		</a:lnL>                                                                             "
						+"		<a:lnR w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                           "
						+"			<a:solidFill>                                                                    "
						+"				<a:schemeClr val=\"bg1\">                                                    "
						+"					<a:lumMod val=\"50000\" />                                               "
						+"				</a:schemeClr>                                                               "
						+"			</a:solidFill>                                                                   "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"			<a:round />                                                                      "
						+"			<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"			<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"		</a:lnR>                                                                             "
						+"		<a:lnT w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                           "
						+"			<a:solidFill>                                                                    "
						+"				<a:schemeClr val=\"bg1\">                                                    "
						+"					<a:lumMod val=\"50000\" />                                               "
						+"				</a:schemeClr>                                                               "
						+"			</a:solidFill>                                                                   "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"			<a:round />                                                                      "
						+"			<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"			<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"		</a:lnT>                                                                             "
						+"		<a:lnB w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\">                           "
						+"			<a:solidFill>                                                                    "
						+"				<a:schemeClr val=\"bg1\">                                                    "
						+"					<a:lumMod val=\"50000\" />                                               "
						+"				</a:schemeClr>                                                               "
						+"			</a:solidFill>                                                                   "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"			<a:round />                                                                      "
						+"			<a:headEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"			<a:tailEnd type=\"none\" w=\"med\" len=\"med\" />                                "
						+"		</a:lnB>                                                                             "
						+"		<a:lnTlToBr w=\"12700\" cmpd=\"sng\">                                                "
						+"			<a:noFill />                                                                     "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"		</a:lnTlToBr>                                                                        "
						+"		<a:lnBlToTr w=\"12700\" cmpd=\"sng\">                                                "
						+"			<a:noFill />                                                                     "
						+"			<a:prstDash val=\"solid\" />                                                     "
						+"		</a:lnBlToTr>                                                                        "
						+"		<a:noFill />                                                                         "
						+"	</a:tcPr>                                                                                "
						+"</a:tc>                                                                                    ";

		return ((CTTableCell) XmlUtils.unmarshalString( contents, org.docx4j.jaxb.Context.jc, CTTableCell.class ));
	}
}