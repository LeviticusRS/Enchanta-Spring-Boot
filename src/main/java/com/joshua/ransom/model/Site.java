package com.joshua.ransom.model;

import lombok.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Site {
    public static Mapper MAPPER = new Mapper();

    private long id;

    private String title;

    private String voteId;

    private String url;

    private int visible = 1;

    static class Mapper implements RowMapper<Site> {

        @Override
        public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
            Site site = new Site();
            site.id = rs.getInt("id");
            site.title = rs.getString("title");
            site.voteId = rs.getString("vote_id");
            site.url = rs.getString("url");
            site.visible = rs.getInt("visible");
            return site;
        }

    }

    public static Site findBySiteId(JdbcTemplate jdbcTemplate, int id) {
        String sql = "SELECT * FROM sites WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, MAPPER);
    }

}
