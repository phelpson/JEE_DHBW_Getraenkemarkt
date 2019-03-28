/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.dashboard.ejb;

import java.util.List;
import javax.ejb.Local;

/**
 * Lokales Interface für EJBs, die Dashboard-Kacheln zur Verfügung stellen.
 */
@Local
public interface DashboardContentProvider {
    void createDashboardContent(List<DashboardSection> sections);
}
