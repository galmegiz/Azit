package com.sun.Azit.entity;


import com.sun.Azit.constant.PaymentStatus;
import com.sun.Azit.constant.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="event_member")
@Getter
@NoArgsConstructor
@ToString
public class EventMember extends BaseEntity {
    @Id @Column(name="event_member_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter @Column(nullable = false) @Enumerated(EnumType.STRING) private Role memberRole;

    @Setter @Column(nullable = false) @Enumerated(EnumType.STRING) private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public EventMember(Role memberRole, PaymentStatus paymentStatus, Event event, Member member){
        this.memberRole = memberRole;
        this.paymentStatus = paymentStatus;
        this.event = event;
        this.member = member;
    }
}
