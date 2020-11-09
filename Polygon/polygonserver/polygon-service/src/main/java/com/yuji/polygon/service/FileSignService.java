package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.FileSignMapper;

import com.yuji.polygon.service.utils.CommonUtil;
import com.yuji.polygon.service.utils.ConstantValue;
import com.yuji.polygon.service.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @className: FileSignService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-02 18:47:00
 */

@Service
public class FileSignService {

    @Value("${custom.path}")
    String customPath;

    @Autowired
    FileSignMapper fileSignMapper;

    @Autowired
    FlowService flowService;

    @Autowired
    FlowNodeService flowNodeService;

    @Autowired
    FlowLineService flowLineService;

    @Autowired
    AuditServie auditServie;

    @Transactional
    public String addFlowSignFlow(FileSign fileSign, FlowNode firstNode, FlowNode secondNode,
                                  FlowNode thirdNode, MultipartFile file) {
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("项目文件签审流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        flowService.insertFlow(flow);


        //创建流程节点
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.FILE_SIGN_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);


        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.FILE_SING_THREE_AUDIT);
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(secondNode);

        thirdNode.setFlowNo(flow.getFlowNo());
        thirdNode.setFlowNodeName(ConstantValue.FILE_SING_TWO_AUDIT);
        thirdNode.setGmtCreate(CommonUtil.getNowTime());
        thirdNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(thirdNode);


        //创建流程线
        FlowLine firstLine = new FlowLine();
        firstLine.setFlowNo(flow.getFlowNo());
        firstLine.setPreNode(0);
        firstLine.setNextNode(firstNode.getId());
        firstLine.setGmtCreate(CommonUtil.getNowTime());
        firstLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(firstLine);


        FlowLine secondLine = new FlowLine();
        secondLine.setFlowNo(flow.getFlowNo());
        secondLine.setPreNode(firstNode.getId());
        secondLine.setNextNode(secondNode.getId());
        secondLine.setGmtCreate(CommonUtil.getNowTime());
        secondLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(secondLine);

        FlowLine thirdLine = new FlowLine();
        thirdLine.setFlowNo(flow.getFlowNo());
        thirdLine.setPreNode(secondNode.getId());
        thirdLine.setNextNode(thirdNode.getId());
        thirdLine.setGmtCreate(CommonUtil.getNowTime());
        thirdLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(thirdLine);

        //创建项目文件签审单
        fileSign.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        fileSign.setCurrentNode(firstNode.getId());
        fileSign.setGmtCreate(CommonUtil.getNowTime());
        fileSign.setGmtModified(CommonUtil.getNowTime());
        //保存文件
        String originalFilename = file.getOriginalFilename();
        String savePath = customPath + "/" + flow.getFlowNo() + "/" + originalFilename.substring(0, originalFilename.lastIndexOf("."));
        savePath = FileUtil.getInstance().save(savePath, file);
        fileSign.setFilePath(savePath);
        fileSignMapper.insertFileSign(fileSign);


        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(fileSign.getId());
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        auditServie.insertAudit(firstAudit);


        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(fileSign.getId());
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        auditServie.insertAudit(secondAudit);

        Audit thirdAudit = new Audit();
        thirdAudit.setBusinessNo(fileSign.getId());
        thirdAudit.setEmployeeNo(thirdNode.getEmployeeNo());
        thirdAudit.setEmployeeName(thirdNode.getEmployeeName());
        thirdAudit.setFlowNodeNo(thirdNode.getId());
        auditServie.insertAudit(thirdAudit);

        return "提交成功";
    }

    @Transactional
    public String updateFileSignFlow(Audit audit) {
        audit.setAuditDate(CommonUtil.getNowTime());
        auditServie.updateAudit(audit);

        //获取请假单
        FileSign fileSign = fileSignMapper.findFileSignById(audit.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(fileSign.getCurrentNode());

        if (flowLine == null || audit.getAuditState() == -1) {
            //已到流程的终点，设置节点为0，流程结束
            fileSign.setCurrentNode(0);
            fileSign.setFlowState(1);
        } else {
            //设置为下一个节点
            fileSign.setCurrentNode(flowLine.getNextNode());
        }
        fileSignMapper.updateFileSign(fileSign);


        return "审批成功";
    }

    public FileSign findFileSignById(int id) {
        return fileSignMapper.findFileSignById(id);
    }

    public Page<FileSign> getFileSignPage(FileSign fileSign, int currentPageNumber, int pageSize) {
        int startIndex = (currentPageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<>(4);
        map.put("fileSign", fileSign);
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        int totalCount = fileSignMapper.countTotal(fileSign);
        Page page = new Page(currentPageNumber, totalCount);

        List<FileSign> records = fileSignMapper.listFileSign(map);
        page.setRecords(records);
        return page;
    }

    public void downloadSignFile(int id, HttpServletResponse response) {
        Optional<FileSign> optional = Optional.ofNullable(fileSignMapper.findFileSignById(id));
        if (!optional.isPresent()) {
            throw new APIException("文档不存在");
        }
        FileUtil.getInstance().download(optional.get().getFilePath(), response);
    }

    @Transactional
    public String deleteFileSignById(int id) {
        String flowNo = fileSignMapper.findFileSignById(id).getFlowNo();
        fileSignMapper.deleteFileSignById(id);
        flowService.deleteFlowByFlowNo(flowNo);
        flowNodeService.deleteFlowNodeByFlowNo(flowNo);
        flowLineService.deleteFlowLineByFlowNo(flowNo);
        auditServie.deleteAuditByBusinessNo(id);
        return "删除成功";
    }
}