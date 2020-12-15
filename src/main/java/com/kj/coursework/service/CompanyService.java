package com.kj.coursework.service;

import com.kj.coursework.util.request.CompanyRequest;
import com.kj.coursework.util.response.CompanyResponse;
import org.bson.types.ObjectId;

public interface CompanyService {
    CompanyResponse get(ObjectId companyId) throws Exception;
    CompanyResponse save(CompanyRequest company);
    CompanyResponse update(ObjectId companyId, CompanyRequest company) throws Exception;
    Boolean delete(ObjectId companyId);
}
