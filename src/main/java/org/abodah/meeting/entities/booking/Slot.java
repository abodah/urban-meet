package org.abodah.meeting.entities.booking;

import org.abodah.meeting.entities.Base;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MT_SLT")
@SequenceGenerator(name = "SeqMtSlot", sequenceName = "SeqMtSlot", initialValue = 1, allocationSize = 1)
public class Slot extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqMtSlot")
    private Long id;

    private Date startTime;
    private Date endTime;
}
