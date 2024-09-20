package aiep.inf.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "ResponseDailyBoxOfficeList")
@Data
public class ResponseDailyBoxOfficeList {
    private DailyBoxOfficeResult boxOfficeResult;

    @Schema(name = "DailyBoxOfficeResult")
    @Data
    public class DailyBoxOfficeResult {
        private String boxofficeType;
        private String showRange;
        private List<DailyBoxOffice> dailyBoxOfficeList;
    }

    @Schema(name = "DailyBoxOffice")
    @Data
    public class DailyBoxOffice {
        private String rnum;
        private String rank;
        private String rankInten;
        private String rankOldAndNew;
        private String movieCd;
        private String movieNm;
        private String openDt;
        private String salesAmt;
        private String salesShare;
        private String salesInten;
        private String salesChange;
        private String salesAcc;
        private String audiCnt;
        private String audiInten;
        private String audiChange;
        private String audiAcc;
        private String scrnCnt;
        private String showCnt;
    }

}



