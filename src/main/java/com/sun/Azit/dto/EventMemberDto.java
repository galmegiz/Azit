package com.sun.Azit.dto;

import com.sun.Azit.constant.PaymentStatus;
import com.sun.Azit.constant.Role;
import com.sun.Azit.entity.Event;
import com.sun.Azit.entity.EventMember;
import com.sun.Azit.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
public class EventMemberDto {

    private Member member;
    private Role memberRole;
    @Enumerated(EnumType.STRING) private PaymentStatus paymentStatus;


    private static ModelMapper modelMapper = new ModelMapper();



    public static EventMemberDto from(EventMember eventMember){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(eventMember, EventMemberDto.class);
    }

}
