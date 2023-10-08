package ru.skillbox.group39.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum StatusCodeType {
 FRIEND, REQUEST_TO, REQUEST_FROM, BLOCKED, DECLINED, SUBSCRIBED, NONE, WATCHING, REJECTING, RECOMMENDATION

}
