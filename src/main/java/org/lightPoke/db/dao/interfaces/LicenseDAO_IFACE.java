package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.dto.LicenseDTO;
import org.lightPoke.db.entities.Entity_License;

public interface LicenseDAO_IFACE {
    Entity_License createLicense();
    LicenseDTO entityToDto(Entity_License entity);
    Entity_License getLicenseByUsername(String username);
}
