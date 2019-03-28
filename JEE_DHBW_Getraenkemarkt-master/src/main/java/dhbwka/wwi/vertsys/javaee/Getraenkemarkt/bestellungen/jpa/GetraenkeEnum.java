/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa;

/**
 *
 * @author Philip Mayer
 */
public enum GetraenkeEnum {
    Bier, Schnaps, Cola, Wasser;

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
            default:
                return this.toString();
        }
    }

}
