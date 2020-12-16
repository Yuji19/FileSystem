package com.yuji.polygon.controller;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.LeaveService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @className: LeaveController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-03 15:30:00
 */

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    LeaveService leaveSerice;

    @PostMapping("/add/one")
    public String addLeaveFlow(@Valid Leave leave, String[] eNo){
        if (eNo.length < ConstantValue.LEAVE_AUDIT_LENGTH ){
            throw new APIException(ResultCode.VALIDATE_FAILED.getCode(),"签审者不足");
        }
        int result = leaveSerice.addLeaveFlow(leave,eNo);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @PutMapping("/update/one")
    public String updateLeaveFlow(@RequestBody @Valid Approve approve){
        int result = leaveSerice.updateLeaveFlow(approve);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @GetMapping("/query/page")
    public Page getLeavePage(Leave leave, int pageNum, int pageSize){
        return leaveSerice.getLeavePage(leave,pageNum,pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLeaveFlow(@PathVariable int id){
        int result = leaveSerice.deleteLeaveFlow(id);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

}
