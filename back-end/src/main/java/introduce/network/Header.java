package introduce.network;

import introduce.utill.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    private String transactionTime; //api 통신시간
    private String resultCode; //api 응답코드
    private String description; //api 부가설명
    private T data;

    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transactionTime(DateUtil.getTodayByCustomFormat("YYYY-dd-MM"))
                .resultCode("200")
                .description("OK")
                .build();
    }

    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(DateUtil.getTodayByCustomFormat("YYYY-dd-MM"))
                .resultCode("200")
                .description("OK")
                .data(data)
                .build();
    }

    public static <T> Header<T> ERROR(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(DateUtil.getTodayByCustomFormat("YYYY-dd-MM"))
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
