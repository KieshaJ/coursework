package com.kj.coursework.service.impl;

import com.kj.coursework.model.Company;
import com.kj.coursework.repository.CategoryRepository;
import com.kj.coursework.repository.CompanyRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.service.CompanyService;
import com.kj.coursework.util.request.CompanyRequest;
import com.kj.coursework.util.response.CompanyResponse;
import com.kj.coursework.util.service.CompanyServiceUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CompanyResponse get(ObjectId companyId) throws Exception {
        Company company = repository.findById(companyId).orElse(null);

        if(company == null) {
            throw new Exception("Company not found");
        }

        return CompanyServiceUtils.entityToResponse(company);
    }

    @Override
    public CompanyResponse save(CompanyRequest company) {
        return null;
    }

    @Override
    public CompanyResponse update(ObjectId companyId, CompanyRequest companyRequest) throws Exception {
        Company company = repository.findById(companyId).orElse(null);

        if(company == null) {
            throw new Exception("Company not found");
        }

        CompanyServiceUtils.updateEntityFields(company, companyRequest, userRepository, categoryRepository);
        company = repository.save(company);
        return CompanyServiceUtils.entityToResponse(company);
    }

    @Override
    public Boolean delete(ObjectId companyId) {
        repository.deleteById(companyId);
        return !repository.existsById(companyId);
    }
}
