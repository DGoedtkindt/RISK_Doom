package mapmaker;

/**
 * Contains infos for a MapState
 */
public interface Visitable {
    //for Visitor Pattern
    void accept(MakeXML makeXML);
    
}
