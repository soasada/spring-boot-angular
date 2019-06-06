package com.popokis.spring_boot_angular.appointment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Appointment.AppointmentBuilder.class)
@AllArgsConstructor(staticName = "create")
public class Appointment {
}
