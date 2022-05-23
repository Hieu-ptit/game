package com.mc.mapper;

import com.domain.Room;
import com.model.bo.RoomStatus;
import com.model.dto.RoomDto;
import com.model.request.RoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    public Room mapToEntity(RoomRequest roomRequest){
        return new Room().setGolfCourseId(roomRequest.getGolfCourseId()).setStatus(RoomStatus.WAITING)
            .setName(roomRequest.getName());
    }

    public RoomDto mapToDto(Room room){
        return new RoomDto().setGolfCourseId(room.getGolfCourseId()).setName(room.getName()).setStatus(room.getStatus())
            .setPlayerObjects(room.getPlayer()).setGolfCourseObject(room.getGolfCourseObject());
    }
}
