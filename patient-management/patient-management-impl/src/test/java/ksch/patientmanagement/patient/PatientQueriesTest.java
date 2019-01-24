package ksch.patientmanagement.patient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;
import static ksch.patientmanagement.patient.Gender.MALE;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientQueriesTest {

    @InjectMocks
    private PatientQueriesImpl patientQueries;

    @Mock
    private PatientRepository patientRepository;

    @Test
    public void should_retrieve_patient_by_patient_number() {
        String patientNumber = "1234";
        when(patientRepository.findByPatientNumberOrName(patientNumber))
                .thenReturn(listOf(buildTestPatient(patientNumber)));

        List<Patient> patients = patientQueries.findByNameOrNumber(patientNumber);

        assertTrue("Could not find patient by patient number.", patients.size() > 0);
        verify(patientRepository).findByPatientNumberOrName(patientNumber);
    }

    @Test
    public void should_get_patient_by_id() {
        Patient p = PatientEntity.builder().id(UUID.randomUUID()).build();

        patientQueries.getById(p.getId());

        verify(patientRepository).getById(p.getId());
    }

    private PatientEntity buildTestPatient(String patientNumber) {
        return PatientEntity.builder()
                .id(UUID.randomUUID())
                .patientNumber(patientNumber)
                .name("John Doe")
                .dateOfBirth(now())
                .gender(MALE)
                .address("Kirpal Sagar")
                .build();
    }

    private List<PatientEntity> listOf(PatientEntity... patient) {
        return Arrays.asList(patient);
    }
}