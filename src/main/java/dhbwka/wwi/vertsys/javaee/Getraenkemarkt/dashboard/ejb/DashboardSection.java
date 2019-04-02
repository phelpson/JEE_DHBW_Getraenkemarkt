package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb;

import java.util.ArrayList;
import java.util.List;

/**
 * Hilfsklasse zur Beschreibung einer Dashboard-Rubrik und ihrer Kacheln.
 */
public class DashboardSection {

    private String label = "";
    private List<DashboardTile> tiles = new ArrayList<>();
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public DashboardSection() {
    }
    
    public DashboardSection(String label, List<DashboardTile> tiles) {
        this.label = label;
        this.tiles = tiles;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public List<DashboardTile> getTiles() {
        return tiles;
    }
    
    public void setTiles(List<DashboardTile> tiles) {
        this.tiles = tiles;
    }
    //</editor-fold>

}
