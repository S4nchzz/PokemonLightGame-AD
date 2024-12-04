package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.dto.LicenseDTO;
import org.lightPoke.db.entities.Entity_License;

public interface LicenseDAO_IFACE {
    Entity_License createLicense();
    Entity_License getLicenseByUsername(String username);

    Entity_License getLicenseByTrainerId(int trainerId);
}
