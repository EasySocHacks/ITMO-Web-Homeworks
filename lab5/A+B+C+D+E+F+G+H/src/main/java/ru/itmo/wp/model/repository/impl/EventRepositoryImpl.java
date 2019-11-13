package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EventRepositoryImpl extends BasicRepositoryImpl implements EventRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Event event) {
        ResultSet resultSet = saveObject("INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())",
                event.getUserId(),
                event.getType().name());

        try {
            event.setId(resultSet.getLong(1));
            event.setCreationTime(find(event.getUserId()).getCreationTime());
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Event.", e);
        }
    }

    @Override
    public Class<?> getRepositoryClass() {
        return Event.class;
    }

    private Event find(long userId) {
        return (Event) findObject("userId", userId);
    }

    private Event toEvent(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return (Event) toObject(metaData, resultSet);
    }
}