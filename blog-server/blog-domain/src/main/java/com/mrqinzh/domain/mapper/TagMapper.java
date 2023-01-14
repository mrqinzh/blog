package com.mrqinzh.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.core.entity.Tag;
import com.mrqinzh.common.model.vo.PageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> page(PageVO pageVO);

    @Select("select * from tag where id = #{id}")
    Tag getById(Integer id);
}
