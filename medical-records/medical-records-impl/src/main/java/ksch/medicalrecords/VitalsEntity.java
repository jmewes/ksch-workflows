package ksch.medicalrecords;

import ksch.patientmanagement.visit.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VitalsEntity implements Vitals {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(unique = true)
    private UUID id;

    @NonNull
    @Column(nullable = false)
    private UUID visitId;

    @Column
    private Integer systolicInMmHg;

    @Column
    private Integer diastolicInMmHg;

    @Column
    private Float temperatureInF;

    @Column
    private Integer pulseInBPM;

    @Column
    private Integer weightInKG;

    public VitalsEntity(Visit visit) {
        this.visitId = visit.getId();
    }

    public static VitalsEntity toVitalsEntity(Vitals vitals) {
        return VitalsEntity.builder()
                .id(vitals.getId())
                .visitId(vitals.getVisitId())
                .systolicInMmHg(vitals.getSystolicInMmHg())
                .diastolicInMmHg(vitals.getDiastolicInMmHg())
                .temperatureInF(vitals.getTemperatureInF())
                .pulseInBPM(vitals.getPulseInBPM())
                .weightInKG(vitals.getWeightInKG())
                .build();
    }
}
