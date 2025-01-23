package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.LicenseDAO_IMPLE;
import org.lightPoke.db.entity.Ent_License;
import org.lightPoke.db.entity.Entity_License;
import org.lightPoke.db.repo.Repo_License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Svice_License {
    private static Svice_License instance;

    @Autowired
    private final Repo_License repoLicense;

    private Svice_License(Repo_License repoLicense) {
        this.repoLicense = repoLicense;
    }

    public Ent_License createLicense() {
        Entity_License entity_license = licenseDao.createLicense();
        return entityToDto(entity_license);
    }

    public Ent_License getLicenseByTrainerId(int trainerId) {
        return entityToDto(licenseDao.getLicenseByTrainerId(trainerId));
    }
}
