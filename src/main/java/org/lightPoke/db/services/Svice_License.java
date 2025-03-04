package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_License;
import org.lightPoke.db.repo.Repo_License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Svice_License {
    @Autowired
    private Repo_License repoLicense;

    public Ent_License createLicense() {
        LocalDate localdate = LocalDate.now();
        final String currentDate = localdate.getDayOfMonth() + "/" + localdate.getMonthValue() + "/" + localdate.getYear();

        return repoLicense.save(new Ent_License(currentDate, 0.0f, 0));
    }

    public void save(Ent_License license) {
        repoLicense.save(license);
    }

    public void removeLicenseFromUser(int id) {
        repoLicense.deleteById(id);
    }
}
