package com.artist.investment.domain;

import com.dili.ss.domain.annotation.Like;
import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2018-01-18 17:11:11.
 */
@Table(name = "`investment_platform`")
public interface InvestmentPlatform extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label="ID")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`name`")
    @FieldDef(label="名称", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getName();

    void setName(String name);

    @Column(name = "`supervision_bank`")
    @FieldDef(label="资金监管银行", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getSupervisionBank();

    void setSupervisionBank(String supervisionBank);

    @Column(name = "`is_record`")
    @FieldDef(label="是否备案")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getIsRecord();

    void setIsRecord(Integer isRecord);

    @Column(name = "`registry_place`")
    @FieldDef(label="注册地", maxLength = 10)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getRegistryPlace();

    void setRegistryPlace(String registryPlace);

    @Column(name = "`business_place`")
    @FieldDef(label="经营地", maxLength = 10)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getBusinessPlace();

    void setBusinessPlace(String businessPlace);

    @Column(name = "`platform_background`")
    @FieldDef(label="平台背景", maxLength = 120)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getPlatformBackground();

    void setPlatformBackground(String platformBackground);

    @Column(name = "`business_mode`")
    @FieldDef(label="业务模式", maxLength = 120)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getBusinessMode();

    void setBusinessMode(String businessMode);

    @Column(name = "`insurance_company`")
    @FieldDef(label="保险公司", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getInsuranceCompany();

    void setInsuranceCompany(String insuranceCompany);

    @Column(name = "`introduction`")
    @FieldDef(label="平台介绍", maxLength = 40)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getIntroduction();

    void setIntroduction(String introduction);

    @Column(name = "`uptime`")
    @FieldDef(label="上线时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    Date getUptime();

    void setUptime(Date uptime);

    @Column(name = "`notes`")
    @FieldDef(label="备注信息", maxLength = 250)
    @EditMode(editor = FieldEditor.Text, required = false)
    @Like(Like.BOTH)
    String getNotes();

    void setNotes(String notes);

    @Column(name = "`created`")
    @FieldDef(label="创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    Date getCreated();

    void setCreated(Date created);

    @Column(name = "`modified`")
    @FieldDef(label="修改时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    Date getModified();

    void setModified(Date modified);
}