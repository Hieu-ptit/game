package com.mc.model.dto;

import com.domain.embedded.GolfCourseObject;
import com.domain.embedded.PlayerObject;
import com.model.bo.RoomStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoomDto {

    private String name;

    private Integer golfCourseId;

    private RoomStatus status;

    private GolfCourseObject golfCourseObject;

    private List<PlayerObject> playerObjects;
}
