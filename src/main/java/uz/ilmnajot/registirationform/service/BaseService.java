package uz.ilmnajot.registirationform.service;

import uz.ilmnajot.registirationform.model.common.ApiResponse;
import uz.ilmnajot.registirationform.model.request.RegisterForm;

public interface BaseService<T, R>{
    ApiResponse save(T t);

    ApiResponse getById(R r);

    ApiResponse getToken(T t);
    ApiResponse deleteById(R r);
    ApiResponse update(T t, R r);

//    ApiResponse getAll(R r1, R r2);
}
