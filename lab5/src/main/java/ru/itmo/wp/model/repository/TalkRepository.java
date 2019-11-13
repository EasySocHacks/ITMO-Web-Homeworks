package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository extends  BasicRepository {
    void save(Talk talk);
    List<Talk> findAll(long id);
}
