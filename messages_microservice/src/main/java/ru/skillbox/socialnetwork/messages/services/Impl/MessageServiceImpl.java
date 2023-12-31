package ru.skillbox.socialnetwork.messages.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.messages.dto.*;
import ru.skillbox.socialnetwork.messages.exception.exceptions.DialogNotFoundException;
import ru.skillbox.socialnetwork.messages.models.DialogModel;
import ru.skillbox.socialnetwork.messages.models.MessageModel;
import ru.skillbox.socialnetwork.messages.repository.DialogRepository;
import ru.skillbox.socialnetwork.messages.repository.MessageRepository;
import ru.skillbox.socialnetwork.messages.services.MessageService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Artem Lebedev | 18/09/2023 - 00:14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final ObjectMapper objectMapper;

    private static Long userId;

    /**
     * Потом создаем сообщения к конкретному диалогу.
     * Инкрементим unreadCount
     */
    @Override
    public Object createMessage(MessageDto messageDto) {

        MessageModel mm = modelMapper.map(messageDto, MessageModel.class);
        Optional<DialogModel> dm = Optional.ofNullable(
                dialogRepository
                        .findById(mm.getDialogId())
                        .orElseThrow(() -> new DialogNotFoundException("Dialog with id " + mm.getDialogId() + " not found")));
        if (dm.isPresent()) {
            dm.get().setLastMessage(mm);
            dm.get().setUnreadCount(dm.get().getUnreadCount() + 1);
        }

        messageRepository.save(mm);
        return new ResponseEntity<>(modelMapper.map(mm, MessageDto.class), HttpStatus.OK);
    }

    @Override
    @Transactional
    public Object changeMessageStatus(UUID dialogId) {
        Optional<List<MessageModel>> mmList =
                Optional.ofNullable(messageRepository
                        .findByDialogId(dialogId)
                        .orElseThrow(() -> new DialogNotFoundException("Dialog with id " + dialogId + " not found")));
        if (mmList.isPresent()) {
            for (MessageModel m : mmList.get()) {
                m.setStatus(EMessageStatus.READ);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<MessageShortDto> getMessagesForDialog(Long companionId, Integer offset, Integer limit) {
        DialogModel dialogId = dialogRepository
                .findByConversationAuthorAndConversationPartner(userId, companionId);
        Optional<MessageModel> messageModels = messageRepository.findById(dialogId.getId());

        return messageModels.stream()
                .map(messageModel -> objectMapper.convertValue(messageModel, MessageShortDto.class))
                .collect(Collectors.toList());
    }

    public void setUserId(Long userId) {
        MessageServiceImpl.userId = userId;
    }
}
