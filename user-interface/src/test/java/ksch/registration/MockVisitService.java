package ksch.registration;

import ksch.patientmanagement.patient.Patient;
import ksch.patientmanagement.visit.Visit;
import ksch.patientmanagement.visit.VisitService;
import ksch.patientmanagement.visit.VisitType;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class MockVisitService {

    @Bean
    public VisitService visitService() {
        return Mockito.spy(new MockVisitServiceImpl());
    }

    class MockVisitServiceImpl implements VisitService {

        private boolean isActive = false;

        @Override
        public boolean isActive(Patient patient) {
            return isActive;
        }

        @Override
        public Optional<Visit> getActiveVisit(Patient patient) {
            return Optional.empty();
        }

        @Override
        public Visit startVisit(Patient patient, VisitType visitType) {
            isActive = true;
            return new NullVisit();
        }

        @Override
        public Visit discharge(Patient patient) {
            isActive = false;
            return new NullVisit();
        }

        @Override
        public Patient getPatient(UUID visitId) {
            return null;
        }
    }

    class NullVisit implements Visit {

        @Override
        public UUID getId() {
            return null;
        }

        @Override
        public Patient getPatient() {
            return null;
        }

        @Override
        public VisitType getType() {
            return null;
        }

        @Override
        public LocalDateTime getTimeStart() {
            return null;
        }

        @Override
        public LocalDateTime getTimeEnd() {
            return null;
        }
    }
}
