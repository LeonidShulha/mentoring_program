package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DefaultGetApiResponse {
    private int code;
    private String type;
    private String message;
}
