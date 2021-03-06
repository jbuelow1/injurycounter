package com.jbuelow.injurycounter.ui;

import com.jbuelow.injurycounter.data.entity.Injury;
import com.jbuelow.injurycounter.data.repo.InjuryRepository;
import com.jbuelow.injurycounter.event.injuryupdate.InjuryUpdateEventPublisher;
import java.util.ArrayList;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
public class UiUpdater {

  private final InjuryRepository injuryRepo;
  private final InjuryUpdateEventPublisher eventPublisher;

  private Injury lastInjury = new Injury();

  private Boolean firstInjury = true;

  public UiUpdater(InjuryRepository injuryRepo,
      InjuryUpdateEventPublisher eventPublisher) {
    this.injuryRepo = injuryRepo;
    this.eventPublisher = eventPublisher;
  }

  @Scheduled(fixedRate = 1000)
  public void checkDB() {
    ArrayList<Injury> injuries = (ArrayList<Injury>) injuryRepo.findAll();
    Collections.sort(injuries);
    Injury injury = injuries.get(injuries.size()-1);
    if (!injury.equals(lastInjury)) {
      log.debug("New injury candidate found with id {}.", injury.getId());
      setLastInjury(injury, lastInjury);
      lastInjury = injury;
    }
  }

  private void setLastInjury(Injury injury, Injury lastInjury) {
    if (injury.isHidden()) {
      log.debug("Injury #{} is hidden. Cancelling ui update.", injury.getId());
      return;
    }
    if (firstInjury) {
      eventPublisher.publish(injury);
      firstInjury = false;
    } else {
      eventPublisher.publish(injury, lastInjury);
    }
  }

}
