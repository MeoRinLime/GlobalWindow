package com.zxq.globalwindow.mapper;

import com.zxq.globalwindow.pojo.keyword;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface keywordMapper {

    @Select("select * from keyword where keyword = #{keyword}")
    public keyword findBykeyword(String keyword);

    @Select("select * from keyword where keyword like CONCAT('%', #{keyword}, '%') limit #{num}")
    public List<keyword> findAllBykeyword(String keyword, Integer num);

    /*@Select("select frequency from keyword where keyword = #{keyword}")
    public keyword getkeywordBykeyword(String keyword);*/

    @Select("SELECT * from keyword order by frequency desc limit #{num}")
    public List<keyword> findByfrequency(Integer num);

    @Insert("insert ignore into keyword(keyword) values (#{keyword})")
    public boolean setkeyword(String keyword);

    @Select("select * from keyword")
    public List<keyword> findAllkeyword();

    @Update("update keyword set frequency = #{frequency} where keyword = #{keyword}")
    public boolean updatekeyword_frequency(Integer frequency, String keyword);
}
