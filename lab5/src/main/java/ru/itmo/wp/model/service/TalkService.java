package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void validateMessage(String text) throws ValidationException {
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text field mustn't be empty");
        }

        if (text.length() > Talk.MAX_TEXT_LENGTH) {
            throw new ValidationException("Text length must be not greater than " + Talk.MAX_TEXT_LENGTH + " characters");
        }
    }

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll(long id) {
        return  talkRepository.findAll(id);
    }
}
