package ru.skillbox.group39.socialnetwork.notifications.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.socialnetwork.notifications.client.FriendsClient;
import ru.skillbox.group39.socialnetwork.notifications.client.HealthChecker;
import ru.skillbox.group39.socialnetwork.notifications.client.dto.AccountDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.NotificationSimpleDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.NotificationStampedDto;
import ru.skillbox.group39.socialnetwork.notifications.utils.ObjectMapperCustom;
import ru.skillbox.group39.socialnetwork.notifications.dto.count.Count;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.common.NotificationCommonDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.event.EventNotificationDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.count.NotificationsCountDto;
import ru.skillbox.group39.socialnetwork.notifications.exception.ErrorResponse;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.AuthorModel;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationCommonModel;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationSimpleModel;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationStampedModel;
import ru.skillbox.group39.socialnetwork.notifications.repositories.AuthorRepository;
import ru.skillbox.group39.socialnetwork.notifications.repositories.NotificationCommonRepository;
import ru.skillbox.group39.socialnetwork.notifications.repositories.NotificationStampedRepository;
import ru.skillbox.group39.socialnetwork.notifications.service.NotificationService;
import ru.skillbox.group39.socialnetwork.notifications.client.UsersClient;
import ru.skillbox.group39.socialnetwork.notifications.utils.ObjectMapperUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static ru.skillbox.group39.socialnetwork.notifications.security.service.UserDetailsServiceImpl.getPrincipalId;

/**
 * @author Artem Lebedev | 07/09/2023 - 08:26
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${friends-service.host}")
    private String friendsHost;
    @Value("${friends-service.port}")
    private String friendsPort;

    private final NotificationCommonRepository notificationCommonRepository;
    private final NotificationStampedRepository notificationStampedRepository;
    private final AuthorRepository authorRepository;
    private final UsersClient usersClient;
    private final FriendsClient friendsClient;
    private final ModelMapper modelMapper;

    @Override
    public Object getCount() {
        Long cnt = notificationStampedRepository.countByData_ConsumerId(getPrincipalId());
        Count count = new Count(cnt);
        return new NotificationsCountDto(LocalDateTime.now(), count);
    }

    @Override
    @Transactional
    public void processCommonNotification(@NotNull NotificationCommonDto notificationCommonDto) {
//		Обработка в зависимости от статуса уведомления
        switch (notificationCommonDto.getNotificationType()) {
//			сохраняем
            case LIKE:
            case POST_COMMENT:
                repeatedEvent(notificationCommonDto);
            case POST:
//				создаем уведомления для каждого
                Optional<AccountDto> accountDto =
                        Optional.ofNullable(
                                usersClient.getUserDetailsById(notificationCommonDto.getProducerId()));
                if (accountDto.isPresent()) {
                    log.info(" do something");
                    repeatedEvent(notificationCommonDto);
                    Optional<List<Long>> friendsIdList = Optional.ofNullable(friendsClient.getFriendsListById(notificationCommonDto.getProducerId()));
                    if (friendsIdList.isPresent()) {
                        for (Long id : friendsIdList.get()) {

                            NotificationSimpleModel nsm = NotificationSimpleModel.builder()
                                    .producerId(notificationCommonDto.getProducerId())
                                    .content(notificationCommonDto.getContent())
                                    .notificationType(notificationCommonDto.getNotificationType().toString())
                                    .timestamp(notificationCommonDto.getTimestamp())
                                    .consumerId(id)
                                    .read(false)
//                                    .author(getAuthorModel(notificationCommonDto.getId()))
                                    .build();
                            processNativeModels(nsm);
                        }
                    }
                }
    }

}

    @Transactional
    private void repeatedEvent(NotificationCommonDto notificationCommonDto) {
        NotificationCommonModel notificationCommonModel = ObjectMapperUtils.map(notificationCommonDto, NotificationCommonModel.class);
        //del
        log.info(notificationCommonDto.toString());
        log.info(" * NotificationCommonDto mapped to NotificationCommonModel entity. {}", notificationCommonModel);

        notificationCommonRepository.save(notificationCommonModel);
        log.info(" * NotificationCommonModel saved to DB/notifications/notifications_common. '{}'", notificationCommonModel);

        NotificationSimpleModel notificationSimpleModel = getNotificationSimpleModel(notificationCommonDto);

        //del
        NotificationSimpleDto dto = modelMapper.map(notificationSimpleModel, NotificationSimpleDto.class);
        log.info(dto.toString());
        notificationSimpleModel.setAuthor(getAuthorModel(notificationCommonDto.getProducerId()));
        processNativeModels(notificationSimpleModel);

    }

    @Transactional
    public void processNativeModels(@NotNull NotificationSimpleModel notificationSimpleModel) {
        log.info(" * Wrap NotificationSimpleModel to NotificationStampedModel");
        NotificationStampedModel notificationStampedModel = new NotificationStampedModel(notificationSimpleModel);

        saveStampedModel(notificationStampedModel);
    }

    private static NotificationSimpleModel getNotificationSimpleModel(@NotNull NotificationCommonDto notificationCommonDto) {
        log.info(" * NotificationCommonDto mapped to NotificationSimpleModel entity");
        return ObjectMapperUtils.map(notificationCommonDto, NotificationSimpleModel.class);
    }

    /**
     * @param id id of any person's object: AuthorId, UserId, ProducerId, ConsumerId
     * @return AuthorModel: firstName, lastName, photo;
     */

    private AuthorModel getAuthorModel(Long id) {
        log.info(" * Trying get AccountDTO from users microservice via FeignClient");
        HealthChecker.checkHealthyService(friendsHost, Integer.valueOf(friendsPort));
        AccountDto accountDto = usersClient.getUserDetailsById(id);
        return authorRepository.findById(
                accountDto.getId()).orElse(AuthorModel.builder()
                .id(accountDto.getId())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .photo(accountDto.getPhoto())
                .build());
    }

    private void saveStampedModel(NotificationStampedModel notificationStampedModel) {
        //del
        NotificationStampedDto dto = modelMapper.map(notificationStampedModel, NotificationStampedDto.class);
        log.info(dto.toString());
        try {
            notificationStampedRepository.save(notificationStampedModel);
            log.info(" * AuthorModel persist into DB/notifications/author_of_notification. '{}'", notificationStampedModel.getData().getAuthor());
            log.info(" * NotificationSimpleModel saved into DB/notifications/notifications_simple. '{}'", notificationStampedModel.getData());
            log.info(" * NotificationStampedModel saved into DB/notifications/notifications_stamped. '{}'", notificationStampedModel);
        } catch (RuntimeException e) {
            log.error(" ! Exception during persisting notification models. {}", e.getMessage());
        }
    }

    /**
     * @Example <p>
     * Right response to FE
     * @Code <p>
     * [<p>
     * {<p>
     * timeStamp": "2023-09-13T20:32:02.596+00:00",<p>
     * "data": {<p>
     * "author": {<p>
     * "firstName": "Artem",<p>
     * "lastName": "Lebedev",<p>
     * "photo": ""<p>
     * },<p>
     * "id": 1,<p>
     * "notificationType": "POST",<p>
     * "timestamp": "2023-09-13T19:47:04.538+00:00",<p>
     * "content": "Пользователь 7 опубликовал пост: 'JPA vs LiquiBase. Я создал changeset c OnetoOne'"<p>
     * }<p>
     * ],<p>
     */

    @Override
    public Object getPageStampedNotifications(Pageable pageable) {
        log.info(" * service/NotificationServiceImpl/getPageNotificationStamped");
        Long consumerId = getPrincipalId();

        Page<NotificationStampedModel> page = notificationStampedRepository.findAllByData_ConsumerId(consumerId, pageable);

        notificationStampedRepository.deleteAll(page);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Override
    public Object addEvent(@NotNull EventNotificationDto eventNotificationDto) {

        if (checkEventNotificationDtoFields(eventNotificationDto)) {
            return new ResponseEntity<>(new ErrorResponse("Check all fields. Any is null", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        NotificationCommonDto notificationCommonDto = ObjectMapperCustom.mapAnyToCommonNotificationDto(eventNotificationDto);
        NotificationSimpleModel notificationSimpleModel = getNotificationSimpleModel(notificationCommonDto);

        AuthorModel authorModel = getAuthorModel(eventNotificationDto.getProducerId());
        notificationSimpleModel.setAuthor(authorModel);

        NotificationStampedModel notificationStampedModel = new NotificationStampedModel(notificationSimpleModel);

        saveStampedModel(notificationStampedModel);

        return new ResponseEntity<>(modelMapper.map(notificationSimpleModel, NotificationSimpleDto.class), HttpStatus.OK);
    }

    @NotNull
    private static Boolean checkEventNotificationDtoFields(@NotNull EventNotificationDto eventNotificationDto) {
        return Stream.of(eventNotificationDto.getProducerId(),
                eventNotificationDto.getConsumerId(),
                eventNotificationDto.getNotificationType(),
                eventNotificationDto.getContent()).anyMatch(Objects::isNull);
    }

    @Override
    public Object setAllRead() {
        Long consumerId = getPrincipalId();
        try {
            log.info(" * Attempting to mark all notifications as read for user with id '{}'", consumerId);
            List<NotificationStampedModel> listToMarkAsRead = notificationStampedRepository.findAllByData_ConsumerId(consumerId);
            notificationStampedRepository.deleteAll(listToMarkAsRead);

        } catch (RuntimeException e) {
            log.error(" ! Exception during marking notifications as read");
            return new ResponseEntity<>(new ErrorResponse("Fault during marking notifications as read", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
