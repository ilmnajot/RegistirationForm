package uz.ilmnajot.registirationform.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;
    private Object info;
    public ApiResponse(String message,  boolean success){
        this.message = message;
        this.success = success;
    }
    public ApiResponse(Object info , boolean success){
        this.info = info;
        this.success = success;
    }
}
