package dev.alex.klepov.persistence;

import java.util.Collection;

import dev.alex.klepov.model.FreeNumberModel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class FreeNumberDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FreeNumberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Collection<FreeNumberModel> findAll() {

        String sql = "" +
                "select * " +
                "from free_numbers.free_number";

        return namedParameterJdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new FreeNumberModel(
                        resultSet.getLong("id"),
                        resultSet.getString("number"),
                        resultSet.getLong("country_code"),
                        resultSet.getTimestamp("updated_at").toLocalDateTime(),
                        resultSet.getString("data_humans"),
                        resultSet.getString("full_number"),
                        resultSet.getString("country_text"),
                        resultSet.getTimestamp("max_date").toLocalDateTime(),
                        resultSet.getString("status")
                )
        );
    }

    public void saveBatch(Collection<FreeNumberModel> freeNumbers) {
        // no need to waste time on query
        if (freeNumbers.size() == 0) {
            return;
        }

        String sql = "" +
                "insert into free_numbers.free_number " +
                "(number, country_code, updated_at, data_humans, full_number, country_text, max_date, status) " +
                "values (:number, :country_code, :updated_at, :data_humans, :full_number, :country_text, :max_date, " +
                ":status)";

        MapSqlParameterSource[] params = freeNumbers.stream()
                .map(freeNumber -> new MapSqlParameterSource()
                        .addValue("number", freeNumber.getNumber())
                        .addValue("country_code", freeNumber.getCountryCode())
                        .addValue("updated_at", freeNumber.getUpdatedAt())
                        .addValue("data_humans", freeNumber.getDataHumans())
                        .addValue("full_number", freeNumber.getFullNumber())
                        .addValue("country_text", freeNumber.getCountryText())
                        .addValue("max_date", freeNumber.getMaxdate())
                        .addValue("status", freeNumber.getStatus())
                )
                .toList().toArray(new MapSqlParameterSource[freeNumbers.size()]);

        namedParameterJdbcTemplate.batchUpdate(
                sql,
                params
        );
    }

    public void deleteBatch(Collection<Long> ids) {
        if (ids.size() == 0) { // no need to waste time on query
            return;
        }

        String sql = "" +
                "DELETE " +
                "FROM free_numbers.free_number " +
                "WHERE id = :id ";

        MapSqlParameterSource[] params = ids.stream()
                .map(id -> new MapSqlParameterSource().addValue("id", id))
                .toList()
                .toArray(new MapSqlParameterSource[ids.size()]);

        namedParameterJdbcTemplate.batchUpdate(
                sql,
                params
        );
    }
}
