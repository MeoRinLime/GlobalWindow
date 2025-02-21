package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface commentMapper {
    @Select("select * from comment where commentId = {#commentId}")
    public comment findBycommentId(Integer commentId);

    @Select("select * from comment where newsId = #{newsId}")
    public List<comment>  findBynewsId(Integer newsId);

    @Select("select * from comment where newsId = #{newsId} order by likeNum limit #{num}")
    public List<comment>  findHotBynewsId(Integer newsId,Integer num);

    @Insert("insert into comment values (#{comment},#{attitude},#{time},#{source},#{newsId},#{emotion},#{likeNum})")
    public boolean setcomment(String comment, Integer attitude, LocalDateTime time, String source, Integer newsId, String emotion, Integer likeNum);
}
