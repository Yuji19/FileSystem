package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.Flow;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.FlowMapper;
import com.yuji.polygon.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowServiceImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:30:00
 */

@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    FlowMapper flowMapper;

    @Override
    public ResultVO insertFlow(Flow flow) {
        return (flowMapper.insertFlow(flow) > 0 ? new ResultVO("添加成功") : new ResultVO(ResultCode.FAILED,"添加失败"));
    }
}
