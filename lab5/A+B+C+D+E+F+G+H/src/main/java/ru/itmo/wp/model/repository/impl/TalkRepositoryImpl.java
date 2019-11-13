package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl implements TalkRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Talk talk) {
        ResultSet resultSet = saveObject("INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())",
                talk.getSourceUserId(),
                talk.getTargetUserId(),
                talk.getText());

        try {
            talk.setId(resultSet.getLong(1));
            talk.setCreationTime(find(talk.getId()).getCreationTime());
        } catch (SQLException e) {
             throw new RepositoryException("Can't save Talk.", e);
        }
    }

    @Override
    public Class<?> getRepositoryClass() {
        return Talk.class;
    }

    private Talk find(long id) {
        return (Talk) findObject(id);
    }

    @Override
    public List<Talk> findAll(long id) {
        List<Object> objectsArray = findAllObjects("SELECT * FROM `Talk` WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime", id, id);
        List<Talk> talksArray = new ArrayList<>();
        for (Object obj : objectsArray) {
            talksArray.add((Talk) obj);
        }

        return  talksArray;
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return (Talk) toObject(metaData, resultSet);
    }
}
