package mapXML;

import base.Map;
import java.awt.HeadlessException;
import java.io.File;
import org.w3c.dom.Document;
import java.io.FileNotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/** Stores the XML Document representing a Map
 */
public class MapXML {
    
    private Document xml;
    private XMLReader reader = new XMLReader();
    private XMLBuilder builder = new XMLBuilder();
    
    /**  Reads a File and creates a new MapXML from it
     * @param mapFile the file containing the Map's XML
     * @throws java.io.FileNotFoundException
    */
    public MapXML(File mapFile) throws Exception {
           if(mapFile.exists() && mapFile.isFile()) xml = builder.build(mapFile);
           else throw new FileNotFoundException();
        
    }
    
    /** Creates a new MapXML from a Map object
     * @throws java.lang.Exception
    */
    public MapXML(Map map) throws Exception{
            xml = builder.build(map);
    }
    
    /** Reads the Map's XML to create a Map object.
     * @return the Map object representing this MapXML
     * @throws java.lang.Exception
     */
    public Map getMap() throws Exception {
        return reader.getMap(xml);
    
    }
    
    /** writes the content of this MapXML onto a file
    */
    public void write(String mapName, String description) {
        try{
            //rajouter la description
            xml.getDocumentElement().setAttribute("description", description);

            //créer le fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xml);
            File dir = new File("Maps");
                if(!dir.exists()) System.err.println("The Maps directory does not seem to exist." +
                        "Please make sure " + dir.getAbsolutePath() + " exists");

            //écrire le fichier
            StreamResult result = new StreamResult(new File(dir.getAbsolutePath() + "/" + mapName + ".xml"));
            transformer.transform(source, result);

            //créer l'image du thumbnail
            //entre commentaire puisque ce code part du principe que la map
            //qu'on veut sauver est la map active sur le monde.
            //je voudrais que cette classe soit correcte indépendament de
            // l'état actuel du monde.
            // il faudra probablement créer un nouveau monde à partir ce cette
            // map pour pouvoir prendre l'image de ce monde-la
            
            /*BufferedImage mapImage = ((MyWorld)getWorld()).createMapImage();
            File out = new File(dir.getAbsolutePath() + "/" + mapName + ".png");

            //écrivre l'image du thumbnail
            ImageIO.write(mapImage, "PNG", out);*/

            System.out.println("Map was succesfully saved (Except the thumbnail "
                    + "as it is still in developpement)");

        } catch(HeadlessException | TransformerException e) {
            System.err.println("Map Couldn't be saved   : " + e);

        }
    
    }
    
    /**  calculates the checksum of the document
     *  to check if the map's XML has been corrupted.
    */
    public int calculateChecksum() {
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
}
