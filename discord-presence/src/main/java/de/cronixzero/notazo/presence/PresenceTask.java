/* 
Coded for Sapota
Made by CronixZero
Created 15.10.2021 - 15:55
 */

package de.cronixzero.notazo.presence;

import com.google.common.util.concurrent.AbstractScheduledService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PresenceTask extends AbstractScheduledService {

    private final PresenceApi presenceApi;
    private final long switchingDuration;

    public PresenceTask(PresenceApi presenceApi, long switchingDuration) {
        this.presenceApi = presenceApi;
        this.switchingDuration = switchingDuration;
    }

    @Override
    protected void runOneIteration() throws IOException {
        presenceApi.updatePresence();
    }

    @Override
    protected @NotNull Scheduler scheduler() {
        Duration initialDelay = Duration.of(0, ChronoUnit.SECONDS);
        Duration delay = Duration.of(switchingDuration, ChronoUnit.SECONDS);

        return Scheduler.newFixedDelaySchedule(initialDelay, delay);
    }
}
