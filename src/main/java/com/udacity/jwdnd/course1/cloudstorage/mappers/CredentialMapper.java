package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from CREDENTIALS where userid = #{userId}")
    public List<Credential> getCredentialsByUser(int userId);

    @Select("select * from CREDENTIALS where credentialid = #{credentialId}")
    public Credential getCredentialById(int credentialId);

    @Insert("insert into CREDENTIALS(url, username, key, password, userid) " +
            "values(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public int addCredential(Credential credential);

    @Update("update CREDENTIALS set url=#{url}, username=#{username}, key=#{key}, password=#{password}" +
            " where credentialid=#{credentialId}")
    public int updateCredentials(Credential credential);

    @Delete("delete from CREDENTIALS where credentialid = #{credentialId}")
    public int deleteCredential(int credentialId);
}
