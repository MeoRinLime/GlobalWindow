package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.event;
import com.zxq.globalwindow.pojo.news;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface eventMapper {

    @Select("select * from event")
    public List<event> findAllEvent();

    @Select("select * from event where eventId = #{eventId}")
    public event findByeventId(String eventId);

    @Select("select * from event where source = #{source} and time >= #{time} order by popularity desc limit #{num}")
    public List<event> findHotBysource(String source, Integer num, LocalDateTime time);

    @Select("select * from event where keyword like CONCAT('%', #{keyword}, '%') order by popularity desc limit #{num}")
    public List<event> findRBykeyword(String keyword,Integer num);

    @Select("select * from event where timeLink = #{timeLink}")
    public  List<event> findByTimeLink(String timeLink);

    @Select("select keyword from event where eventId = #{eventId}")
    public String getkeywordsByeventId(String eventId);

    @Select("select * from event where time >= #{time} order by popularity desc limit #{num}")
    public List<event> findRBypopularity(@Param("num") Integer num,LocalDateTime time);

    @Update("update event set timeLink = #{timeLink} where eventId = #{eventId}")
    public boolean setTimeLink(String timeLink, String eventId);

    @Insert("insert into event values (#(eventId),#{keyword},#{event},#{popularity}) on duplicate key update event = #{event}, popularity = #{popularity}")
    public boolean setevent(String eventId, String keyword, String event, Integer popularity);
}
