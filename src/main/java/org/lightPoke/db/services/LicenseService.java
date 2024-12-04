package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.LicenseDAO_IMPLE;
import org.lightPoke.db.dto.LicenseDTO;
import org.lightPoke.db.entities.Entity_License;

public class LicenseService {
    private static LicenseService instance;
    private final LicenseDAO_IMPLE licenseDao;

    private LicenseService() {
        this.licenseDao = LicenseDAO_IMPLE.getInstance();
    }

    public static LicenseService getInstance() {
        if (instance == null) {
            instance = new LicenseService();
        }

        return instance;
    }

    private LicenseDTO entityToDto(final Entity_License entity) {
        return new LicenseDTO(entity.id(), entity.expedition_date(), entity.points(), entity.n_victories());
    }

    public LicenseDTO createLicense() {
        Entity_License entity_license = licenseDao.createLicense();
        return entityToDto(entity_license);
    }

    public LicenseDTO getLicenseByTrainerId(int trainerId) {
        return entityToDto(licenseDao.getLicenseByTrainerId(trainerId));
    }
}
