package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_License;
import org.lightPoke.db.repo.Repo_License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Svice_License {
    private static Svice_License instance;

    @Autowired
    private final Repo_License repoLicense;

    private Svice_License(Repo_License repoLicense) {
        this.repoLicense = repoLicense;
    }

    public Ent_License createLicense() {
        LocalDate localdate = LocalDate.now();
        final String currentDate = localdate.getDayOfMonth() + "/" + localdate.getMonthValue() + "/" + localdate.getYear();

        return repoLicense.save(new Ent_License(currentDate, 0.0f, 0));
    }

    public Ent_License getLicenseByTrainerId(int trainerId) {
        return repoLicense.getLicenseByTrainerId(trainerId);
    }
}
