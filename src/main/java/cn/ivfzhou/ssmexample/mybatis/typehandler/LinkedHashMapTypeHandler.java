package cn.ivfzhou.ssmexample.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class LinkedHashMapTypeHandler extends BaseTypeHandler<LinkedHashMap<String, String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LinkedHashMap<String, String> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public LinkedHashMap<String, String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put(rs.getString("url"), rs.getString("filter"));
        while (rs.next()) {
            filterChainDefinitionMap.put(rs.getString("url"), rs.getString("filter"));
        }
        return filterChainDefinitionMap;
    }

    @Override
    public LinkedHashMap<String, String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public LinkedHashMap<String, String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
