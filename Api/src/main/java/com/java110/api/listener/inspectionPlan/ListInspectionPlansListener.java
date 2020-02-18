package com.java110.api.listener.inspectionPlan;

import com.alibaba.fastjson.JSONObject;
import com.java110.api.listener.AbstractServiceApiListener;
import com.java110.core.smo.org.IOrgInnerServiceSMO;
import com.java110.core.smo.org.IOrgStaffRelInnerServiceSMO;
import com.java110.core.smo.user.IUserInnerServiceSMO;
import com.java110.dto.org.OrgStaffRelDto;
import com.java110.dto.user.UserDto;
import com.java110.utils.constant.ServiceCodeInspectionPlanConstant;
import com.java110.utils.util.BeanConvertUtil;
import com.java110.core.annotation.Java110Listener;
import com.java110.core.context.DataFlowContext;
import com.java110.core.smo.inspectionPlan.IInspectionPlanInnerServiceSMO;
import com.java110.dto.inspectionPlan.InspectionPlanDto;
import com.java110.event.service.api.ServiceDataFlowEvent;
import com.java110.vo.api.inspectionPlan.ApiInspectionPlanDataVo;
import com.java110.vo.api.inspectionPlan.ApiInspectionPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 查询小区侦听类
 */
@Java110Listener("listInspectionPlansListener")
public class ListInspectionPlansListener extends AbstractServiceApiListener {

    @Autowired
    private IInspectionPlanInnerServiceSMO inspectionPlanInnerServiceSMOImpl;
    @Autowired
    private IOrgStaffRelInnerServiceSMO iOrgStaffRelInnerServiceSMO;


    @Override
    public String getServiceCode() {
        return ServiceCodeInspectionPlanConstant.LIST_INSPECTION_PLANS;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }


    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }


    public IInspectionPlanInnerServiceSMO getInspectionPlanInnerServiceSMOImpl() {
        return inspectionPlanInnerServiceSMOImpl;
    }

    public void setInspectionPlanInnerServiceSMOImpl(IInspectionPlanInnerServiceSMO inspectionPlanInnerServiceSMOImpl) {
        this.inspectionPlanInnerServiceSMOImpl = inspectionPlanInnerServiceSMOImpl;
    }

    @Override
    protected void validate(ServiceDataFlowEvent event, JSONObject reqJson) {
        super.validatePageInfo(reqJson);
    }

    @Override
    protected void doSoService(ServiceDataFlowEvent event, DataFlowContext context, JSONObject reqJson) {

        InspectionPlanDto inspectionPlanDto = BeanConvertUtil.covertBean(reqJson, InspectionPlanDto.class);

        int count = inspectionPlanInnerServiceSMOImpl.queryInspectionPlansCount(inspectionPlanDto);

        List<ApiInspectionPlanDataVo> inspectionPlans = null;

        if (count > 0) {
            inspectionPlans = BeanConvertUtil.covertBeanList(inspectionPlanInnerServiceSMOImpl.queryInspectionPlans(inspectionPlanDto), ApiInspectionPlanDataVo.class);
            ArrayList staffIds = new ArrayList<>();
            for( ApiInspectionPlanDataVo Plans : inspectionPlans){
                if(Plans.getStaffId() != null){
                    staffIds.add(Plans.getStaffId());
                }
            }
            if(staffIds.size() > 0){
                OrgStaffRelDto orgStaffRelDto = new OrgStaffRelDto();
                String[] staffIdsArray=new String[staffIds.size()];
                staffIds.toArray(staffIdsArray);
                orgStaffRelDto.setStaffIds(staffIdsArray);
                List<OrgStaffRelDto> orgStaffRelDtos = iOrgStaffRelInnerServiceSMO.queryOrgInfoByStaffIds(orgStaffRelDto);
                for( ApiInspectionPlanDataVo planDataVo : inspectionPlans){
                    for(OrgStaffRelDto orgs : orgStaffRelDtos){
                        if(planDataVo.getStaffId().equals(orgs.getStaffId())){
                            planDataVo.setDepartmentId(orgs.getDepartmentId());
                            planDataVo.setDepartmentName(orgs.getDepartmentName());
                            planDataVo.setCompanyId(orgs.getCompanyId());
                            planDataVo.setCompanyName(orgs.getCompanyName());
                        }
                    }
                }
            }
        } else {
            inspectionPlans = new ArrayList<>();
        }

        ApiInspectionPlanVo apiInspectionPlanVo = new ApiInspectionPlanVo();

        apiInspectionPlanVo.setTotal(count);
        apiInspectionPlanVo.setRecords((int) Math.ceil((double) count / (double) reqJson.getInteger("row")));
        apiInspectionPlanVo.setInspectionPlans(inspectionPlans);

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(JSONObject.toJSONString(apiInspectionPlanVo), HttpStatus.OK);

        context.setResponseEntity(responseEntity);

    }
}