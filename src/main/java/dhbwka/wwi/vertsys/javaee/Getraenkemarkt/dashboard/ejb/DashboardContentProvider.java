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
