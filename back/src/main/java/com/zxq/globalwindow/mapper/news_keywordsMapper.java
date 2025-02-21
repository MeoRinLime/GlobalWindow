package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.keyword;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface news_keywordsMapper {
    @Select("select keyword from news_keywords where newsId = #{newsId}")
    public List<String> getkeywordBynewsId(Integer newsId);

    @Select("select news.newsId from news_keywords join news on news_keywords.newsId = news.newsId where keyword = #{keyword}")
    public List<Integer> getnewsIdBykeyword(String keyword);

    @Select("select news.newsId from news_keywords join news on news_keywords.newsId = news.newsId where keyword = #{keyword} order by news.popularity limit #{num}")
    public List<Integer> getHotnewsIdBykeyword(String keyword, Integer num);

    @Select("select news.newsId from news_keywords join news on news_keywords.newsId = news.newsId where keyword like CONCAT('%', #{keyword}, '%') order by news.popularity limit #{num}")
    public List<Integer> getRHotnewsIdBykeyword(String keyword, Integer num);

    @Insert("insert ignore into news_keywords values (#{newsId},#{keyword})")
    public boolean setnews_keywords(Integer newsId, String keyword);
}
