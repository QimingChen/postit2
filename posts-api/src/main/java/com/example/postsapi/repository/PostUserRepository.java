package com.example.postsapi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostUserRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public int save(long userId, long postId) {
    return jdbcTemplate
        .update("insert into post_user (user_id, post_id) " + "values(?,?)", userId, postId);
  }

  public Long getUserIdByPostId(Long postId) {
    String sql = "SELECT user_id FROM post_user WHERE post_id = " + postId;
    return jdbcTemplate.queryForObject(sql, Long.class);
  }

  public List<Long> findUserIdsByPostIds(List<Long> postIdList) {
      String sql = "select user_id from post_user where post_id in(:postids)";
      Map<String,Object> queryParams = new HashMap<>();
      queryParams.put("postids",postIdList);
      List<Long> userIds = namedParameterJdbcTemplate.query(sql, queryParams, new RowMapper<Long>() {
          @Override
          public Long mapRow(ResultSet resultSet, int i) throws SQLException {
              return resultSet.getLong("user_id");
          }
      });

      return userIds;
  }
}

