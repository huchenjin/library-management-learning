package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.library.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT id, username, password_hash, display_name, role, status FROM sys_user WHERE username = #{username} LIMIT 1")
    SysUser selectByUsername(@Param("username") String username);
}
