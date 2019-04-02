
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb;

/**
 * Hilfsklasse zur Beschreibung einer Dashboard-Kachel.
 */
public class DashboardTile {

    private String label = "";
    private String cssClass = "";
    private String href = "";
    private String icon = "";
    private double amount = 0;
    private boolean showDecimals = false;
    
    //<editor-fold defaultstate="collapsed" desc="Konstrukturen">
    public DashboardTile() {
    }
    
    public DashboardTile(String label, String cssClass, String href, double amount) {
        this.label = label;
        this.cssClass = cssClass;
        this.href = href;
        this.amount = amount;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getCssClass() {
        return cssClass;
    }
    
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    
    public String getHref() {
        return href;
    }
    
    public void setHref(String href) {
        this.href = href;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public boolean getShowDecimals() {
        return showDecimals;
    }
    
    public void setShowDecimals(boolean showDecimals) {
        this.showDecimals = showDecimals;
    }
    //</editor-fold>
    
}
