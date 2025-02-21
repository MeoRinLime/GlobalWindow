package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.timeLink;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface timeLinkMapper {
    @Select("select * from timelink")
    public Set<timeLink> findAlltimeLink();

    @Insert("insert into timelink values (#{timeLink})")
    public boolean setTimeLink(String timeLink);
}
