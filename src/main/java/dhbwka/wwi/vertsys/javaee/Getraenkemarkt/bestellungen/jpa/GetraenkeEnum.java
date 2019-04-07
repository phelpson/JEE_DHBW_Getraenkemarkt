package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa;

/**
 *
 * @author Philip Mayer
 */

//Getränk Enum zur anzeige der Getränke, die der Getränkemarkt aktuell auf Lager hat
//pro Getränk wird eine Bestellung angelegt. Auf Hinzufügung von Getränke wurde bewusst verzichtet.
public enum GetraenkeEnum {
    Bier, Schnaps, Cola, Wasser, Orangensaft, Mischzeug, Radler, Apfelsaft, Traubensaft, ClubMate;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case Bier:
                return "Bier";
            case Schnaps:
                return "Schnaps";
            case Cola:
                return "Cola";
            case Wasser:
                return "Wasser";
            case Orangensaft:
                return "Orangensaft";
            case Mischzeug:
                return "Mischzeug";
            case Radler:
                return "Radler";
            case Apfelsaft:
                return "Apfelsaft";
            case Traubensaft:
                return "Traubensaft";
            case ClubMate:
                return "ClubMate";     
            default:
                return "Unbekannt";
        }
    }

}
