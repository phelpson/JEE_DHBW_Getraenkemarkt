/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;

/**
 *
 * @author Philip Mayer
 */
class KundeDTO {
    
    private String companyname;
    
    public KundeDTO(Kunde kunde) {
        this.companyname = kunde.getName();
    }
    
    public String getCompanyname() {
        return companyname;
    }
}
