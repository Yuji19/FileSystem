package com.yuji.polygon.utils;

/**
 * @className: ConstantValue
 * @description: 常量类
 * @author: yuji
 * @create: 2020-10-31 09:51:00
 */
public class ConstantValue {

    public final static String ADD_SUCCESS = "添加成功";

    public final static String ADD_FAILURE = "添加失败";

    public final static String DELETE_SUCCESS = "删除成功";

    public final static String DELETE_FAILURE = "删除失败";

    public final static String UPDATE_SUCCESS = "更新成功";

    public final static String UPDATE_FAILURE = "更新失败";

    public final static int SUCCESS_CODE = 1000;
    //请假单一级审批：主任审批
    public final static String LEAVE_ONE_AUDIT = "主任审批";
    //请假单二级审批：部长审批
    public final static String LEAVE_TWO_AUDIT = "部长审批";

    public final static String FILE_SIGN_ONE_AUDIT = "会签";

    public final static String FILE_SING_TWO_AUDIT = "审核";

    public final static String FILE_SING_THREE_AUDIT = "审定";

    public final static String END_NODE = "流程终点";

    public final static int LEAVE_AUDIT_LENGTH = 2;

    public final static int FILE_SING_AUDIT_LENGTH = 3;
}
