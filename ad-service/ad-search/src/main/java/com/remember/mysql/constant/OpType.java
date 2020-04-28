package com.remember.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
  * @author remember
  * @date 2020/4/24 12:41
  */
public enum OpType {
    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType){
        switch (eventType){
            case DELETE_ROWS:
                return DELETE;
            case UPDATE_ROWS:
                return UPDATE;
            case WRITE_ROWS:
                return ADD;
                default:return OTHER;
        }
    }
}
