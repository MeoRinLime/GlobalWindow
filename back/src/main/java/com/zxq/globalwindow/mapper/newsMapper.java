package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.news;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface newsMapper {

    @Select("select * from news where newsId = #{newsId}")
    public news findBynewsId(Integer newsId);

    @Select("SELECT * from news where source not like '%学习强国%' order by popularity desc limit #{num}")
    public List<news> findBypopularity(Integer num);

    /*@Select("select link from news where newsId = #{newsId}")
    public String getlinkBynewsId(Integer newsId);*/

    @Select("select * from news where type = #{type} and source not like '%学习强国%' order by popularity desc limit #{num}")
    public List<news> findHotBytype(String type, Integer num);

    @Select("select * from news where type = #{type} and source not like '%学习强国%'")
    public List<news> findBytype(String type);

    @Select("select * from news where source = #{source}")
    public List<news> findBysource(String source);

    @Select("select * from news where source = #{source} order by popularity desc limit #{num}")
    public List<news> findHotBysource(String source, Integer num);

    @Select("select * from news where eventId = #{eventId}")
    public List<news> findByeventId(String eventId);

    @Select("select * from news where eventId = #{eventId} order by popularity desc limit #{num}")
    public List<news> findHotByeventId(String eventId, Integer num);

    @Select("select newsId from news where link = #{link}")
    public Integer getnewsIdBylink(String link);

    @Select("select * from news where source = '学习强国' limit #{limitNum}")
    public List<news> getNewsByX(Integer limitNum);

    @Select("select picturelink from news where source = '学习强国-图' limit #{num}")
    public List<String> getNewsImages(Integer num);

    /*@Select("select news from news where newsId = #{newsId}")
    public String getnewsBynewsId(Integer newsId);*/

    /*@Select("select popularity from news where newsId = #{newsId}")
    public int getpopulairtyBynewsId(Integer newsId);*/

    /*@Select("select picturelink from news where newsId = #{newsId}")
    public String getpicturelinkBynewsId(Integer newsId);*/

    /*@Insert("insert into news(news,type,popularity,link,picturelink,source,attitude,time,emotion,eventId) values (#{news},#{type},#{popularity},#{link},#{picturelink},#{source},#{attitude},#{time},#{emotion},#{eventId})")
    public boolean setnews(String news, String type, Integer popularity, String link, String picturelink, String source, Integer attitude, String time, String emotion, String eventId);*/

    @Update("update news set eventId = #{eventId} where newsId = #{newsId}")
    public boolean setnewsEventId(String eventId,Integer newsId);

    @Update("update news set popuarity = #{popularity} where newsId = #{newsId}")
    public boolean updatenews_popularity(Integer popularity, Integer newsId);

    @Update("update news set type = #{type} where newsId = #{newsId}")
    public boolean updatenews_type(String type, Integer newsId);
}
