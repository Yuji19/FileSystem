package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Audit;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: AuditMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:09:00
 */

@Mapper
public interface AuditMapper {

    int insertAudit(Audit audit);
}
