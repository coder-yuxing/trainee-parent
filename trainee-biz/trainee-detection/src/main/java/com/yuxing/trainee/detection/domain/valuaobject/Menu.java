package com.yuxing.trainee.detection.domain.valuaobject;

/**
 * 检测报告菜单
 *
 * @author yuxing
 */
public enum Menu {

    /**
     * 户型
     */
    HOUSE_TYPE("户型", 0, null, 1),
    /**
     * 隐蔽工程
     */
    CONCEALED_WORKS("隐蔽工程", 1, null, 3),

    /**
     * 硬装工程
     */
    HARD_FITTING("硬装工程", 2, null, 2),

    /**
     * 房间
     */
    ROOM("房间名称", 3, HOUSE_TYPE, 1),

    WALL("墙", 4, HOUSE_TYPE, 2),

    DOOR("门", 5, HOUSE_TYPE, 3),

    WINDOW("窗", 6, HOUSE_TYPE, 4),

    PILLAR("柱子", 7, HOUSE_TYPE, 5),

    AIR_DUCT("烟道", 8, HOUSE_TYPE, 6),

    ELECTRICITY("电", 9, CONCEALED_WORKS, 1),

    WATER("水", 10, CONCEALED_WORKS, 2),

    FIRE("燃", 11, CONCEALED_WORKS, 3),

    WIND_WARM("风暖", 12, CONCEALED_WORKS, 4),

    WALL_WORKS("墙面工程", 13, HARD_FITTING, 1),

    TOP_SURFACE_ENGINEERING("顶面工程", 14, HARD_FITTING, 2),

    GROUND_ENGINEERING("地面工程", 15, HARD_FITTING, 3),

    DOOR_ENGINEERING("门工程", 16, HARD_FITTING, 4),

    WINDOW_ENGINEERING("窗工程", 17, HARD_FITTING, 5),

    OTHER("其他", 18, HARD_FITTING, 6),

    MUZUO("木作", 19, null, 4),

    CABINET("柜体", 20, MUZUO, 1),

    MESA("台面", 21, MUZUO, 2),

    HARDWARE("五金", 22, MUZUO, 3),

    DECORATIVE_PANEL("装饰板件", 23, MUZUO, 4),
    ;

    Menu(String name, int code, Menu parent, int sort) {
        this.name = name;
        this.code = code;
        this.parent = parent;
        this.sort = sort;
    }

    public static Menu getByCode(int code) {
        for (Menu m : Menu.values()) {
            if (m.code == code) {
                return m;
            }
        }
        return null;
    }

    /**
     * 检测项名目
     */
    public final String name;

    /**
     * 检测项代码
     */
    public final int code;

    /**
     * 父级菜单
     */
    public final Menu parent;

    /**
     * 排序
     */
    public final int sort;

}
