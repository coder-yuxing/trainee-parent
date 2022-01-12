package com.yuxing.trainee.detection.domain.valuaobject;

import lombok.Getter;

/**
 * 检测对象类型
 *
 * @author yuxing
 */
@Getter
public enum DetectionItemType {
    /**
     * 承重墙
     */
    BEARING_WALL,
    /**
     * 柱子
     */
    PILLAR,
    /**
     * 下水主管道
     */
    MAIN_DRAIN,
    /**
     * 烟道
     */
    AIR_FLUE,

    /**
     * 房间
     */
    ROOM,

    /**
     * 门
     */
    DOOR,
    /**
     * 防盗门
     */
    SECURITY_DOOR,
    /**
     * 窗
     */
    WINDOW,
    /**
     * 空调孔
     */
    AIR_LOUVER,
    /**
     * 排烟孔
     */
    SMOKE_LOUVER,
    /**
     * 房间名称
     */
    ROOM_NAME,
    /**
     * 墙
     */
    WALL,
    /**
     * 顶
     */
    ROOF,
    /**
     * 地
     */
    GROUND,
    /**
     * 水点位
     */
    WATER,
    /**
     * 其他
     */
    OTHER,
    /**
     * 开关
     */
    SWITCHS,
    /**
     * 插座
     */
    SOCKET,
    /**
     * 灯
     */
    LAMP,
    /**
     * 配电箱
     */
    ELECTRIC_BOX,
    /**
     * 燃气主管道
     */
    GAS_MAIN_PIPE,

    /**
     * 柜子
     */
    CABINET,

    ;

}
