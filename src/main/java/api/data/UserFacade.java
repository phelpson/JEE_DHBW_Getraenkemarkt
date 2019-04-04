/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.data;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
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
public class UserFacade {

    
    @EJB
    UserBean userBean;
    
//    Simple Date Format für das Mappen des Datums ins DTO als String
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
    
//    Find by Username Methode, die vom UserBean die Daten ausliest und diese in ein Data-Transfer-Object schreibt
    public List<UserDTO> findByUsername(String query) {
        List<User> users = userBean.findByUsername(query);
        
        return users.stream().map((user) -> {
            UserDTO dto = new UserDTO();
            dto.setUsername(user.getUsername());
            dto.setVorname(user.getVorname());
            dto.setNachname(user.getNachname());
            dto.setAdresse(user.getAdresse());
            dto.setPlz(user.getPlz());
            dto.setArt(user.getDisAttribut());
            
//            Bestellungen sind im User als ArrayList gemappt und werden mit einer seperaten Methode verarbeitet
            dto.setBestellungen(this.mapBestellungList(user.getbestellungen()));
            dto.setCountGesamtBestellungen(user.getbestellungen().size());
            return dto;
        }).collect(Collectors.toList());
    }
    
    
//    Methode für das Mappen von der Bestellung List Elementen in das BestellungsDTO für den JSON Output
//    Loop durch die einzelnen Bestellungselemente, die dann eine gemappte Liste zurückgibt.
    public List<BestellungDTO> mapBestellungList(List<Bestellung> toMapBestellungen) {
        List <Bestellung> bestellungen = toMapBestellungen;
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
