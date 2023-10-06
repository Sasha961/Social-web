package com.example.demo.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum StatusCodeType {
 FRIEND, REQUEST_TO, REQUEST_FROM, BLOCKED, DECLINED, SUBSCRIBED, NONE, WATCHING, REJECTING, RECOMMENDATION

}
