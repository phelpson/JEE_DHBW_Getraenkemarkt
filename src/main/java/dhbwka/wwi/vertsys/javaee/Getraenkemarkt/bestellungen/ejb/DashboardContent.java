package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb.DashboardTile;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.BestellungStatus;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Dashboard-Kacheln für Bestellung.
 */
@Stateless(name = "bestellungen")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    private KundeBean kundeBean;

    @EJB
    private BestellungBean bestellungBean;
    
    /**
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Bestellungen
        // in allen Kategorien erzeugen
        DashboardSection section = this.createSection(null);
        sections.add(section);

        // Anschließend je Kategorie einen weiteren Abschnitt erzeugen
        List<Kunde> kunden = this.kundeBean.findAllSorted();

        for (Kunde kunde : kunden) {
            section = this.createSection(kunde);
            sections.add(section);
        }
    }

    /**
     * Hilfsmethode, die für die übergebene Bestellung-Kategorie eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Je Bestellungenstatus wird eine Kachel
     * erzeugt. Zusätzlich eine Kachel für alle Bestellungen innerhalb der
     * jeweiligen Kategorie.
     *
     * Ist die Kategorie null, bedeutet dass, dass eine Rubrik für alle Bestellungen
     * aus allen Kategorien erzeugt werden soll.
     *
     * @param kunde Bestellung-Kategorie, für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection createSection(Kunde kunde) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        if (kunde != null) {
            section.setLabel(kunde.getName());
        } else {
            section.setLabel("Alle Kunden");
            cssClass = "overview";
        }

        // Eine Kachel für alle Bestellungen in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(kunde, null, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Bestellungenstatus eine weitere Kachel erzeugen
        for (BestellungStatus status : BestellungStatus.values()) {
            String cssClass1 = cssClass + " status-" + status.toString().toLowerCase();
            String icon = "";

            switch (status) {
                case OPEN:
                    icon = "doc-text";
                    break;
                case IN_PROGRESS:
                    icon = "rocket";
                    break;
                case FINISHED:
                    icon = "ok";
                    break;
                case CANCELED:
                    icon = "cancel";
                    break;
                case POSTPONED:
                    icon = "bell-off-empty";
                    break;
            }

            tile = this.createTile(kunde, status, status.getLabel(), cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param kunde
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(Kunde kunde, BestellungStatus status, String label, String cssClass, String icon) {
        int amount = bestellungBean.search(null, kunde, status).size();
        String href = "/app/bestellungen/list/";

        if (kunde != null) {
            href = WebUtils.addQueryParameter(href, "search_kunde", "" + kunde.getId());
        }

        if (status != null) {
            href = WebUtils.addQueryParameter(href, "search_status", status.toString());
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
