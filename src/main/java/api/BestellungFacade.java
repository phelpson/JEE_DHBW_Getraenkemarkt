/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Philip Mayer
 */
@Stateless
public class BestellungFacade {
    @EJB
    private BestellungBean bestellungBean;

    public List<BestellungDTO> getAll(String query){
        List<Bestellung> bestellungen = this.bestellungBean.findByUsername(query);
        return bestellungen.stream().map((bestellung) -> {
                BestellungDTO bestellungDTO = new BestellungDTO(bestellung);
                
                return bestellungDTO;
            }).collect(Collectors.toList());
    }
}
