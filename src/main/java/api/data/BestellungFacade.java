/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.data;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import java.text.SimpleDateFormat;
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
    BestellungBean bestellungBean;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
    
    public List<BestellungDTO> findBestellungByName(String query) {
        List<Bestellung> bestellungen = bestellungBean.findByShortText(query);
        return bestellungen.stream().map((bestellung) -> {
            BestellungDTO dto = new BestellungDTO();
            dto.setShortText(bestellung.getShortText());
            dto.setLongText(bestellung.getLongText());
            dto.setGetraenk(bestellung.getGetraenkEnum().toString());
            dto.setStatus(bestellung.getStatus().toString());
            dto.setDueDate(sdf.format(bestellung.getDueDate()));
            dto.setDueTime(sdfTime.format(bestellung.getDueTime()));                 
            return dto;
        }).collect(Collectors.toList());
    }
}
